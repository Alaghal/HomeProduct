package com.homeproducts.user.core.events;

import com.homeproducts.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserRemovedEvent {
    private String id;
    private User user;
}
