package com.github.mobrubov.usermanagement.logic.manager.impl;

import java.util.List;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import com.github.mobrubov.usermanagement.common.exception.UserManagementException;
import com.github.mobrubov.usermanagement.logic.UserManagementContext;
import com.github.mobrubov.usermanagement.logic.entity.AuditOperation;
import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.entity.UserAuditHistoryItem;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.logic.repository.UserAuditHistoryRepository;
import com.github.mobrubov.usermanagement.logic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;
    private final UserAuditHistoryRepository userAuditHistoryRepository;

    @Override
    public User create(User user) {
        if(Strings.isBlank(user.getPassword())) {

        }

        User savedUser = userRepository.save(user);
        User author = userRepository.findById(UserManagementContext.getCurrentUser())
            .orElseThrow(() -> new UserManagementException(ErrorCode.INCORRECT_CURRENT_USER));

        UserAuditHistoryItem createHistoryItem = new UserAuditHistoryItem();
        createHistoryItem.setAuthor(author);
        createHistoryItem.setTarget(savedUser);
        createHistoryItem.setOperation(AuditOperation.CREATED);
        userAuditHistoryRepository.save(createHistoryItem);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getOne(String guid) {
        return null;
    }

    @Override
    public void update(String guid, User user) {

    }

    @Override
    public void delete(String guid) {

    }
}
