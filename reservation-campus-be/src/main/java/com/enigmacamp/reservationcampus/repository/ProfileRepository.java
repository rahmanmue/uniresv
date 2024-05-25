package com.enigmacamp.reservationcampus.repository;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByFullNameContainsIgnoreCase(String name);
    Profile findByUserId(String userId);
}
