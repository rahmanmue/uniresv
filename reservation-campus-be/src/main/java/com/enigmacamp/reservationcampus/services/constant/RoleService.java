package com.enigmacamp.reservationcampus.services.constant;


import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.utils.constant.ERole;

public interface RoleService {
    Role getOrSave(ERole eRole);
}
