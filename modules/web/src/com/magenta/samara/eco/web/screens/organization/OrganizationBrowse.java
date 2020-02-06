package com.magenta.samara.eco.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Organization;

@UiController("eco_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {
}