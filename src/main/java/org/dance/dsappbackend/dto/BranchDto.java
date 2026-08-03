package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import java.time.LocalDateTime;

public class BranchDto {
    private Long id;
    private String name;
    private String address;
    private LocalDateTime createdAt;

    public BranchDto() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
