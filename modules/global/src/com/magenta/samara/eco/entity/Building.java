package com.magenta.samara.eco.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.Set;

@NamePattern("%s %s|address,description")
@Table(name = "ECO_BUILDING")
@Entity(name = "eco_Building")
public class Building extends StandardEntity {
    private static final long serialVersionUID = 6349583310962679057L;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", unique = true)
    protected Address address;

    @Column(name = "DESCRIPTION", length = 1000)
    protected String description;

    @JoinTable(name = "ECO_BUILDING_ORGANIZATION_LINK",
            joinColumns = @JoinColumn(name = "BUILDING_ID"),
            inverseJoinColumns = @JoinColumn(name = "ORGANIZATION_ID"))
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected Set<Organization> organizations;

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    /*public List<Organization> getOrganizationsCustom() {
        List<Organization> orgs = new ArrayList<>(organizations.size());
        organizations.forEach(otbr -> orgs.add(otbr.getOrganization()));
        return orgs;
    }

    public void addOrganizationCustom(Organization organization) {
        this.organizations.forEach(otbr -> {
            if(otbr.getOrganization().equals(organization)) return;
        });
        OrgBuildingLink orgToBuilding = new OrgBuildingLink();
        orgToBuilding.setBuilding(this);
        orgToBuilding.setOrganization(organization);
        this.organizations.add(orgToBuilding);
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}