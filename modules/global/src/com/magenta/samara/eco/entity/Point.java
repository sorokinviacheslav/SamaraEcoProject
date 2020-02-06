package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|latitude,longitude")
@Table(name = "ECO_POINT")
@Entity(name = "eco_Point")
public class Point extends StandardEntity {
    private static final long serialVersionUID = 4397433014202740434L;

    @NotNull
    @Column(name = "LONGITUDE", nullable = false)
    protected Double longitude;

    @NotNull
    @Column(name = "LATITUDE", nullable = false)
    protected Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}