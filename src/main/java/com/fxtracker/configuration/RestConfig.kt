package com.fxtracker.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
open class RestConfig {
    @Bean
    open fun restTemplate():RestTemplate = RestTemplate()

}