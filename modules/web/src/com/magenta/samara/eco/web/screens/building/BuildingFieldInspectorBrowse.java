package com.magenta.samara.eco.web.screens.building;

import com.haulmont.charts.gui.components.map.MapViewer;
import com.haulmont.charts.gui.map.model.InfoWindow;
import com.haulmont.charts.gui.map.model.Marker;
import com.haulmont.charts.gui.map.model.base.MarkerImage;
import com.haulmont.charts.web.gui.components.map.google.base.MarkerImageDelegate;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.magenta.samara.eco.entity.Address;
import com.magenta.samara.eco.entity.Building;
import com.magenta.samara.eco.entity.Organization;
import com.magenta.samara.eco.entity.Street;
import com.magenta.samara.eco.service.AddressManipulationService;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UiController("eco_BuildingFieldInspector.browse")
@UiDescriptor("building-field-inspector-browse.xml")
@LookupComponent("buildingsTable")
@LoadDataBeforeShow
public class BuildingFieldInspectorBrowse extends StandardLookup<Building> {
    @Inject
    private Button createBtnOrg;
    @Inject
    private Button addOrgButton;
    @Inject
    private Button createBtn;
    @Inject
    private LookupPickerField<Street> addressLookupPickerField;
    @Inject
    private GroupTable<Building> buildingsTable;
    @Inject
    private GroupTable<Organization> organizationsTable;
    @Inject
    private LookupField<Address> streetNumberLookupField;

    @Inject
    private MapViewer map;

    @Inject
    private AddressManipulationService addressManipulationService;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<Address> addressesDc;
    @Inject
    private Dialogs dialogs;
    @Inject
    private Messages messages;
    @Inject
    private Notifications notifications;
    @Inject
    private DataManager dataManager;

    @Inject
    private CollectionLoader<Address> addressesDl;
    @Inject
    private CollectionLoader<Building> buildingsDl;
    @Inject
    private CollectionContainer<Building> buildingsDc;

