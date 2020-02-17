package com.magenta.samara.eco.web.screens.building;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Building;

@UiController("eco_Building.browse")
@UiDescriptor("building-browse.xml")
@LookupComponent("buildingsTable")
@LoadDataBeforeShow
public class BuildingBrowse extends StandardLookup<Building> {
}