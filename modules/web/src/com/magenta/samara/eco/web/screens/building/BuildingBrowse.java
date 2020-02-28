package com.magenta.samara.eco.web.screens.building;

import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.data.aggregation.AggregationStrategy;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Building;
import com.magenta.samara.eco.entity.Organization;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@UiController("eco_Building.browse")
@UiDescriptor("building-browse.xml")
@LookupComponent("buildingsTable")
@LoadDataBeforeShow
public class BuildingBrowse extends StandardLookup<Building> {

    @WindowParam
    private Organization org;
    @Inject
    private CollectionLoader<Building> buildingsDl;

    public void setOrganization(Organization org) {
       this.org=org;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (org != null) {
            buildingsDl.setQuery("select b from eco_Building b join b.organizations o where o=:org");
            buildingsDl.setParameter("org",org);
            buildingsDl.load();
        }
    }
   }