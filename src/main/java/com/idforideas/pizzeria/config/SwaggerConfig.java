package com.idforideas.pizzeria.config;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 3/21/2022
 */
@Configuration
public class SwaggerConfig {

    // FIXME Correguir configuracion
    // Actualmente solo ve los recursos de product
    @Bean
    public Docket api(){
        return new Docket(SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.idforideas.pizzeria"))
        .paths(PathSelectors.ant("/api/v1/*"))
        .build()
        .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("API de la pizzeria Don Remolo",
            "API para la solicitud de pedidos de productos a la pizzeria",
            "1.0",
            "Term of service",
            new Contact("Equipo # 221 de ID FOR IDEAS", "www.idforideas.org", "pizzeriadonremolo@gmail.com"),
            "MIT",
            "https://opensource.org/licenses/MIT",
            Collections.emptyList());
    }
}
