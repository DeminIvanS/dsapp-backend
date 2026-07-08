package org.dance.dsappbackend.dto;


import org.dance.dsappbackend.entity.User;

public record RegisterRequest(

        String username,
        String password,
        User.Role role

) {
}
