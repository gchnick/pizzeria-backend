package com.idforideas.pizzeria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandledSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket docs(){
        return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandledSelectors.basePackage("com.idforideas.pizzeria"))
        .paths(PathSelectors.any()).build();
    }
    
}
