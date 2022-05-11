package com.idforideas.pizzeria.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomSortUtil {

    @Bean
    public SortUtil sort() {
        return new SortArrays();
    }
}
