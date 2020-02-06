package com.magenta.samara.eco.web.screens.street;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Street;

@UiController("eco_Street.browse")
@UiDescriptor("street-browse.xml")
@LookupComponent("streetsTable")
@LoadDataBeforeShow
public class StreetBrowse extends StandardLookup<Street> {
}