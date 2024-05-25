package com.enigmacamp.reservationcampus.controller.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.Penalties;
import com.enigmacamp.reservationcampus.services.constant.PenaltyService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIPath.API + APIPath.PENALTY)
public class PenaltyController {

    @Autowired
    private PenaltyService penaltyService;

    @GetMapping
    public ResponseEntity<List<Penalties>> getAllPenalties() {
        List<Penalties> penaltiesList = penaltyService.getAllPenalties();
        return ResponseEntity.ok(penaltiesList);
    }
}
