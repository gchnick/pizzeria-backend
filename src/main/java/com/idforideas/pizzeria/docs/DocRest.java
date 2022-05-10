package com.idforideas.pizzeria.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"", "/", "/docs"})
public class DocRest {
    
    @GetMapping
    public String doc() {
        return "/docs/index";    
    }
}
