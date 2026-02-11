package com.pucetec.menteactiva.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// Controlador Global de Excepciones
// Captura errores en cualquier parte de la aplicación y devuelve una respuesta JSON limpia.
@RestControllerAdvice
class GlobalExceptionHandler {

    // Maneja errores de lógica (ej: credenciales incorrectas, email duplicado)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        // Devuelve 401 Unauthorized o 400 Bad Request según el caso, con el mensaje de error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to (ex.message ?: "Credenciales invalidas")))
    }

    // Maneja errores de validación de formulario (@Valid, @NotBlank, etc.)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        // Recolecta todos los errores de campo y los devuelve en un mapa
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Valor invalido") }
        return ResponseEntity.badRequest().body(errors)
    }
}
