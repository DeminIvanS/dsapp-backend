package org.dance.dsappbackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.dance.dsappbackend.dto.CreateGroupDto;
import org.dance.dsappbackend.dto.GroupDto;
import org.dance.dsappbackend.entity.Branch;
import org.dance.dsappbackend.entity.Group;
import org.dance.dsappbackend.entity.SubscriptionPlan;
import org.dance.dsappbackend.entity.Teacher;
import org.dance.dsappbackend.mapper.GroupMapper;
import org.dance.dsappbackend.repository.BranchRepository;
import org.dance.dsappbackend.repository.GroupRepository;
import org.dance.dsappbackend.repository.SubscriptionPlanRepository;
import org.dance.dsappbackend.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {


    private final GroupRepository groupRepository;
    private final BranchRepository branchRepository;
    private final TeacherRepository teacherRepository;
    private final SubscriptionPlanRepository planRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository,
                        BranchRepository branchRepository,
                        TeacherRepository teacherRepository,
                        SubscriptionPlanRepository planRepository,
                        GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.branchRepository = branchRepository;
        this.teacherRepository = teacherRepository;
        this.planRepository = planRepository;
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
    @Transactional
    public GroupDto create(CreateGroupDto dto){
        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new EntityNotFoundException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found"));
        var entity = groupMapper.toGroupEntity(dto,teacher,branch);
        return groupMapper.toGroupDto(groupRepository.save(entity));
    }
    @Transactional
    public void update(Long id, CreateGroupDto dto){

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(()-> new EntityNotFoundException("Branch not found"));
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found"));
        var entity = groupMapper.toGroupEntity(dto, teacher, branch);
        entity.setId(id);
        groupRepository.save(entity);
    }
    @Transactional
    public void delete(Long id){
        groupRepository.deleteById(id);
    }
    @Transactional
    public void addSubscriptionPlan(Long groupId, Long planId){
        Group group = groupRepository.findById(groupId).
                orElseThrow(()-> new EntityNotFoundException("Group with id=" +groupId+" not found."));
        SubscriptionPlan plan = planRepository.findById(planId).
                orElseThrow(()-> new EntityNotFoundException("Plan with id=" +planId+" not found."));
        if(!plan.isActive()) throw  new IllegalArgumentException("Нельзя привязать неактивный план подписки");
        boolean alreadyExists = group.getSubscriptionPlans().stream().anyMatch(g->g.getId().equals(planId));
        if(alreadyExists){
            throw new IllegalArgumentException("Этот план подписки уже привязан к группе");
        }
        group.getSubscriptionPlans().add(plan);
        groupRepository.save(group);

    }

    }


