package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.CreateGroupDto;
import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.mapper.GroupMapper;
import org.dance.dsappbackend.repository.BranchRepository;
import org.dance.dsappbackend.repository.GroupRepository;
import org.dance.dsappbackend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {


    private final GroupRepository groupRepository;
    private final BranchRepository branchRepository;
    private final TeacherRepository teacherRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, BranchRepository branchRepository, TeacherRepository teacherRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.branchRepository = branchRepository;
        this.teacherRepository = teacherRepository;
        this.groupMapper = groupMapper;
    }

    public GroupDto findById(Long id){

        return groupRepository.findById(id)
                .map(groupMapper::toGroupDto)
                .orElseThrow(()->new EntityNotFoundException("Group with id=" +id+" not found."));

    }

    public List<GroupDto> findAll(){
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::toGroupDto)
                .toList();
    }

    public GroupDto create(CreateGroupDto dto){
        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new RuntimeException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(()-> new RuntimeException("Teacher not found"));
        var entity = groupMapper.toGroupEntity(dto,teacher,branch);
        return groupMapper.toGroupDto(groupRepository.save(entity));
    }

    public void update(Long id, CreateGroupDto dto){

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new RuntimeException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(()-> new RuntimeException("Teacher not found"));
        var entity = groupMapper.toGroupEntity(dto, teacher, branch);
        entity.setId(id);
        groupRepository.save(entity);
    }

    public void delete(Long id){
        groupRepository.deleteById(id);
    }

}
