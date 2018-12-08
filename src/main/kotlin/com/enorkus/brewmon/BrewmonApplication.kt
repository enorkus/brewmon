package com.enorkus.brewmon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BrewmonApplication

fun main(args: Array<String>) {
    runApplication<BrewmonApplication>(*args)
}
