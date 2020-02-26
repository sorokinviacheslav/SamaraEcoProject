package com.magenta.samara.eco.web.screens.address;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Address;
import com.magenta.samara.eco.entity.Street;
import com.magenta.samara.eco.entity.StreetType;

import javax.inject.Inject;

@UiController("eco_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
@LoadDataBeforeShow
public class AddressEdit extends StandardEditor<Address> {

    @Inject
    private Form streetForm;
    @Inject
    private LookupField<StreetType> streetType;
    @Inject
    private LookupPickerField<Street> streetField;
    @Inject
    private Metadata metadata;
    @Inject
    private TextField<String> streetName;
    @Inject
    private DataManager dataManager;

    @Subscribe("streetField")
    public void onStreetFieldValueChange(HasValue.ValueChangeEvent<Street> event) {
        streetForm.setEnabled(streetField.isEmpty());
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if(streetField.isEmpty()) {
            Street str = metadata.create(Street.class);
            str.setName(streetName.getValue());
            str.setStreetType(streetType.getValue());
            getEditedEntity().setStreet(dataManager.commit(str));
        }
        event.resume();
    }


}