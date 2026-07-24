package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;

public record CreateTeacherDto(
        String username,
        String firstName,
        String lastName,
        String patronymic
)
{

    public User toUserEntity(String password){
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(password);
        user.setRole(User.Role.ROLE_TEACHER);
        user.setActive(true);
        user.setMustChangePassword(true);
        return user;
    }

    public Teacher toTeacherEntity(User user){
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setFirstName(this.firstName);
        teacher.setLastName(this.lastName);
        teacher.setPatronymic(this.patronymic);
        return teacher;
    }
}
