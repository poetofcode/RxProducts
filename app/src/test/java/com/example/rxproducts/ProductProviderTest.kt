package com.example.rxproducts

import junit.framework.Assert.assertEquals
import org.junit.Test

class ProductProviderTest {

    @Test
    fun productList_isValid() {
        val productProvider = ProductProvider()
        assertEquals(productProvider.getProductInvoice(), getActualInvoice())
    }

    // Src: https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/result.txt
    fun getActualInvoice() = "#1 Картофель\n" +
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