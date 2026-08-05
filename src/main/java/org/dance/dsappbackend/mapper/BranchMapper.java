package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.dto.CreateBranchDto;
import org.dance.dsappbackend.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toBranchEntity (CreateBranchDto dto){
        Branch branch = new Branch();
        branch.setName(branch.getName());
        branch.setAddress(branch.getAddress());

        return branch;
    }
    public BranchDto toBranchDto(Branch branch){
        BranchDto dto = new BranchDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());

        dto.setAddress(branch.getAddress());
        dto.setCreatedAt(branch.getCreatedAt());
        return dto;
    }
}
