package br.anhembi.funmodechild

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Fun Mode Child API",
        version = "2.0.0",
        description = "API for Fun Mode Child E-commerce"
    )
)
class FunModeChildApplication

fun main(args: Array<String>) {
    runApplication<FunModeChildApplication>(*args)
}
