package com.magenta.samara.eco.web.screens.address;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
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
    private Form form;
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
    @Inject
    private ScreenValidation screenValidation;
    @Inject
    private Notifications notifications;
    @Inject
    private TabSheet tabSheet;

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

    @Subscribe("nextButton")
    public void onNextButtonClick(Button.ClickEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(form.getComponents());
        if(errors.isEmpty()) {
            TabSheet.Tab tab = tabSheet.getTab("additionalTab");
            TabSheet.Tab tabMain = tabSheet.getTab("mainTab");
            tabMain.setEnabled(false);
            tab.setEnabled(true);
            tabSheet.setSelectedTab(tab);
            return;
        }
        for (ValidationErrors.Item er : errors.getAll()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(er.description)
                    .show();
        }
    }

    @Subscribe("backButton")
    public void onBackButtonClick(Button.ClickEvent event) {
        TabSheet.Tab tabAd = tabSheet.getTab("additionalTab");
        TabSheet.Tab tabMain = tabSheet.getTab("mainTab");
        tabAd.setEnabled(false);
        tabMain.setEnabled(true);
        tabSheet.setSelectedTab(tabMain);
    }



}