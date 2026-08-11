package org.dance.dsappbackend.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", nullable = false, unique = true, length = 64)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "should_change_password", nullable = false)
    private boolean shouldChangePassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_TEACHER,
        ROLE_STUDENT
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setShouldChangePassword(boolean shouldChangePassword) {
        this.shouldChangePassword = shouldChangePassword;
    }

    public boolean shouldChangePassword() {
        return shouldChangePassword;
    }
}