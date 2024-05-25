package com.enigmacamp.reservationcampus.repository.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole role);
}
