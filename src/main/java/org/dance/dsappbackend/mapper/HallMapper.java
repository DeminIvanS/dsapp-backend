package org.dance.dsappbackend.mapper;

import org.dance.dsappbackend.dto.CreateHallDto;
import org.dance.dsappbackend.dto.HallDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Hall;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {
    public Hall toHallEntity(CreateHallDto dto, Branch branch) {
        Hall hall = new Hall();
        hall.setName(dto.name());
        hall.setBranch(branch);
        hall.setDescription(dto.description());
        return hall;
    }

    public HallDto toHallDto(Hall hall) {
        return new HallDto(
                hall.getId(),
                hall.getBranch().getId(),
                hall.getName(),
                hall.getDescription(),
                hall.getCreatedAt());
    }
}

