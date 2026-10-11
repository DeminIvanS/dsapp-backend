package org.dance.dsappbackend;

import org.dance.dsappbackend.dto.CreatedUserDto;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();
    @Test
    void toUserEntity(){
        // Given
        CreatedUserDto dto = new CreatedUserDto("vova", "KID-123456");

        String passwordHash = "$2a$10$xyzKID123456HashStringExample";
        User.Role role = User.Role.ROLE_STUDENT;
        // When
        User result = userMapper.toUserEntity(dto, passwordHash, role);
        // Then
        assertNotNull(result);
        assertEquals("vova", result.getUsername());
        assertEquals(passwordHash, result.getPassword());
        assertEquals(role, result.getRole());
        assertTrue(result.isActive());
        assertTrue(result.shouldChangePassword());
    }
}
