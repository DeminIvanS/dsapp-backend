package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Hall;

import java.time.LocalDateTime;


public class HallDto {
    private Long id;
    private Long branchId;
    private String name;
    private String description;
    private LocalDateTime createdAt;


    public static HallDto from(Hall hall){
        HallDto dto = new HallDto();
        dto.branchId = hall.getBranch().getId();
        dto.id = hall.getId();
        dto.name = hall.getName();
        dto.description = hall.getDescription();
        dto.createdAt = hall.getCreatedAt();
        return dto;
    }
    public Hall toEntity(Branch branch){
        Hall hall = new Hall();
        hall.setId(this.id);
        hall.setBranch(branch);
        hall.setName(this.name);
        hall.setDescription(this.description);
        hall.setCreatedAt(this.createdAt);
        return hall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
