package com.enigmacamp.reservationcampus.controller;


import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.model.response.CommonResponse;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import com.enigmacamp.reservationcampus.services.AdminService;
import com.enigmacamp.reservationcampus.services.ProfileService;
import com.enigmacamp.reservationcampus.services.UserService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import com.enigmacamp.reservationcampus.utils.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIPath.API + APIPath.ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping(APIPath.USERS + APIPath.STUDENT)
    public ResponseEntity<?> registerStudent(@RequestBody AuthRequestStudent authRequestStudent){
        RegisterResponse registerResponse = adminService.registerStudent(authRequestStudent);

        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .message("Successfully register new Student")
                .statusCode(HttpStatus.CREATED.value())
                .data(registerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(APIPath.USERS)
    public ResponseEntity<?> getAllUser(){
        String message = String.format(Message.MESSAGE_READ);
        List<User> result = userService.getAllUsers();

        CommonResponse<List<User>> response = CommonResponse.<List<User>>builder()
               .statusCode(HttpStatus.OK.value())
               .message(message)
               .data(result)
               .build();
        return ResponseEntity
               .status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(response);
    }


    @DeleteMapping(APIPath.USERS + "/{id}")
    public ResponseEntity<CommonResponse<Profile>> deleteProfile(@PathVariable String id) {
        // Hapus profile dan user yang terkait
        Profile profile = profileService.deleteProfile(id);
        userService.deleteUser(profile.getUser().getId());

        String message = String.format(Message.MESSAGE_DELETE, id);
        CommonResponse<Profile> response = CommonResponse.<Profile>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(profile)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
