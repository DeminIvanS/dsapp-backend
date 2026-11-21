package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toStudentEntity(CreateStudentDto dto, User user){
        Student student = new Student();
        student.setUser(user);
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setPatronymic(dto.patronymic());
        student.setBirthdate(dto.birthdate());
        student.setParentName(dto.parentName());
        student.setPhone(dto.phone());
        student.setReferralSource(dto.referralSource());

        return student;
    }
    public StudentDto toStudentDto (Student student){
               return new StudentDto(
                student.getId(),
                student.getUser().getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPatronymic(),
                student.getBirthdate(),
                student.getParentName(),
                student.getPhone(),
                student.getReferralSource(),
                student.getCreatedAt()
        );
    }
    public User toUserEntity(CreateStudentDto dto, String password){
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(password);
        user.setRole(Role.ROLE_STUDENT);
        user.setActive(true);
        user.setShouldChangePassword(true);
        return user;
    }


}
