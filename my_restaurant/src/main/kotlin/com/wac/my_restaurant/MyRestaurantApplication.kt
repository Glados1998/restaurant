package com.wac.my_restaurant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyRestaurantApplication

fun main(args: Array<String>) {
    runApplication<MyRestaurantApplication>(*args)
}
