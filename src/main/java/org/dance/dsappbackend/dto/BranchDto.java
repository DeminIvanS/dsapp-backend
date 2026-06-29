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

    public static BranchDto from(Branch branch){
        BranchDto dto = new BranchDto();
        dto.id = branch.getId();
        dto.name = branch.getName();
        dto.address = branch.getAddress();
        dto.createdAt = branch.getCreatedAt();
        return dto;
    }
    public Branch toEntity(){
        Branch branch = new Branch();
        branch.setId(this.id);
        branch.setName(this.name);
        branch.setAddress(this.address);
        branch.setCreatedAt(this.createdAt);
        return branch;
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
