package com.pucetec.menteactiva

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// Punto de entrada principal de la aplicación Spring Boot
@SpringBootApplication
class MenteactivaApplication

fun main(args: Array<String>) {
    // Inicia el servidor Tomcat embebido y configura todo el contexto de Spring
	runApplication<MenteactivaApplication>(*args)
}
