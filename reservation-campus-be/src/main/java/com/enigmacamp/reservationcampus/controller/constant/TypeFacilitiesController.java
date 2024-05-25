package com.enigmacamp.reservationcampus.controller.constant;

import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import com.enigmacamp.reservationcampus.services.constant.TypeFacilitiesService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIPath.API + APIPath.TYPE_FACILITIES)
public class TypeFacilitiesController {

    @Autowired
    private TypeFacilitiesService typeFacilitiesService;

    @GetMapping
    public ResponseEntity<List<TypeFacilities>> getAllTypeFacilities() {
        List<TypeFacilities> typeFacilitiesList = typeFacilitiesService.getAll();
        return ResponseEntity.ok(typeFacilitiesList);
    }
}
