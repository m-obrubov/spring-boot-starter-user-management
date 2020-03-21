package com.github.mobrubov.usermanagement.rest.ro;

import com.github.mobrubov.usermanagement.rest.Constraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserRo {
    private String guid;
    @NotBlank(groups = Constraint.Create.class)
    private String firstName;
    @NotBlank(groups = Constraint.Create.class)
    private String lastName;
    private String patronymic;
    @NotNull(groups = Constraint.Create.class)
    private LocalDate birthDate;
}
