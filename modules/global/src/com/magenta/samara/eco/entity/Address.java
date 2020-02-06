package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s %s|street,streetNumber,addressParams")
@Table(name = "ECO_ADDRESS")
@Entity(name = "eco_Address")
public class Address extends StandardEntity {
    private static final long serialVersionUID = -7865887894837200073L;

    @NotNull
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COORDINATES_ID", unique = true)
    protected Point coordinates;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STREET_ID")
    protected Street street;

    @NotNull
    @Column(name = "STREET_NUMBER", nullable = false)
    protected Integer streetNumber;

    @Column(name = "ADDRESS_PARAMS", length = 10)
    protected String addressParams;

    public String getAddressParams() {
        return addressParams;
    }

    public void setAddressParams(String addressParams) {
        this.addressParams = addressParams;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}