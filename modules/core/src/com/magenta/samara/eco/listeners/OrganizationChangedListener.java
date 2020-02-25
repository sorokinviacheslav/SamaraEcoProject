package com.magenta.samara.eco.listeners;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.app.UserSessionService;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserSessionEntity;
import com.haulmont.cuba.security.global.UserSession;
import com.magenta.samara.eco.entity.Organization;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.UUID;

@Component("eco_OrganizationChangedListener")
public class OrganizationChangedListener {

    @Inject
    private UserSessionService userSessionService;
    @Inject
    private Persistence persistence;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(EntityChangedEvent<Organization, UUID> event) {
        //userSessionService.killSession(((UserSessionEntity)(userSessionService.loadUserSessionEntities(UserSessionService.Filter.create().setUserLogin("test")).toArray())[0]).getId());
    }
}