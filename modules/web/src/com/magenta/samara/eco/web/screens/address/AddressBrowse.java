package com.magenta.samara.eco.web.screens.address;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Address;

@UiController("eco_Address.browse")
@UiDescriptor("address-browse.xml")
@LookupComponent("addressesTable")
@LoadDataBeforeShow
public class AddressBrowse extends StandardLookup<Address> {
}