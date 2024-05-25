package com.enigmacamp.reservationcampus.controller;


import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.model.response.CommonResponse;
import com.enigmacamp.reservationcampus.services.ProfileService;
import com.enigmacamp.reservationcampus.services.UserService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import com.enigmacamp.reservationcampus.utils.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIPath.API + APIPath.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProfileService profileService;

    @PutMapping(APIPath.STUDENT + "/{id}")
    public ResponseEntity<Profile> editUserProfileById(@PathVariable String id, @RequestBody AuthRequestStudent authRequestStudent) {
        Profile updatedProfile = profileService.updateProfileById(id, authRequestStudent);
        return ResponseEntity.ok(updatedProfile);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        String message = String.format(Message.MESSAGE_UPDATE);
        User userUpdate = userService.editUser(id, user);

        CommonResponse<User> response = CommonResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(userUpdate)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
