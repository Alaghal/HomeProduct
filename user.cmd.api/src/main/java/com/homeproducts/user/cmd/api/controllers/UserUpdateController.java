package com.homeproducts.user.cmd.api.controllers;

import com.homeproducts.user.cmd.api.commands.UpdateUserCommand;
import com.homeproducts.user.cmd.api.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/updateUser")
@RequiredArgsConstructor
public class UserUpdateController {

    private final CommandGateway commandGateway;


    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {
        try {
            command.setId(id);
            commandGateway.send(command);

            return new ResponseEntity<>(new BaseResponse("User successfully update"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing user request for id - " + command.getId();
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
