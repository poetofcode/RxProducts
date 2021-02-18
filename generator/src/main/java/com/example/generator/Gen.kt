package com.example.generator

import java.io.File

//
// Can Android Studio be used to run standard Java projects?
// https://stackoverflow.com/questions/16626810/can-android-studio-be-used-to-run-standard-java-projects
//

fun main(args: Array<String>) {
    // Step 1: save result to "result.txt"
    val products = buildProducts()
    val result = products.mapIndexed { index, product ->
        product.toString(index + 1)
    }.joinToString("\n\n")
    saveTextFile("result.txt", result)

    // Step 2: save total list of products in "products.json"
    val productList = products.shuffled().joinToString(", ") { product ->
        product.toJsonAsListItem("https://github.com/.../")
    }
    saveTextFile("products.json", "[$productList]")

    // Step 3: save each products
    products.forEach {
        saveTextFile("item_${it.fileName}.json", it.toJsonAsFileContent())
    }
}


fun buildProducts(): List<Product> {
    val products = arrayListOf<Product>()
    products += Product("Картофель", "Жарь и вари, бери и жри", "potato")
    products += Product("Бананы", "Банан - вкусный и полезный фрукт", "banana")
    products += Product("Молоко", "Вкусное молочко Амка", "milk")
    products += Product("Кефир", "Кислый и молочный", "kefir")
    products += Product("Сливочное масло", "Масло масляное", "butter")
    products += Product("Мясо рыбы", "Полезное морское блюдо", "fish")
    products += Product("Рис", "Все блюда из риса, чего не нажрись", "rice")
    products += Product("Яблоки", "Сорта Gold", "apple")
    products += Product("Чипсы", "Lays с паприкой", "chips")
    products += Product("Чай", "Суй сушки в чай", "tea")
    products += Product("Пончики", "Вкусные, с сахарной пудрой", "donut")
    return products.mapIndexed { index, product -> product.apply {
        price = 40 + (index + 1) * 10
    }}
}

fun saveTextFile(name: String, contents: String) {
    val myfile = File("products/" + name)
    myfile.printWriter().use { out ->
        out.print(contents)
    }
}

data class Product(
    val name: String,
    val description: String,
    val fileName: String,
    var price: Int = -1
)

fun Product.toString(idx: Int) = StringBuffer().apply {
    append("#$idx $name")
    append("\n")
    append(description)
    append("\n")
    append("$price руб")
}.toString()

fun Product.toJsonAsListItem(basePath: String) = StringBuffer().apply {
    append("{")
    append(kv("name", name))
    append(",")
    append(kv("src", "${basePath}item_$fileName"))
    append("}")
}.toString()

fun Product.toJsonAsFileContent() = StringBuffer().apply {
    append("{")
    append(kv("description", description))
    append(",")
    append(kv("price", price))
    append("}")
}.toString()

fun kv(key: String, value: String) = "\"$key\": \"$value\""

fun kv(key: String, value: Int) = "\"$key\": $value"