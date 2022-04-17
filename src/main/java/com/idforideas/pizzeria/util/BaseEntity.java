package com.idforideas.pizzeria.util;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;


import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity  {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    @JsonIgnore
    public boolean isNew() {
        return id == null ? true : false;
    }
}
