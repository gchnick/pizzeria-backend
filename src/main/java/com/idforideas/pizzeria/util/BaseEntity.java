package com.idforideas.pizzeria.util;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;

import org.springframework.data.domain.Persistable;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Override
    public boolean isNew() {
        return id == null ? true : false;
    }
}
