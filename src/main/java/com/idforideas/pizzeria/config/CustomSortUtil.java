package com.idforideas.pizzeria.config;

import com.idforideas.pizzeria.util.SortArrays;
import com.idforideas.pizzeria.util.SortUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomSortUtil {

    @Bean
    public SortUtil sort() {
        return new SortArrays();
    }
}
