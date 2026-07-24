package org.dance.dsappbackend.mappers;

import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;

import java.time.LocalDate;

public record StudentMapper(
        String username,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate birthdate,
        String parentName,
        String phone,
        String referralSource
) {
    public User toUserEntity(String password) {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(password);
        user.setRole(User.Role.ROLE_STUDENT);
        user.setActive(true);
        user.setMustChangePassword(true);
        return user;
    }

    public Student toStudentEntity(User user) {
        Student student = new Student();
        student.setUser(user);
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        student.setPatronymic(this.patronymic);
        student.setBirthdate(this.birthdate);
        student.setParentName(this.parentName);
        student.setPhone(this.phone);
        student.setReferralSource(this.referralSource);
        return student;
    }

}
