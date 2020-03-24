package com.github.mobrubov.usermanagement.logic.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Data
@Table(name = "user_audit_history")
@Entity
public class UserAuditHistoryItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "guid", nullable = false, unique = true, updatable = false)
    private UUID guid;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "target_user_guid", nullable = false, updatable = false)
    private User target;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_user_guid", nullable = false, updatable = false)
    private User author;

    @CreatedDate
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, updatable = false)
    private AuditOperation operation;

    @Length(max = 1024)
    @Column(name = "details", updatable = false, length = 1024)
    private String details;
}
