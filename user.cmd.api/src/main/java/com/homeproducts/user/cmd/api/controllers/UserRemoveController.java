package com.homeproducts.user.cmd.api.controllers;

import com.homeproducts.user.cmd.api.commands.RemoveUserCommand;
import com.homeproducts.user.core.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/removeUser")
@RequiredArgsConstructor
public class UserRemoveController {

    private final CommandGateway command;

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {
        try {
            command.send(new RemoveUserCommand(id));

            return new ResponseEntity<>(new BaseResponse("User successfully delete with id " + id), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing user request for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

