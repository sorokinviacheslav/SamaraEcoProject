package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NamePattern("%s %s %s|name,inn,description")
@Table(name = "ECO_ORGANIZATION")
@Entity(name = "eco_Organization")
public class Organization extends StandardEntity {
    private static final long serialVersionUID = 1678071854986251832L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "INN", length = 20)
    protected String inn;

    @NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    protected String description;

    @JoinTable(name = "ECO_BUILDING_ORGANIZATION_LINK",
            joinColumns = @JoinColumn(name = "ORGANIZATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "BUILDING_ID"))
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected Set<Building> buildings;

    public Set<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}