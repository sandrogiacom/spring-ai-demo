package com.sandrogiacom.spring_ai_demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SwaggerConfig: WebMvcConfigurer {

    private val swaggerUiPath = "/webjars/swagger-ui/5.17.14/index.html?url=/openapi.json"

    override fun addViewControllers(registry: ViewControllerRegistry) {
        // Redireciona a rota /swagger-ui para o index.html correto do WebJar
        registry.addRedirectViewController("/swagger-ui.html", swaggerUiPath)
        registry.addRedirectViewController("/swagger-ui", swaggerUiPath)
        registry.addRedirectViewController("/swagger-ui/", swaggerUiPath)
        registry.addRedirectViewController("/webjars/swagger-ui/index.html", swaggerUiPath)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // Garante que o Spring consiga servir os arquivos internos do Swagger UI
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

}