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

    fun getProductInvoice(): String {

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

        val productService = retrofit.create(ProductService::class.java)
        productService
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
            .blockingSubscribe(
                { resp ->
                    Log.d(LOG_TAG, "Product: $resp")
                },
                { t ->
                    Log.e(LOG_TAG, "On error")
                    t.let { Log.e(LOG_TAG, t.message!!) }
                },
                {
                    Log.w(LOG_TAG, "Complete")
                }
            )

        return ""
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