    @Subscribe
    public void onInit(InitEvent event) {
        map.addMarkerClickListener(ev -> {
            Marker marker = ev.getMarker();
            Building bld = addressManipulationService.getBuildingByCoordinates(marker.getPosition().getLatitude(),marker.getPosition().getLongitude());
            if(bld==null) return;
            addressesDc.getItems().forEach(a->{
                if (a.getCoordinates().getLatitude().compareTo(bld.getAddress().getCoordinates().getLatitude())==0&&a.getCoordinates().getLongitude().compareTo(bld.getAddress().getCoordinates().getLongitude())==0) {
                    streetNumberLookupField.setValue(a);
                    buildingsTable.setSelected(bld);
                }
            });
            StringBuilder caption = new StringBuilder()
                    .append("Адрес: ")
                    .append(messages.getMessage(bld.getAddress().getStreet().getStreetType()))
                    .append(" ")
                    .append(bld.getAddress().getStreet().getName())
                    .append(" ")
                    .append(bld.getAddress().getStreetNumber())
                    .append(System.lineSeparator())
                    .append("Описание: ")
                    .append(bld.getDescription())
                    .append(System.lineSeparator())
                    .append("Организации: ")
                    .append(System.lineSeparator());
            if(bld.getOrganizations()==null||bld.getOrganizations().size()<1) {
                caption.append("Нет организаций");
            }
            else {
                for (Organization org : bld.getOrganizations()) {
                    caption.append(org.getName())
                            .append(" ИНН: ")
                            .append(org.getInn())
                            .append(System.lineSeparator())
                            .append(org.getDescription())
                            .append(System.lineSeparator());
                }
            }
            InfoWindow w = map.createInfoWindow(caption.toString(), marker);
            map.openInfoWindow(w);
        });

        streetNumberLookupField.setNewOptionHandler(caption -> {
            int number =0;
            try {
                number = Integer.parseInt(caption);
            }
            catch(Exception ex) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption("Неверный формат номера дома! Проверьте ввод!")
                        .show();
                streetNumberLookupField.setValue(null);
                return;
            }
            if(number<=0||number>800) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption("Неверный формат номера дома! Проверьте ввод!")
                        .show();
                streetNumberLookupField.setValue(null);
                return;
            }
            AddressManipulationService.AddressInfo addrInfo =
                    new AddressManipulationService.AddressInfo(
                            addressLookupPickerField.getValue().getName(),
                            addressLookupPickerField.getValue().getStreetType(),
                            number,
                            ""
                    );

            if(addressManipulationService.isAddressInDB(addrInfo)) {
                addressesDc.getItems().forEach(a->{
                    if (a.getStreetNumber().intValue() == Integer.parseInt(caption)) {
                        streetNumberLookupField.setValue(a);
                    }
                });
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption("Данный адрес уже существует!")
                        .show();
                return;
            }
            Address addr = metadata.create(Address.class);
            addr.setStreet(addressLookupPickerField.getValue());
            addr.setStreetNumber(number);
            /*addressesDc.getMutableItems().add(addr);*/
            streetNumberLookupField.setValue(addr);
        });
    }

    private void updateMapMarkers(List<Building> blds) {
        if(blds==null||blds.size()<1) return;
        map.clearMarkers();
        for(Building b:blds) {
            Marker marker = map.createMarker(b.getDescription(), map.createGeoPoint(b.getAddress().getCoordinates().getLatitude(), b.getAddress().getCoordinates().getLongitude()), false);
            marker.setClickable(true);
            MarkerImage imRed =map.createMarkerImage("https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color/254000/67-512.png");
            MarkerImage imGreen =map.createMarkerImage("https://cdn1.iconfinder.com/data/icons/basic-ui-elements-coloricon/21/06_1-512.png");
            imRed.setScaledSize(map.createSize(50.0,50.0));
            imGreen.setScaledSize(map.createSize(40.0,40.0));
            if(b.getOrganizations()==null||b.getOrganizations().size()<1) {
                marker.setIcon(imRed);
            }
            else {
                marker.setIcon(imGreen);
            }
            map.addMarker(marker);
        }
    }

    @Subscribe("addressLookupPickerField")
    public void onAddressLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Street> event) {
        map.clearMarkers();
        streetNumberLookupField.setValue(null);
    }

    @Subscribe("buildingsTable")
    public void onBuildingsTableSelection(Table.SelectionEvent<Building> event) {
        if(!event.isUserOriginated()||event.getSource().getSingleSelected()==null) return;
        map.setCenter(map.createGeoPoint(event.getSource().getSingleSelected().getAddress().getCoordinates().getLatitude(), event.getSource().getSingleSelected().getAddress().getCoordinates().getLongitude()));
    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        Street str = addressLookupPickerField.getValue();
        Address addr = streetNumberLookupField.getValue();
        AddressManipulationService.AddressInfo addrInfo =
                new AddressManipulationService.AddressInfo(
                        str.getName(),
                        str.getStreetType(),
                        addr.getStreetNumber(),
                        ""
                );
        if(addressManipulationService.isAddressInDB(addrInfo)) {
           notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("По данному адресу уже есть здание!")
                    .show();
            return;
        }
        dialogs.createInputDialog(this)
                .withCaption("Добавить здание по адресу: "+messages.getMessage(str.getStreetType())+" "+str.getName()+" "+addr.getStreetNumber())
                .withParameter(
                        InputParameter.stringParameter("description").withCaption("Описание")
                )
                .withValidator(context -> {
                    /*String name = context.getValue("name");
                    Customer customer = context.getValue("customer");
                    if (Strings.isNullOrEmpty(name) && customer == null) {
                        return ValidationErrors.of("Enter name or select a customer");
                    }*/
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.getCloseAction().equals(InputDialog.INPUT_DIALOG_OK_ACTION)) {
                        String desc = closeEvent.getValue("description");
                        if(!addressManipulationService.addNewBuilding(addrInfo,new AddressManipulationService.BuildingInfo(desc))) {
                            notifications.create(Notifications.NotificationType.WARNING)
                                    .withCaption("По данному адресу нет здания! Проверьте правильность информации или обновите страницу!")
                                    .show();
                            return;
                        }
                        notifications.create(Notifications.NotificationType.TRAY)
                                .withCaption("Здание успешно добавлено!!")
                                .show();
                        addressesDl.load();
                        addressesDc.getItems().forEach(a->{
                            if(a.getStreetNumber().intValue()==addrInfo.getStreetNumber()) {
                                streetNumberLookupField.setValue(a);
                            }
                        });
                    }
                })
                .show();
    }

    @Subscribe("createBtnOrg")
    public void onCreateBtnOrgClick(Button.ClickEvent event) {
        screenBuilders.editor(organizationsTable)
                .newEntity()
                .withInitializer(org -> {
                    Set<Building> set = new HashSet<>();
                    set.add(buildingsTable.getSingleSelected());
                    org.setBuildings(set);
                })
                .withLaunchMode(OpenMode.DIALOG)
                .build()
                .show();
    }

    @Subscribe("organizationsTable.addOrg")
    public void onOrganizationsTableAddOrg(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(organizationsTable)
                .withOpenMode(OpenMode.DIALOG)
                .build()
                .show();
    }

        @Subscribe(id = "buildingsDc", target = Target.DATA_CONTAINER)
    public void onBuildingsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Building> event) {
        if(event.getChangeType().equals(CollectionChangeType.REMOVE_ITEMS)) {
            addressesDl.load();
        }
        createBtn.setEnabled((event.getSource().getItems()==null||event.getSource().getItems().size()<1)&&streetNumberLookupField.getValue()!=null);
        if(addressLookupPickerField.getValue()==null) {
            map.clearMarkers();
        }
        else if(streetNumberLookupField.getValue()==null) {
            updateMapMarkers(event.getSource().getItems());
        }
    }

    @Subscribe(id = "organizationsDc", target = Target.DATA_CONTAINER)
    public void onOrganizationsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Organization> event) {
        if(event.getChangeType().equals(CollectionChangeType.SET_ITEM)) {
            buildingsDl.load();
        }
        if(event.getChangeType().equals(CollectionChangeType.ADD_ITEMS)) {
            event.getChanges().forEach(org->{
                Set<Building> set = new HashSet<>();
                set.add(buildingsTable.getSingleSelected());
                org.setBuildings(set);
                dataManager.commit(org);
            });
            //buildingsDl.load();
        }
        createBtnOrg.setEnabled(buildingsTable.getSingleSelected()!=null);
        addOrgButton.setEnabled(buildingsTable.getSingleSelected()!=null);
    }

}