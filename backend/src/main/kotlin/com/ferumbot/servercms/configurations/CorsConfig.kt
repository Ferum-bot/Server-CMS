package com.ferumbot.servercms.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    companion object {

        private const val FIRST_BASE_URL = "http://localhost:3000"
        private const val SECOND_BASE_URL = "http://localhost:4200"
    }

    @Bean
    fun configCors(): CorsFilter {
        val allowedHeaders = listOf(
            "Origin", "Access-Control-Allow-Origin", "Content-Type",
            "Accept", "Jwt-Token", "Authorization", "X-Requested-With",
            "Access-Control-Request-Method", "Access-Control-Request-Headers"
        )
        val exposedHeaders = listOf(
            "Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"
        )
        val allowedMethods = listOf(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        )


        val corsBaseSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowCredentials = true
        corsConfig.allowedOrigins = listOf(FIRST_BASE_URL, SECOND_BASE_URL)
        corsConfig.allowedHeaders = allowedHeaders
        corsConfig.exposedHeaders = exposedHeaders
        corsConfig.allowedMethods = allowedMethods
        corsBaseSource.registerCorsConfiguration("/**", corsConfig)
        return CorsFilter(corsBaseSource)
    }
}