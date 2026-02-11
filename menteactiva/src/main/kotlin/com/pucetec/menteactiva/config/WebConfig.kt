package com.pucetec.menteactiva.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    // Configuración de CORS (Cross-Origin Resource Sharing)
    // Permite que el Frontend (React/Ionic) se comunique con este Backend aunque estén en puertos diferentes.
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**") // Aplica a todas las rutas de la API
            .allowedOriginPatterns("*") // Permite cualquier origen (para desarrollo)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // Métodos HTTP permitidos
            .allowedHeaders("*") // Permite todos los encabezados
            .allowCredentials(true) // Permite cookies y credenciales
    }
}
