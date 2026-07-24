package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Teacher;
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

    public GroupService(GroupRepository groupRepository, BranchRepository branchRepository, TeacherRepository teacherRepository) {
        this.groupRepository = groupRepository;
        this.branchRepository = branchRepository;
        this.teacherRepository = teacherRepository;
    }

    public GroupDto findById(Long id) {

        return groupRepository.findById(id)
                .map(GroupDto::from)
                .orElseThrow(() -> new EntityNotFoundException("Group with id=" + id + " not found."));

    }

    public List<GroupDto> findAll() {
        return groupRepository.findAll()
                .stream()
                .map(GroupDto::from)
                .toList();
    }

    public GroupDto create(GroupDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        var entity = dto.toEntity(branch, teacher);
        return GroupDto.from(groupRepository.save(entity));
    }

    public void update(Long id, GroupDto dto) {

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        var entity = dto.toEntity(branch, teacher);
        entity.setId(id);
        groupRepository.save(entity);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

}
