package com.homeproducts.user.cmd.api.controllers;

import com.homeproducts.user.cmd.api.commands.RegisterUserCommand;
import com.homeproducts.user.cmd.api.dto.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registerUser")
@RequiredArgsConstructor
public class UserRegisterController {
    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserCommand command) {
        try {
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new RegisterUserResponse("User successfully registered"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing user request for id - " + command.getId();
            log.error(e.getMessage());
            return new ResponseEntity<>(new RegisterUserResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
