package com.homeproducts.user.query.api.handlers;

import com.homeproducts.user.query.api.dto.UserLookupResponse;
import com.homeproducts.user.query.api.queries.FindAllUserQuery;
import com.homeproducts.user.query.api.queries.FindUserByIDQuery;
import com.homeproducts.user.query.api.queries.SearchUserQuery;

public interface UserQueryHandler {
    UserLookupResponse getUserById(FindUserByIDQuery query);

    UserLookupResponse getAllUsers(FindAllUserQuery query);

    UserLookupResponse searchUser(SearchUserQuery query);
}
