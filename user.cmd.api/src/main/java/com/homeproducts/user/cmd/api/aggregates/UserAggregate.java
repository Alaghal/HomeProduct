package com.homeproducts.user.cmd.api.aggregates;

import com.homeproducts.user.cmd.api.commands.RegisterUserCommand;
import com.homeproducts.user.cmd.api.commands.RemoveUserCommand;
import com.homeproducts.user.cmd.api.commands.UpdateUserCommand;
import com.homeproducts.user.cmd.api.security.PasswordEncoder;
import com.homeproducts.user.cmd.api.security.impl.PasswordEncoderImpl;
import com.homeproducts.user.core.events.UserRegisteredEvent;
import com.homeproducts.user.core.events.UserRemovedEvent;
import com.homeproducts.user.core.events.UserUpdatedEvent;
import com.homeproducts.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {
    @TargetAggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder encoder;

    public UserAggregate() {
        this.encoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command, PasswordEncoder encoder) {
        this.encoder = new PasswordEncoderImpl();
        var newUser = command.getUser();
        newUser.setId(command.getId());
        var password = command.getUser().getAccount().getPassword();
        var hashPassword = encoder.hashPassword(password);
        newUser.getAccount().setPassword(hashPassword);

        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        var updateUser = command.getUser();
        updateUser.setId(command.getId());

        var password = command.getUser().getAccount().getPassword();
        var hashPassword = encoder.hashPassword(password);
        updateUser.getAccount().setPassword(hashPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updateUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        var event = UserRemovedEvent.builder().build();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
