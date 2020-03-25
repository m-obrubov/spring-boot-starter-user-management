package com.github.mobrubov.usermanagement.rest.ro;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(groups = Constraint.Create.class)
    private LocalDate birthDate;
}
