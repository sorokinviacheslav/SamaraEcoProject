package com.magenta.samara.eco.service;

import com.magenta.samara.eco.entity.Building;
import com.magenta.samara.eco.entity.StreetType;

import java.io.IOException;
import java.io.Serializable;

public interface AddressManipulationService {
    String NAME = "eco_AddressManipulationService";

    boolean addNewBuilding(AddressInfo addrInfo, BuildingInfo bldInfo);
    boolean isAddressInDB(AddressInfo addrInfo);
    Building getBuildingByCoordinates(Double latitude, Double longitude);

    class AddressInfo implements Serializable {
        private String streetName;
        private StreetType streetType;
        private int streetNumber;
        private String addressParams;

        public AddressInfo(String streetName, StreetType streetType, int streetNumber, String addressParams) {
            this.streetName = streetName;
            this.streetType = streetType;
            this.streetNumber = streetNumber;
            this.addressParams = addressParams;
        }

        public String getAddressParams() {
            return addressParams;
        }

        public void setAddressParams(String addressParams) {
            this.addressParams = addressParams;
        }

        public int getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(int streetNumber) {
            this.streetNumber = streetNumber;
        }

        public StreetType getStreetType() {
            return streetType;
        }

        public void setStreetType(StreetType streetType) {
            this.streetType = streetType;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }
    }

    class BuildingInfo implements Serializable{
        private String description;

        public BuildingInfo(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}