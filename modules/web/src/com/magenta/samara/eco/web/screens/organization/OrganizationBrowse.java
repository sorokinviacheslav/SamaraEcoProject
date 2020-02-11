package com.magenta.samara.eco.web.screens.organization;

import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.magenta.samara.eco.entity.Organization;
import com.magenta.samara.eco.mentionjs.MentionJsExtension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("eco_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {

    @Inject
    private TextArea<String> testTextArea;
    @Inject
    private CollectionContainer<Organization> organizationsDc;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        com.vaadin.ui.TextArea vTextArea = testTextArea.unwrap(com.vaadin.ui.TextArea.class);
        // enable extension
        MentionJsExtension extension = new MentionJsExtension(vTextArea);
        List<String> list=new ArrayList<>();
        for(Organization o:organizationsDc.getItems()) {
            list.add(o.getName());
        }
        extension.setUsers(list);
    }





    
}