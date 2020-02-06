package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum StreetType implements EnumClass<Integer> {

    STREET(10),
    AVENUE(20),
    DRIVEWAY(30),
    HIGHWAY(40),
    LANE(50),
    BLOCK(60),
    DEAD_END(70),
    CLEARING(80);

    private Integer id;

    StreetType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StreetType fromId(Integer id) {
        for (StreetType at : StreetType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}