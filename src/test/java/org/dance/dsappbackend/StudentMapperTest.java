package org.dance.dsappbackend;


import org.dance.dsappbackend.dto.CreateStudentDto;
import org.dance.dsappbackend.dto.StudentDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.mapper.StudentMapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class StudentMapperTest {
    private final StudentMapper studentMapper = new StudentMapper();
    @Test
    void toStudentEntity(){
        // Given
        CreateStudentDto dto = new CreateStudentDto(
                "vova",
                "vova",
                "petrov",
                "vasil'evich",
                LocalDate.of(2019, 1, 1),
                "Vasily",
                "+79991234567",
                "telega");

        User mockUser = new User();
        // When
        Student result = studentMapper.toStudentEntity(dto, mockUser);
        // Then
        assertNotNull(result);
        assertEquals(mockUser, result.getUser(), "ок");
        assertEquals("vova", result.getFirstName());
        assertEquals("petrov", result.getLastName());
        assertEquals("vasil'evich", result.getPatronymic());
        assertEquals(LocalDate.of(2019, 1, 1), result.getBirthdate());
        assertEquals("Vasily", result.getParentName());
        assertEquals("+79991234567", result.getPhone());
        assertEquals("telega", result.getReferralSource());
    }
    @Test
    void toStudentDtoTest() {
        // Given
        User mockUser = new User();
        mockUser.setId(2L);

        Student student = new Student();
        student.setId(42L);
        student.setUser(mockUser);
        student.setFirstName("vova");
        student.setLastName("petrov");
        student.setPatronymic("vasil'evich");
        student.setBirthdate(LocalDate.of(2019, 1, 1));
        student.setParentName("Vasily");
        student.setPhone("+79991234567");
        student.setReferralSource("telega");

        LocalDateTime now = LocalDateTime.now();
        student.setCreatedAt(now);

        // When
        StudentDto result = studentMapper.toStudentDto(student);

        // Then
        assertNotNull(result);
        assertEquals(42L, result.getId());
        assertEquals(2L, result.getUserId(), "ok");
        assertEquals("vova", result.getFirstName());
        assertEquals("petrov", result.getLastName());
        assertEquals("vasil'evich", result.getPatronymic());
        assertEquals(LocalDate.of(2019, 1, 1), result.getBirthdate());
        assertEquals("Vasily", result.getParentName());
        assertEquals("+79991234567", result.getPhone());
        assertEquals("telega", result.getReferralSource());
        assertEquals(now, result.getCreatedAt());
    }

}
