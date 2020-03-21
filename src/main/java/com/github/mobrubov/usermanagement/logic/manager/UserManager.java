package com.github.mobrubov.usermanagement.logic.manager;

import com.github.mobrubov.usermanagement.logic.entity.User;

import java.util.List;

public interface UserManager {
    User create(User user);
    List<User> getAll();
    User getOne(String guid);
    void update(String guid, User user);
    void delete(String guid);
}
