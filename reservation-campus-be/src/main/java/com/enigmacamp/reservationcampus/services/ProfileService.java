package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Profile saveProfile(Profile profile);

    List<Profile> getAllProfiles();

    Profile getProfileById(String id);

    List<Profile> getProfileByName(String name);

    Profile updateProfile(Profile profile);

    Profile deleteProfile(String id);

    Profile getProfileByUserId(String userId);

    Profile updateProfileById(String id, AuthRequestStudent authRequestStudent);
}