package com.enigmacamp.reservationcampus.services.constant.impl;

import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.repository.constant.RoleRepository;
import com.enigmacamp.reservationcampus.services.constant.RoleService;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = roleRepository.findByName(role);
        return optionalRole.orElseGet(() -> {
            Role newRole = Role.builder()
                    .name(role)
                    .build();
            return roleRepository.save(newRole);
        });
    }
}