package com.homeproducts.user.oauth20.repositories;

import com.homeproducts.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    //   @Query("{'account.username': ?0}")
    Optional<User> findUserByAccount_Username(String username);

}
