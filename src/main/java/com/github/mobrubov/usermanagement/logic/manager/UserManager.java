package com.github.mobrubov.usermanagement.logic.manager;

import java.util.List;
import java.util.UUID;

import com.github.mobrubov.usermanagement.logic.entity.User;

public interface UserManager {
    User create(User user);
    List<User> getAll();
    User getOne(UUID guid);
    void update(UUID guid, User user);
    void delete(UUID guid);

    boolean exists(String login);
    boolean exists(UUID guid);

    String getCurrentUserName();
}
