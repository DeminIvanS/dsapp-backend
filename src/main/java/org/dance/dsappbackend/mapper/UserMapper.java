package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateStudentDto;

import org.dance.dsappbackend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUserEntity (CreateStudentDto dto, String passwordHash, User.Role role) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordHash);
        user.setRole(role);
        user.setActive(true);
        user.setMustChangePassword(true);
        return user;
    }
}
