package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateGroupDto;
import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Group;
import org.dance.dsappbackend.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public Group toGroupEntity(CreateGroupDto dto, Teacher teacher, Branch branch) {
        Group group = new Group();
        group.setBranch(branch);
        group.setTeacher(teacher);
        group.setName(dto.name());
        group.setAgeRange(dto.ageRange());
        return group;
    }

    public GroupDto toGroupDto(Group group){
        return new GroupDto(
                group.getId(),
                group.getBranch().getId(),
                group.getTeacher().getId(),
                group.getName(),
                group.getAgeRange(),
                group.getCreatedAt());
    }
}
