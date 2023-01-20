package com.mpi.kissing_company.configuration


import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry


@Configuration
class WebMvcConfig : WebMvcConfigurer {
    private val log = LoggerFactory.getLogger(WebMvcConfig::class.java)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        log.info("addResourceHandlers called")
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
    }
    override fun addViewControllers(registry: ViewControllerRegistry) {
        log.info("addViewControllers called")
        registry.addViewController("/").setViewName("forward:/index.html")
    }
}