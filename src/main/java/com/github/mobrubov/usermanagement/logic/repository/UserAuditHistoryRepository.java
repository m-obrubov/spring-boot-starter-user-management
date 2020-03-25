package com.github.mobrubov.usermanagement.logic.repository;

import java.util.UUID;

import com.github.mobrubov.usermanagement.logic.entity.UserAuditHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Максим
 * Created on 24.03.2020
 */
@Repository
public interface UserAuditHistoryRepository extends JpaRepository<UserAuditHistoryItem, UUID> {
}
