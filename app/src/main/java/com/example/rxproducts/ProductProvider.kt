package com.example.rxproducts

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val API_URL = "https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/"
const val LOG_TAG = "ProductProvider"

class ProductProvider {

    fun getProductInvoice(): String {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

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
            .subscribeOn(Schedulers.trampoline())
            .subscribe(
                { prods ->
                    Log.d(LOG_TAG, "Product size from request: ${prods.size}")
                },
                { t ->
                    t.let { Log.e(LOG_TAG, t.message!!) }
                }
            )

        return ""
    }

}

class Product {

    var name: String = ""

    var src: String = ""

    var desription: String = ""

    var price: Double = .0

}

interface ProductService {

    @GET("products.json")
    fun getProductList(): Observable<List<Product>>

}