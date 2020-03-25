package com.github.mobrubov.usermanagement.rest.ro;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.github.mobrubov.usermanagement.rest.Constraint;
import lombok.Data;

@Data
public class UserRo {
    private String guid;
    @NotBlank(groups = Constraint.Create.class)
    private String firstName;
    @NotBlank(groups = Constraint.Create.class)
    private String lastName;
    @NotBlank(groups = Constraint.Create.class)
    private String login;
    private String nickName;
    @Email
    private String email;
    @NotBlank
    private String role;
}
