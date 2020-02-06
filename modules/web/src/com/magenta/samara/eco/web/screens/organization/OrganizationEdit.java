package com.magenta.samara.eco.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Organization;

@UiController("eco_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
@LoadDataBeforeShow
public class OrganizationEdit extends StandardEditor<Organization> {
}