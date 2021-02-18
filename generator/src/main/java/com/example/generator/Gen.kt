package com.example.generator

//
// Can Android Studio be used to run standard Java projects?
// https://stackoverflow.com/questions/16626810/can-android-studio-be-used-to-run-standard-java-projects
//

fun main(args: Array<String>) {
    buildProducts().apply {
        forEachIndexed { index, product ->
            println(product.toString(index + 1))
            if (index < size - 1) println()
        }
    }

    // TODO build files:
    //      1. Total list of products
    //      2. For each products
    //      3. For results
}


fun buildProducts(): List<Product> {
    val products = arrayListOf<Product>()
    products += Product("Картофель", "Жарь и вари, фри и жри")
    products += Product("Бананы", "Банан - вкусный и полезный фрукт")
    products += Product("Молоко", "Вкусное молочко Амка")
    products += Product("Кефир", "Кислый и молочный")
    products += Product("Сливочное масло", "Масло масляное")
    products += Product("Мясо рыбы", "Полезное морское блюдо")
    products += Product("Рис", "Все блюда из риса, чего не нажрись")
    products += Product("Яблоки", "Сорта Gold")
    products += Product("Чипсы", "Lays с паприкой")
    products += Product("Чай", "Суй сушки в чай")
    products += Product("Пончики", "Вкусные, с сахарной пудрой")
    return products.mapIndexed { index, product -> product.apply {
        price = 40 + (index + 1) * 10
    }}
}

data class Product(val name: String, val description: String, var price: Int = -1)

fun Product.toString(idx: Int) = StringBuffer().apply {
    append("#$idx $name")
    append("\n")
    append(description)
    append("\n")
    append("$price руб")
}.toString()
