package org.dance.dsappbackend;



import org.dance.dsappbackend.dto.CreateTeacherDto;
import org.dance.dsappbackend.dto.TeacherDto;
import org.dance.dsappbackend.entity.Student;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.mapper.TeacherMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeacherMapperTest {
    private final TeacherMapper teacherMapper = new TeacherMapper();

    @Test
    void toTeacherEntityTest(){

        // Given
        CreateTeacherDto dto = new CreateTeacherDto(
                "Pavel",
                "Pavel",
                "Pavlov",
                "Petrovich");


    User mockUser = new User();
    // When
    Teacher result = teacherMapper.toTeacherEntity(dto, mockUser);
    // Then
        assertNotNull(result);
        assertEquals(mockUser, result.getUser(), "ок");
        assertEquals("Pavel",result.getFirstName());
        assertEquals("Pavlov",result.getLastName());
        assertEquals("Petrovich",result.getPatronymic());
    }
    @Test
    void toTeacherDtoTest() {
        // Given
        User mockUser = new User();
        mockUser.setId(2L);

        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setUser(mockUser);
        teacher.setFirstName("Pavel");
        teacher.setLastName("Pavlov");
        teacher.setPatronymic("Petrovich");

        LocalDateTime now = LocalDateTime.now();
        teacher.setCreatedAt(now);
        // When
        TeacherDto result = teacherMapper.toTeacherDto(teacher);
        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(2L, result.getUserId(), "ok");
        assertEquals("Pavel", result.getFirstName());
        assertEquals("Pavlov", result.getLastName());
        assertEquals("Petrovich", result.getPatronymic());
        assertEquals(now, result.getCreatedAt());
    }
}