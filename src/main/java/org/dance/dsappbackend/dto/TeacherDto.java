package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;

import java.time.LocalDateTime;


public class TeacherDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDateTime createdAt;

    public TeacherDto() {
    }

    public static TeacherDto from(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.id = teacher.getId();
        dto.userId = teacher.getUser().getId();
        dto.firstName = teacher.getFirstName();
        dto.lastName = teacher.getLastName();
        dto.patronymic = teacher.getPatronymic();
        dto.createdAt = teacher.getCreatedAt();
        return dto;
    }

    public Teacher toEntity(User user) {
        Teacher teacher = new Teacher();
        teacher.setId(this.id);
        teacher.setUser(user);
        teacher.setFirstName(this.firstName);
        teacher.setLastName(this.lastName);
        teacher.setPatronymic(this.patronymic);
        teacher.setCreatedAt(this.createdAt);
        return teacher;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
