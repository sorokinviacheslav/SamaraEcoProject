package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NamePattern("%s %s|streetType,name")
@Table(name = "ECO_STREET")
@Entity(name = "eco_Street")
public class Street extends StandardEntity {
    private static final long serialVersionUID = 7264410046343612446L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "STREET_TYPE", nullable = false)
    protected Integer streetType;

    public StreetType getStreetType() {
        return streetType == null ? null : StreetType.fromId(streetType);
    }

    public void setStreetType(StreetType streetType) {
        this.streetType = streetType == null ? null : streetType.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}