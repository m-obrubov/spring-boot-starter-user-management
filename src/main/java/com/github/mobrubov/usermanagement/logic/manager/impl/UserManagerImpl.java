package com.github.mobrubov.usermanagement.logic.manager.impl;

import java.util.List;

import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.logic.manager.UserManager;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager {
    @Override
    public User create(User user) {
        return null;
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
