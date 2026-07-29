package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
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
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setUserId(teacher.getUser().getId());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setPatronymic(teacher.getPatronymic());
        return dto;
    }
    public User toUserEntity (CreateTeacherDto dto, String passwordHash) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordHash);
        user.setRole(User.Role.ROLE_TEACHER);
        user.setActive(true);
        user.setMustChangePassword(true);
        return user;
    }



}
