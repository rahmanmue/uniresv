package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.repository.ProfileRepository;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileById(String id) {
        return profileRepository.findById(id).get();
    }

    @Override
    public Profile updateProfile(Profile profile) {
        Profile profile1 = profileRepository.findById(profile.getId()).get();
        profile1.setNIM(profile.getNIM());
        profile1.setFullName(profile.getFullName());
        profile1.setDateofbirth(profile.getDateofbirth());
        profile1.setPhone(profile.getPhone());
        profile1.setPhoto(profile.getPhoto());
        return profileRepository.save(profile1);

    }



    @Override
    public Profile deleteProfile(String id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile != null) {
            profileRepository.delete(profile);
        }
        return profile;
    }

    @Override
    public Profile getProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public Profile updateProfileById(String id, AuthRequestStudent authRequestStudent) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if(optionalProfile.isPresent()){
            Profile profile = optionalProfile.get();
            profile.setNIM(authRequestStudent.getNim());
            profile.setFullName(authRequestStudent.getFullName());
            profile.setDateofbirth(authRequestStudent.getBirthDate());
            profile.setPhone(authRequestStudent.getPhone());
            profile.setPhoto(authRequestStudent.getPhoto());

            User user =  profile.getUser();
            if (user != null){
                if (authRequestStudent.getEmail() != null && !authRequestStudent.getEmail().isEmpty()) {
                    user.setEmail(authRequestStudent.getEmail());
                }
                if (authRequestStudent.getPassword() != null && !authRequestStudent.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(authRequestStudent.getPassword()));
                }
                userRepository.save(user);
            }

            return profileRepository.save(profile);
        } else {
            throw new RuntimeException("Profile not found");
        }
    }


    @Override
    public List<Profile> getProfileByName(String name) {
        return profileRepository.findByFullNameContainsIgnoreCase(name);
    }
}
