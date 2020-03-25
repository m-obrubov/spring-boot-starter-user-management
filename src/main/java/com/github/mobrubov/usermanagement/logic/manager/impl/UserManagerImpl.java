package com.github.mobrubov.usermanagement.logic.manager.impl;

import java.util.List;
import java.util.UUID;

import com.github.mobrubov.usermanagement.common.exception.ErrorCode;
import com.github.mobrubov.usermanagement.common.exception.UserManagementException;
import com.github.mobrubov.usermanagement.logic.UserManagementContext;
import com.github.mobrubov.usermanagement.logic.entity.AuditOperation;
import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.entity.UserAuditHistoryItem;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import com.github.mobrubov.usermanagement.logic.repository.UserAuditHistoryRepository;
import com.github.mobrubov.usermanagement.logic.repository.UserRepository;
import com.github.mobrubov.usermanagement.logic.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserManagerImpl implements UserManager {
    private final UserRepository userRepository;
    private final UserAuditHistoryRepository userAuditHistoryRepository;
    private final PasswordUtils passwordUtils;

    @Override
    public User create(User user) {
        User author = userRepository.findById(UserManagementContext.getCurrentUser())
            .orElseThrow(() -> new UserManagementException(ErrorCode.INCORRECT_CURRENT_USER));

        user.setGuid(UUID.randomUUID());
        if(Strings.isBlank(user.getPassword())) {
            user.setPassword(passwordUtils.generatePassword());
            user.setTemporal(Boolean.TRUE);
        }
        if(Strings.isBlank(user.getNickName())) {
            user.setNickName(user.getLogin());
        }
        User savedUser = userRepository.save(user);

        UserAuditHistoryItem createHistoryItem = new UserAuditHistoryItem();
        createHistoryItem.setAuthor(author);
        createHistoryItem.setTarget(savedUser);
        createHistoryItem.setOperation(AuditOperation.CREATED);
        userAuditHistoryRepository.save(createHistoryItem);
        return savedUser;
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
