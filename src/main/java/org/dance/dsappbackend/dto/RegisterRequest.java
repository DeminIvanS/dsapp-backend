package org.dance.dsappbackend.dto;


import org.dance.dsappbackend.enums.Role;

public record RegisterRequest(

        String username,
        String password,
        Role role
) {
}
