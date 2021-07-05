package com.homeproducts.user.query.api.handlers.impl;

import com.homeproducts.user.core.models.User;
import com.homeproducts.user.query.api.dto.UserLookupResponse;
import com.homeproducts.user.query.api.handlers.UserQueryHandler;
import com.homeproducts.user.query.api.queries.FindAllUserQuery;
import com.homeproducts.user.query.api.queries.FindUserByIDQuery;
import com.homeproducts.user.query.api.queries.SearchUserQuery;
import com.homeproducts.user.query.api.repositores.UserRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository repository;

    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIDQuery query) {
        var user = repository.findById(query.getId()).orElse(new User());
        return new UserLookupResponse(user);
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUserQuery query) {
        var users = repository.findAll();
        return new UserLookupResponse(users);
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUser(SearchUserQuery query) {
        var users = repository.findByFilterRegex(query.getFilter());
        return new UserLookupResponse(users);
    }
}
