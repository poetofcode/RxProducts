# Задание по RxJava: список продуктов

Есть список продуктов в формате JSON по адресу: https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/products.json:

```
[
    {
        "name": "Бананы",
        "src": "https://raw.githubusercontent.com/..../item_banan.json"
    },
    {
        "name": "Картошка",
        "src": "https://raw.githubusercontent.com/..../item_patato.json"
    },
    ...
]
```

Детализация параметров продукта доступна по урлам в параметре `src` и представляет собой JSON вида:

```
{
    "desription": "Банан - вкусный и полезный фрукт",
    "price": 35
}
```

**Задание:** необходимо получить сначала список всех продуктов. Затем запросить у каждого продукта детализацию по ссылкам `src`, произвести сортировку по цене (от меньшей к большей), далее вывести на экран в виде:

```
#1 Картошка
Вид многолетних клубненосных травянистых растений
17 руб

#2 Бананы
Банан - вкусный и полезный фрукт
35 руб

...
```

Можно написать юнит-тест для запроса. 
Проверочный результат взять отсюда: https://raw.githubusercontent.com/poetofcode/RxProducts/master/products/result.txt
