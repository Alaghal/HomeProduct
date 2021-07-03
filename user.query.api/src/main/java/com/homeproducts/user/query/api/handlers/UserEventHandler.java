package com.homeproducts.user.query.api.handlers;

import com.homeproducts.user.core.events.UserRegisteredEvent;
import com.homeproducts.user.core.events.UserRemovedEvent;
import com.homeproducts.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);

    void on(UserUpdatedEvent event);

    void on(UserRemovedEvent event);
}
