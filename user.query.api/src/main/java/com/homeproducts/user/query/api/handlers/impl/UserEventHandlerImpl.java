package com.homeproducts.user.query.api.handlers.impl;

import com.homeproducts.user.core.events.UserRegisteredEvent;
import com.homeproducts.user.core.events.UserRemovedEvent;
import com.homeproducts.user.core.events.UserUpdatedEvent;
import com.homeproducts.user.query.api.handlers.UserEventHandler;
import com.homeproducts.user.query.api.repositores.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository repository;

    @EventHandler
    @Override
    public void on(UserRegisteredEvent event) {
        this.repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent event) {
        this.repository.save(event.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent event) {
        this.repository.deleteById(event.getId());
    }
}
