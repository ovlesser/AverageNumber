package com.example.number

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.util.*

object AppModuleProvider {
    val objectMapper: ObjectMapper
        get() = jacksonObjectMapper()
            .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH))
            .enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

    private val apiConfig: ApiConfig = ApiConfig()
    val apiBaseUrl: String = "${apiConfig.domain}/"
}

data class ApiConfig(
    val domain: String = " https://roktcdn1.akamaized.net/store/test/android"
)
