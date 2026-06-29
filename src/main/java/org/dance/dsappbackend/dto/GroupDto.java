package org.dance.dsappbackend.dto;

import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Group;
import org.dance.dsappbackend.entity.Teacher;

import java.time.LocalDateTime;

/*Table groups {
  id integer [primary key]
  branch_id integer [not null]
  teacher_id integer [not null]             // Основной педагог
  name varchar(100) [not null]
  age_range varchar(50)
  created_at timestamp [default: `now()`]
}*/

public class GroupDto {
    private Long id;
    private Long branchId;
    private Long teacherId;
    private String name;
    private String ageRange;
    private LocalDateTime createdAt;

    public GroupDto() {
    }

    public static GroupDto from(Group group){
        GroupDto dto = new GroupDto();
        dto.id = group.getId();
        dto.branchId = group.getBranch().getId();
        dto.teacherId = group.getTeacher().getId();
        dto.name = group.getName();
        dto.ageRange = group.getAgeRange();
        dto.createdAt = group.getCreatedAt();
        return dto;
    }
    public Group toEntity(Branch branch, Teacher teacher){
        Group group = new Group();
        group.setBranch(branch);
        group.setTeacher(teacher);
        group.setName(this.name);
        group.setAgeRange(this.ageRange);
        group.setCreatedAt(this.createdAt);
        return group;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
