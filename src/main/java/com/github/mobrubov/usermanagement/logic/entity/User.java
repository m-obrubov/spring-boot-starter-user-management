package com.github.mobrubov.usermanagement.logic.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Table(name = "\"user\"")
@Entity
@EntityListeners({AuditingEntityListener.class})
public class User {
    @Id
    @NotNull
    @Column(name = "guid", nullable = false, unique = true, updatable = false)
    private UUID guid;

    @NotNull
    @Length(max = 256)
    @Column(name = "login", nullable = false, unique = true, length = 256)
    private String login;

    @NotNull
    @Length(max = 60)
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Length(max = 256)
    @Column(name = "nick_name", unique = true, length = 256)
    private String nickName;

    @Email
    @Length(max = 512)
    @Column(name = "email", length = 512)
    private String email;

    @NotNull
    @Length(max = 256)
    @Column(name = "role", nullable = false, length = 256)
    private String role;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    private User lastUpdatedBy;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @NotNull
    @Min(0)
    @Max(Integer.MAX_VALUE)
    @Column(name = "login_count")
    private Integer loginFailCount = 0;

    @NotNull
    @Column(name = "locked", nullable = false)
    private Boolean locked = Boolean.FALSE;

    @NotNull
    @Column(name = "temporal", nullable = false)
    private Boolean temporalPassword = Boolean.FALSE;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;
}
