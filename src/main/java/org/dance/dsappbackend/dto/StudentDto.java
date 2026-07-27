package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthdate;
    private String parentName;
    private String phone;
    private String referralSource;
    private LocalDateTime createdAt;


    public StudentDto() {
    }

    public static StudentDto from(Student student){
        StudentDto dto = new StudentDto();
        dto.id = student.getId();
        dto.userId = student.getUser().getId();
        dto.firstName = student.getFirstName();
        dto.lastName = student.getLastName();
        dto.patronymic = student.getPatronymic();
        dto.birthdate = student.getBirthdate();
        dto.parentName = student.getParentName();
        dto.phone = student.getPhone();
        dto.referralSource = student.getReferralSource();
        dto.createdAt = student.getCreatedAt();
        return dto;
    }
    public Student toEntity(User user){
        Student student = new Student();
        student.setId(this.id);
        student.setUser(user);
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        student.setPatronymic(this.patronymic);
        student.setBirthdate(this.birthdate);
        student.setParentName(this.parentName);
        student.setPhone(this.phone);
        student.setReferralSource(this.referralSource);
        student.setCreatedAt(this.createdAt);
        return student;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
