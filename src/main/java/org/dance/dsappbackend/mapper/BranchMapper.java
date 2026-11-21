package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.BranchDto;
import org.dance.dsappbackend.dto.CreateBranchDto;
import org.dance.dsappbackend.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toBranchEntity (CreateBranchDto dto){
        Branch branch = new Branch();
        branch.setName(dto.name());
        branch.setAddress(dto.address());
        return branch;
    }
    public BranchDto toBranchDto(Branch branch){
        return new BranchDto(
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getCreatedAt());
    }
}
