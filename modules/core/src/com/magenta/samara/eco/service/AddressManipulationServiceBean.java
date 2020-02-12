package com.magenta.samara.eco.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.magenta.samara.eco.core.GeoJsonTools;
import com.magenta.samara.eco.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;

@Service(AddressManipulationService.NAME)
public class AddressManipulationServiceBean implements AddressManipulationService {

    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private Messages messages;

    @Inject
    private GeoJsonTools geoJsonTools;

    @Override
    @Transactional
    public boolean addNewBuilding(AddressInfo addrInfo, BuildingInfo bldInfo) {
        EntityManager em = persistence.getEntityManager();
        if(isAddressExist(em,addrInfo)) return false;
        String par = "Самара, "+addrInfo.getStreetName()+" "+messages.getMessage(addrInfo.getStreetType())+" "+addrInfo.getStreetNumber()+" "+addrInfo.getAddressParams();
        JSONObject json = null;
        try {
            json = geoJsonTools.getGeoJson(par);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(!geoJsonTools.checkJsonFullMatch(json)) return false;
        JSONArray coord = geoJsonTools.getCoordinatesAsArray(json);
        Point p = metadata.create(Point.class);
        p.setLatitude(coord.getDouble(1));
        p.setLongitude(coord.getDouble(0));
        em.persist(p);
        Street str = getStreet(em,addrInfo.getStreetName(),addrInfo.getStreetType());
        if(str==null) {
            str = metadata.create(Street.class);
            str.setName(addrInfo.getStreetName());
            str.setStreetType(addrInfo.getStreetType());
            em.persist(str);
        }
        Address addr = metadata.create(Address.class);
        addr.setStreet(str);
        addr.setCoordinates(p);
        addr.setStreetNumber(addrInfo.getStreetNumber());
        addr.setAddressParams(addrInfo.getAddressParams());
        em.persist(addr);
        Building bld = metadata.create(Building.class);
        bld.setAddress(addr);
        bld.setDescription(bldInfo.getDescription());
        em.persist(bld);
        return true;
    }

    @Override
    @Transactional
    public boolean addNewAddress(AddressInfo addrInfo) {
        EntityManager em = persistence.getEntityManager();
        if(isAddressExist(em,addrInfo)) return false;
        String par = "Самара, "+addrInfo.getStreetName()+" "+messages.getMessage(addrInfo.getStreetType())+" "+addrInfo.getStreetNumber()+" "+addrInfo.getAddressParams();
        JSONObject json = null;
        try {
            json = geoJsonTools.getGeoJson(par);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(!geoJsonTools.checkJsonFullMatch(json)) return false;
        JSONArray coord = geoJsonTools.getCoordinatesAsArray(json);
        Point p = metadata.create(Point.class);
        p.setLatitude(coord.getDouble(1));
        p.setLongitude(coord.getDouble(0));
        em.persist(p);
        Street str = getStreet(em,addrInfo.getStreetName(),addrInfo.getStreetType());
        if(str==null) {
            str = metadata.create(Street.class);
            str.setName(addrInfo.getStreetName());
            str.setStreetType(addrInfo.getStreetType());
            em.persist(str);
        }
        Address addr = metadata.create(Address.class);
        addr.setStreet(str);
        addr.setCoordinates(p);
        addr.setStreetNumber(addrInfo.getStreetNumber());
        addr.setAddressParams(addrInfo.getAddressParams());
        em.persist(addr);
        return true;
    }

    @Override
    @Transactional
    public boolean isAddressInDB(AddressInfo addrInfo) {
        return isAddressExist(persistence.getEntityManager(),addrInfo);
    }

    @Override
    @Transactional
    public Building getBuildingByCoordinates(Double latitude, Double longitude) {
        return (Building)persistence.getEntityManager().createQuery("select b from eco_Building b where b.address.coordinates.longitude=:long"+
                " and b.address.coordinates.latitude=:lat")
                .setParameter("long",longitude)
                .setParameter("lat",latitude)
                .setView(Building.class,"building-field-inspector-view")
                .getFirstResult();
    }

    private boolean isAddressExist(EntityManager em,AddressInfo addrInfo) {
        int existing = em.createQuery(
                "select a from eco_Address a where a.street.name=:street and a.street.streetType=:streetType and a.streetNumber=:number and a.addressParams=:addressParams")
                .setParameter("street", addrInfo.getStreetName())
                .setParameter("streetType",addrInfo.getStreetType())
                .setParameter("number",addrInfo.getStreetNumber())
                .setParameter("addressParams",addrInfo.getAddressParams())
                .getResultList()
                .size();
        return !(existing < 1);
    }

    private Street getStreet(EntityManager em,String streetName, StreetType streetType) {
        return (Street)em.createQuery(
                "select s from eco_Street s where s.name=:street and s.streetType=:streetType")
                .setParameter("street", streetName)
                .setParameter("streetType",streetType).getFirstResult();
    }

}