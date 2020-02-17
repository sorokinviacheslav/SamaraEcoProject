package com.magenta.samara.eco.listeners;

import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.magenta.samara.eco.entity.Organization;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component("eco_OrganizationChangedListener")
public class OrganizationChangedListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeCommit(EntityChangedEvent<Organization, UUID> event) {

    }
}