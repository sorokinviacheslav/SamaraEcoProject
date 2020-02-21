package com.magenta.samara.eco.web.screens.organization;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaPropertyPath;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.magenta.samara.eco.entity.Organization;
import com.magenta.samara.eco.mentionjs.MentionJsExtension;
import com.magenta.samara.eco.sliderjs.SliderServerComponent;
import com.magenta.samara.eco.web.screens.building.BuildingBrowse;
import com.vaadin.ui.Layout;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@UiController("eco_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {

    @Inject
    private CollectionLoader<Organization> organizationsDl;
    @Inject
    private GroupTable<Organization> organizationsTable;
    @Inject
    private DateField<Date> dateField;
    @Inject
    private CheckBox date;
    @Inject
    private CheckBox descr;
    @Inject
    private Metadata metadata;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private PopupButton popupButton;

    @Subscribe("organizationsTable.lookupBuildings")
    public void onOrganizationsTableLookupBuildings(Action.ActionPerformedEvent event) {
        if(organizationsTable.getSingleSelected()!=null) {
            BuildingBrowse screen = screenBuilders.screen(this)
                    .withScreenClass(BuildingBrowse.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .build();
            screen.setOrganization(organizationsTable.getSingleSelected());
            screen.show();
        }
    }

    @Subscribe("organizationsTable")
    public void onOrganizationsTableSelection(Table.SelectionEvent<Organization> event) {
        popupButton.setEnabled(organizationsTable.getSingleSelected()!=null);
    }

    @Subscribe("clear")
    public void onClearClick(Button.ClickEvent event) {
        dateField.setValue(null);
        dropFilter();
    }

    @Subscribe("apply")
    public void onApplyClick(Button.ClickEvent event) {
        if(dateField.getValue()!=null) {
            organizationsDl.setQuery("select o from eco_Organization o where o.date=:date");
            organizationsDl.setParameter("date",dateField.getValue() );
            organizationsDl.load();
        }
        else {
            dropFilter();
        }
    }

    @Subscribe("apply1")
    public void onApply1Click(Button.ClickEvent event) {
        MetaClass userClass = metadata.getClassNN(Organization.class);
        List<Object> props = new ArrayList<>(2);
        if(descr.getValue()) {
            MetaPropertyPath groupNameProp = userClass.getPropertyPath("description");
            props.add(groupNameProp);
        }
        if(date.getValue()) {
            MetaPropertyPath groupNameProp = userClass.getPropertyPath("date");
            props.add(groupNameProp);
        }
        if(props.size()<1) {
            organizationsTable.ungroup();
            return;
        }
        organizationsTable.groupBy(props.toArray());
    }

    @Subscribe("clear1")
    public void onClear1Click(Button.ClickEvent event) {
        organizationsTable.ungroup();
        date.setValue(false);
        descr.setValue(false);
    }

    private void dropFilter() {
        organizationsDl.removeParameter("date");
        organizationsDl.setQuery("select o from eco_Organization o");
        organizationsDl.load();
    }

    /*@Subscribe
    public void onAfterShow(AfterShowEvent event) {
        com.vaadin.ui.TextArea vTextArea = testTextArea.unwrap(com.vaadin.ui.TextArea.class);
        // enable extension
        MentionJsExtension extension = new MentionJsExtension(vTextArea);
        List<String> list=new ArrayList<>();
        for(Organization o:organizationsDc.getItems()) {
            list.add(o.getName());
        }
        extension.setUsers(list);
        SliderServerComponent slider = new SliderServerComponent();
        slider.setValue(new double[]{
               0.0,
                10.0
        });
        slider.setMinValue(0.0);
        slider.setMaxValue(10.0);
        slider.setWidth("250px");*/
        /*slider.setListener(newValue -> {
            getEditedEntity().setMinDiscount(newValue[0]);
            getEditedEntity().setMaxDiscount(newValue[1]);
        });*/

        /*this.getWindow().unwrap(Layout.class).addComponent(slider);
    }*/





    
}