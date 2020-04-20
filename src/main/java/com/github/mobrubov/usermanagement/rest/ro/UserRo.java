package com.github.mobrubov.usermanagement.rest.ro;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.github.mobrubov.usermanagement.rest.Constraint;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRo {
    // full get
    // get
    private String guid;

    // full get
    // create
    @Length(max = 256, groups = Constraint.Create.class)
    @NotBlank(groups = Constraint.Create.class)
    private String login;

    // create
    // update
    @Length(max = 256, groups = { Constraint.Create.class, Constraint.Update.class })
    private String password;

    // full get
    // get
    // create
    // update
    @Length(max = 256, groups = { Constraint.Create.class, Constraint.Update.class })
    private String nickName;

    // full get
    // get
    // create
    // update
    @Email
    @Length(max = 512, groups = { Constraint.Create.class, Constraint.Update.class })
    private String email;

    // full get
    // create
    // update
    @NotBlank(groups = Constraint.Create.class)
    @Length(max = 256, groups = { Constraint.Create.class, Constraint.Update.class })
    private String role;

    // full get
    // update
    private Boolean locked;

    // full get
    private LocalDateTime createDate;

    // full get
    private String createdBy;

    // full get
    private LocalDateTime lastUpdateDate;

    // full get
    private String lastUpdatedBy;

    // full get
    private LocalDateTime lastLoginDate;

    // full get
    private Integer loginFailCount;

    // full get
    private Boolean temporal;

    // full get
    private Boolean deleted;
}
