package com.example.rxproducts

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Test

class ProductProviderTest {

    @Test
    fun `generated invoice is valid`() {
        val productProvider = ProductProvider()
        assertEquals(productProvider.getProductInvoice(), getActualInvoice())
    }

    @Test
    fun `observable of products is valid`() {
        val testObserver: TestObserver<Product> = TestObserver()

        val productProvider = ProductProvider()
        productProvider.getProductsForInvoice()
            .blockingSubscribe(testObserver)

        testObserver.assertValueCount(11)
        testObserver.assertValueAt(1, Product().apply {
            name = "Бананы"
            description = "Банан - вкусный и полезный фрукт"
            price = 60.0
            src = "https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/item_banana.json"
        })
    }

    // Src: https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/result.txt
    private fun getActualInvoice() = "#1 Картофель\n" +
            "Жарь и вари, бери и жри\n" +
            "50 руб\n" +
            "\n" +
            "#2 Бананы\n" +
            "Банан - вкусный и полезный фрукт\n" +
            "60 руб\n" +
            "\n" +
            "#3 Молоко\n" +
            "Вкусное молочко Амка\n" +
            "70 руб\n" +
            "\n" +
            "#4 Кефир\n" +
            "Кислый и молочный\n" +
            "80 руб\n" +
            "\n" +
            "#5 Сливочное масло\n" +
            "Масло масляное\n" +
            "90 руб\n" +
            "\n" +
            "#6 Мясо рыбы\n" +
            "Полезное морское блюдо\n" +
            "100 руб\n" +
            "\n" +
            "#7 Рис\n" +
            "Все блюда из риса, чего не нажрись\n" +
            "110 руб\n" +
            "\n" +
            "#8 Яблоки\n" +
            "Сорта Gold\n" +
            "120 руб\n" +
            "\n" +
            "#9 Чипсы\n" +
            "Lays с паприкой\n" +
            "130 руб\n" +
            "\n" +
            "#10 Чай\n" +
            "Суй сушки в чай\n" +
            "140 руб\n" +
            "\n" +
            "#11 Пончики\n" +
            "Вкусные, с сахарной пудрой\n" +
            "150 руб"

}