package org.dance.dsappbackend;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.advice.GlobalExceptionHandler;
import org.dance.dsappbackend.controller.UserController;
import org.dance.dsappbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)

public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private org.dance.dsappbackend.security.JwtTokenProvider jwtTokenProvider;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testIllegalArgumentException() throws Exception {
        Mockito.when(userService.resetPassword(10L))
                        .thenThrow(new IllegalArgumentException("Illegal Id(400)"));

        mockMvc.perform(post("/api/users/10/reset-password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Illegal Id(400)"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testEntityNotFoundException() throws Exception {
        Mockito.when(userService.resetPassword(20L))
                .thenThrow(new EntityNotFoundException("Not Found(404)"));

        mockMvc.perform(post("/api/users/20/reset-password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found(404)"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testGeneralException() throws Exception {
        Mockito.when(userService.resetPassword(30L))
                .thenThrow(new RuntimeException("Server Error(500)"));

        mockMvc.perform(post("/api/users/30/reset-password")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Server Error(500)"));
    }
}
