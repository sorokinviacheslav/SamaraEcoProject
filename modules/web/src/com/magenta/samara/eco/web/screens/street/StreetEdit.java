package com.magenta.samara.eco.web.screens.street;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Street;

@UiController("eco_Street.edit")
@UiDescriptor("street-edit.xml")
@EditedEntityContainer("streetDc")
@LoadDataBeforeShow
public class StreetEdit extends StandardEditor<Street> {
}