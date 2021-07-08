package com.homeproducts.user.query.api.controllers;

import com.homeproducts.user.query.api.dto.UserLookupResponse;
import com.homeproducts.user.query.api.queries.FindAllUserQuery;
import com.homeproducts.user.query.api.queries.FindUserByIDQuery;
import com.homeproducts.user.query.api.queries.SearchUserQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/userLookup")
@RequiredArgsConstructor
public class UserLookupController {
    private final QueryGateway gateway;

    @GetMapping(path = "/")
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            var response = gateway.query(new FindAllUserQuery(),
                    ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();

            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(new UserLookupResponse("Not found users"),
                        HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error failed get all users";
            log.error(e.toString());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable(value = "id") String id) {
        try {

            var response = gateway.query(new FindUserByIDQuery(id),
                    ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();

            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(new UserLookupResponse("Not found user by id " + id), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error failed get user by id";
            log.error(e.toString());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byFilter/{filter}")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable(value = "filter") String filter) {
        try {

            var response = gateway.query(new SearchUserQuery(filter),
                    ResponseTypes.instanceOf(UserLookupResponse.class))
                    .join();

            if (response == null || response.getUsers() == null) {
                return new ResponseEntity<>(new UserLookupResponse("Not found user by filter " + filter), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error failed get user by filter";
            log.error(e.toString());
            return new ResponseEntity<>(new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
