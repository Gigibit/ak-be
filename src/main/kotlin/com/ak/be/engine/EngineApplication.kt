package com.ak.be.engine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
//@ConfigurationProprties
class EngineApplication

fun main(args: Array<String>) {
    runApplication<EngineApplication>(*args)
}
