package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher toTeacherEntity(CreateTeacherDto dto, User user){
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setFirstName(dto.firstName());
        teacher.setLastName(dto.lastName());
        teacher.setPatronymic(dto.patronymic());

        return teacher;
    }
    public TeacherDto toTeacherDto (Teacher teacher){
        return new TeacherDto(
                teacher.getId(),
                teacher.getUser().getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getPatronymic(),
                teacher.getCreatedAt());
    }
    public User toUserEntity(CreateTeacherDto dto, String password){
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(password);
        user.setRole(Role.ROLE_TEACHER);
        user.setActive(true);
        user.setShouldChangePassword(true);
        return user;
    }

}
