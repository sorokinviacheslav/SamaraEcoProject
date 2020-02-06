package com.magenta.samara.eco.web.screens.building;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Building;

@UiController("eco_Building.edit")
@UiDescriptor("building-edit.xml")
@EditedEntityContainer("buildingDc")
@LoadDataBeforeShow
public class BuildingEdit extends StandardEditor<Building> {


}