package com.homeproducts.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "users")
@Builder
public class User {

    @Id
    private String id;
    @NotEmpty(message = "FirstName is mandatory")
    private String firstName;
    @NotEmpty(message = "lastName is mandatory")
    private String lastName;
    @Email(message = "please provide a valid email Address")
    private String emailAddress;
    @NotNull(message = "please provide credentials")
    @Valid
    private Account account;
}
