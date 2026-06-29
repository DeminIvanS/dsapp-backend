package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Hall;

import java.time.LocalDateTime;


public class HallDto {
    private Long id;
    private Long branchId;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public HallDto() {
    }

    public static HallDto from(Hall hall){
        HallDto dto = new HallDto();
        dto.id = hall.getId();
        dto.name = hall.getName();
        dto.description = hall.getDescription();
        dto.createdAt = hall.getCreatedAt();
        return dto;
    }
    public Hall toEntity(){
        Hall hall = new Hall();
        hall.setId(this.id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setAddress(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
