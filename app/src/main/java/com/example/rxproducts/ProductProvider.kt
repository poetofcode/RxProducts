package com.example.rxproducts

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException


const val API_URL = "https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/"
const val LOG_TAG = "ProductProvider"

class ProductProvider {

    private val productService: ProductService

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.NONE)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()


        productService = retrofit.create(ProductService::class.java)
    }

    fun getProductInvoice(): String {
        var resultInvoice = ""
            getProductsForInvoice()
            .toList()
            .blockingSubscribe(
                { sortedProds ->
                    // Log.d(LOG_TAG, "Products: $sortedProds")
                    resultInvoice = sortedProds.mapIndexed { idx, prod ->
                        if (prod !is Product) {
                            return@mapIndexed ""
                        }
                        "#${idx + 1} ${prod.name}\n${prod.description}\n${prod.price.toInt()} руб"
                    }.joinToString("\n\n")
                },
                { t ->
                    Log.e(LOG_TAG, "On error")
                    t.let { Log.e(LOG_TAG, t.message!!) }
                }
            )

        return resultInvoice
    }

    fun getProductsForInvoice(): Observable<Product> {
        return productService
            .getProductList()
            .flatMap { prods ->
                if (prods == null) {
                    return@flatMap Observable.just(Observable.error<Any>(IOException("Product list request failed")))
                }
                val obsList = prods.map {
                    val parts = it.src.split("/")
                    Observable.zip(
                        Observable.just(it),
                        productService.getProduct(parts.last()),
                        BiFunction { prodA: Product?, prodB: Product? ->
                            Product.mergeTwo(prodA, prodB)
                        }
                    )
                }
                return@flatMap Observable.merge(obsList)
            }
            .toSortedList { prodA, prodB ->
                if (prodA !is Product || prodB !is Product) {
                    return@toSortedList 0
                }
                return@toSortedList if (prodA.price > prodB.price) 1 else -1
            }
            .toObservable()
            .flatMap { lst ->
                return@flatMap Observable.fromIterable(lst.map {
                    return@map it as Product
                })
            }
    }
}

class Product {

    var name: String = ""

    var src: String = ""

    var description: String = ""

    var price: Double = .0

    override fun toString(): String {
        return "Product(name='$name', src='$src', description='$description', price=$price)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (name != other.name) return false
        if (src != other.src) return false
        if (description != other.description) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + src.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }

    companion object {
        fun mergeTwo(prodA: Product?, prodB: Product?): Product {
            if (prodA == null && prodB == null) {
                return Product()
            }
            if (prodB == null) {
                return prodA!!
            }
            if (prodA == null) {
                return prodB
            }
            val res = Product()
            res.name = prodA.name
            res.src = prodA.src
            res.price = prodB.price
            res.description = prodB.description
            return res
        }
    }
}

interface ProductService {

    @GET("products.json")
    fun getProductList(): Observable<List<Product>>

    @GET("{path}")
    fun getProduct(@Path("path") path: String): Observable<Product>

}