package com.homeproducts.user.query.api.dto;

import com.homeproducts.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLookupResponse {
    private User user;
}
