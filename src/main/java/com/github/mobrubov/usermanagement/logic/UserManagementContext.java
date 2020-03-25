package com.github.mobrubov.usermanagement.logic;

import java.util.UUID;

/**
 * @author Максим
 * Created on 24.03.2020
 */
public class UserManagementContext {
    private static ThreadLocal<UUID> currentUserGuid = new ThreadLocal<>();

    public static void setCurrentUser(String guid) {
        setCurrentUser(UUID.fromString(guid));
    }

    public static void setCurrentUser(UUID guid) {
        currentUserGuid.set(guid);
    }

    public static UUID getCurrentUser() {
        return currentUserGuid.get();
    }
}
