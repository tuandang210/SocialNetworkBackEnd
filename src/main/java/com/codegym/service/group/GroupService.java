package com.codegym.service.group;

import com.codegym.model.group.Group;
import com.codegym.repository.IGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService implements IGroupService{
    @Autowired
    private IGroupRepository groupRepository;
    @Override
    public Iterable<Group> findAll() {
        return null;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
