package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;
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
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setUserId(student.getUser().getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setPatronymic(student.getPatronymic());
        dto.setBirthdate(student.getBirthdate());
        dto.setParentName(student.getParentName());
        dto.setPhone(student.getPhone());
        dto.setReferralSource(student.getReferralSource());
        dto.setCreatedAt(student.getCreatedAt());
        return dto;
    }



}
