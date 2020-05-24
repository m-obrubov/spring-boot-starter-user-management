package com.github.mobrubov.usermanagement.rest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.mobrubov.usermanagement.logic.entity.User;
import com.github.mobrubov.usermanagement.rest.ro.UserRo;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "nickName", source = "user")
    UserRo mapFull(User user);

    default List<UserRo> mapFull(List<User> source) {
        if(source == null) {
            return null;
        }
        List<UserRo> list = new ArrayList<>(source.size());
        for(User from : source) {
            list.add(mapFull(from));
        }
        return list;
    }

    @Mapping(target = "login", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "nickName", source = "user")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "lastUpdatedBy", ignore = true)
    @Mapping(target = "lastLoginDate", ignore = true)
    @Mapping(target = "loginFailCount", ignore = true)
    @Mapping(target = "temporalPassword", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    UserRo map(User user);

    default List<UserRo> map(List<User> source) {
        if(source == null) {
            return null;
        }
        List<UserRo> list = new ArrayList<>(source.size());
        for(User from : source) {
            list.add(map(from));
        }
        return list;
    }

    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "lastUpdatedBy", ignore = true)
    @Mapping(target = "lastLoginDate", ignore = true)
    @Mapping(target = "loginFailCount", ignore = true)
    @Mapping(target = "temporalPassword", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User mapCreate(UserRo userRo);

    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "lastUpdatedBy", ignore = true)
    @Mapping(target = "lastLoginDate", ignore = true)
    @Mapping(target = "loginFailCount", ignore = true)
    @Mapping(target = "temporalPassword", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User mapUpdate(UserRo userRo);

    default String uuid(UUID uuid) {
        return uuid.toString();
    }

    default String resolveNickName(User user) {
        if(StringUtils.isNotBlank(user.getNickName())) {
            return user.getNickName();
        }
        if(StringUtils.isNotBlank(user.getEmail())) {
            return user.getEmail();
        }
        return user.getLogin();
    }
}
