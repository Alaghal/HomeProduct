package com.homeproducts.user.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUserQuery {
    private String filter;
}
