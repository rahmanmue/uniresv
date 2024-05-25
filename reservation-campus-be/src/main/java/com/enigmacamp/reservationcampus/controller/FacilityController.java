package com.enigmacamp.reservationcampus.controller;

import com.enigmacamp.reservationcampus.model.entity.Facility;
import com.enigmacamp.reservationcampus.model.request.FacilityRequest;
import com.enigmacamp.reservationcampus.model.response.CommonResponse;
import com.enigmacamp.reservationcampus.model.response.FacilityDataResponse;
import com.enigmacamp.reservationcampus.model.response.FacilityResponse;
import com.enigmacamp.reservationcampus.model.response.PageResponseWrapper;
import com.enigmacamp.reservationcampus.services.FacilityService;
import com.enigmacamp.reservationcampus.services.constant.TypeFacilitiesService;
import com.enigmacamp.reservationcampus.utils.constant.APIPath;
import com.enigmacamp.reservationcampus.utils.constant.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(APIPath.API + APIPath.FACILITIES)
@RequiredArgsConstructor
public class FacilityController {

    private final String uploadDir = "assets/images/facilities/";
    private final Path uploadPath = Paths.get(uploadDir);

    FacilityService facilityService;
    TypeFacilitiesService typeFacilitiesService;

    @Autowired
    public FacilityController(FacilityService facilityService, TypeFacilitiesService typeFacilitiesService){
        this.facilityService = facilityService;
        this.typeFacilitiesService = typeFacilitiesService;
    }

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<FacilityRequest>> createFacility(@RequestBody FacilityRequest facility){
        String message = String.format(Message.MESSAGE_INSERT);
        FacilityRequest facilityRequest = facilityService.saveFacility(facility);

        CommonResponse<FacilityRequest> response = CommonResponse.<FacilityRequest>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(facilityRequest)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CommonResponse<Facility>> getFacility(@PathVariable("id") String id) {
        String message = String.format("Success");
        Facility facility = facilityService.getFacilityById(id);

        CommonResponse<Facility> response = CommonResponse.<Facility>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(facility)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CommonResponse<FacilityResponse>> updateFacility(@RequestBody FacilityRequest facility) {
        String message = String.format(Message.MESSAGE_UPDATE);
        FacilityResponse facilityResponse = facilityService.updateFaciliyRes(facility);

        CommonResponse<FacilityResponse> response = CommonResponse.<FacilityResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(facilityResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @PutMapping
//    public ResponseEntity<CommonResponse<Facility>> updateFacility(@RequestBody Facility facility){
//        String message = String.format(Message.MESSAGE_UPDATE);
//        Facility result = facilityService.updateFacility(facility);
//
//        CommonResponse<Facility> response = CommonResponse.<Facility>builder()
//                .statusCode(HttpStatus.OK.value())
//                .message(message)
//                .data(result)
//                .build();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }


//    @GetMapping
//    public ResponseEntity<CommonResponse<List<Facility>>> getAllFacilities(){
//        String message = String.format(Message.MESSAGE_READ);
//        List<Facility> result = facilityService.getAllFacilities();
//
//        CommonResponse<List<Facility>> response = CommonResponse.<List<Facility>>builder()
//               .statusCode(HttpStatus.OK.value())
//               .message(message)
//               .data(result)
//               .build();
//        return ResponseEntity
//               .status(HttpStatus.OK)
//               .body(response);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Facility>> deleteFacility(@PathVariable String id){
        String message = String.format(Message.MESSAGE_DELETE);
        facilityService.deleteFacility(id);

        CommonResponse<Facility> response = CommonResponse.<Facility>builder()
               .statusCode(HttpStatus.OK.value())
               .message(message)
               .build();
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(response);
    }

//    @PostMapping
//    public ResponseEntity<FacilityRequest> uploadFacility(@RequestParam("name") String name,
//                                                         @RequestParam("picture") MultipartFile picture,
//                                                         @RequestParam("quantity") Integer quantity,
//                                                         @RequestParam("information") String information,
//                                                         @RequestParam("price") Integer price,
//                                                         @RequestParam("typeFacilities") String id_facility,
//                                                         @RequestParam("availability") String id_availability) throws IOException {
//        FacilityRequest facilityRequest = new FacilityRequest();
//        facilityRequest.setName(name);
//        facilityRequest.setInformation(information);
//        facilityRequest.setPrice(price);
//        facilityRequest.setQuantity(quantity);
//        facilityRequest.setTypeFacilities(id_facility);
//        facilityRequest.setAvailability(id_availability);
//        String originalFilename = picture.getOriginalFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
//        if(!extension.equals(".jpg")){
//            extension = ".jpg";
//        }
//        String newFileName = name + extension;
//        facilityRequest.setPicture(picture.getName());
//        if(!Files.exists(uploadPath)){
//            Files.createDirectories(uploadPath);
//        }
//        Path filePath = uploadPath.resolve(newFileName);
//        Files.copy(picture.getInputStream(), filePath);
//
//        return new ResponseEntity<>(facilityService.saveFacility(facilityRequest), HttpStatus.CREATED);
//    }

//    @PutMapping("edit/{id}")
//    public ResponseEntity<FacilityRequest> editFacility(@PathVariable String id,
//                                                        @RequestParam("name") String name,
//                                                          @RequestParam("picture") MultipartFile picture,
//                                                          @RequestParam("quantity") Integer quantity,
//                                                          @RequestParam("information") String information,
//                                                          @RequestParam("price") Integer price,
//                                                          @RequestParam("id_facility") String id_facility,
//                                                          @RequestParam("id_availability") String id_availability) throws IOException {
//        FacilityRequest facilityRequest = new FacilityRequest();
//        facilityRequest.setName(name);
//        facilityRequest.setInformation(information);
//        facilityRequest.setPrice(price);
//        facilityRequest.setQuantity(quantity);
//        facilityRequest.setTypeFacilities(id_facility);
//        facilityRequest.setAvailability(id_availability);
//
//        if (picture != null && !picture.isEmpty()) {
//            String originalFilename = picture.getOriginalFilename();
//            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
//            if(!extension.equals(".jpg")){
//                extension = ".jpg";
//            }
//            String newFileName = name + extension;
//
//            facilityRequest.setPicture(newFileName); // Perbarui nama gambar
//
//            // Simpan gambar yang baru
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            Path filePath = uploadPath.resolve(newFileName);
//            Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); // Ganti gambar yang ada
//        }
//        facilityService.saveFacility(facilityRequest);
//
//        // Beri respons sukses
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //GET All Facilities
    @GetMapping("/all")
    public ResponseEntity<CommonResponse<PageResponseWrapper<FacilityDataResponse>>> getAllFacilities(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {


        Pageable pageable = PageRequest.of(page, size);
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(7));

        Page<Facility> availableFacilities = facilityService.getAvailableFacilities(startDate, endDate, pageable);
        Page<Facility> unavailableFacilities = facilityService.getUnavailableFacilities(startDate, endDate, pageable);

        Page<FacilityDataResponse> combinedPage = mergePages(availableFacilities, unavailableFacilities, pageable);

        PageResponseWrapper<FacilityDataResponse> pageResponse = new PageResponseWrapper<>(combinedPage);

        CommonResponse<PageResponseWrapper<FacilityDataResponse>> response = CommonResponse.<PageResponseWrapper<FacilityDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Facility availability retrieved successfully")
                .data(pageResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    // GET Facility Availability by Date
    @GetMapping
    public ResponseEntity<CommonResponse<PageResponseWrapper<FacilityDataResponse>>> getFacilitiesByDate(
            @RequestParam Date startDate,
            @RequestParam Date endDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Facility> availableFacilities = facilityService.getAvailableFacilities(startDate, endDate, pageable);
        Page<Facility> unavailableFacilities = facilityService.getUnavailableFacilities(startDate, endDate, pageable);

        Page<FacilityDataResponse> combinedPage = mergePages(availableFacilities, unavailableFacilities, pageable);

        PageResponseWrapper<FacilityDataResponse> pageResponse = new PageResponseWrapper<>(combinedPage);

        CommonResponse<PageResponseWrapper<FacilityDataResponse>> response = CommonResponse.<PageResponseWrapper<FacilityDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Facility availability retrieved successfully")
                .data(pageResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CommonResponse<PageResponseWrapper<FacilityDataResponse>>> getFacilitiesByName(
            @PathVariable String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(7));


        Page<Facility> availableFacilities = facilityService.getAvailableFacilitiesByName(name, startDate, endDate, pageable);
        Page<Facility> unavailableFacilities = facilityService.getUnavailableFacilitiesByName(name, startDate, endDate, pageable);

        Page<FacilityDataResponse> combinedPage = mergePages(availableFacilities, unavailableFacilities, pageable);

        PageResponseWrapper<FacilityDataResponse> pageResponse = new PageResponseWrapper<>(combinedPage);

        CommonResponse<PageResponseWrapper<FacilityDataResponse>> response = CommonResponse.<PageResponseWrapper<FacilityDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Facilities retrieved successfully")
                .data(pageResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<CommonResponse<PageResponseWrapper<FacilityDataResponse>>> getFacilitiesByType(
            @PathVariable String typeId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = Date.valueOf(startDate.toLocalDate().plusDays(7));

        Page<Facility> availableFacilities = facilityService.getAvailableFacilitiesByType(typeId, startDate, endDate, pageable);
        Page<Facility> unavailableFacilities = facilityService.getUnavailableFacilitiesByType(typeId, startDate, endDate, pageable);

        Page<FacilityDataResponse> combinedPage = mergePages(availableFacilities, unavailableFacilities, pageable);

        PageResponseWrapper<FacilityDataResponse> pageResponse = new PageResponseWrapper<>(combinedPage);

        CommonResponse<PageResponseWrapper<FacilityDataResponse>> response = CommonResponse.<PageResponseWrapper<FacilityDataResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Facilities retrieved successfully")
                .data(pageResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    private FacilityDataResponse mapToResponse(Facility facility) {
        return FacilityDataResponse.builder()
                .id(facility.getId())
                .name(facility.getName())
                .information(facility.getInformation())
                .picture(facility.getPicture())
                .quantity(facility.getQuantity())
                .price(facility.getPrice())
                .typeFacilities(facility.getTypeFacilities().getName())
                .availability(facility.getAvailability().getName())
                .build();
    }


    private Page<FacilityDataResponse> mergePages(Page<Facility> availableFacilities, Page<Facility> unavailableFacilities, Pageable pageable) {
        List<FacilityDataResponse> combinedList = Stream.concat(
                availableFacilities.getContent().stream().map(this::mapToResponse),
                unavailableFacilities.getContent().stream().map(this::mapToResponse))
                .toList();

        long totalElements = availableFacilities.getTotalElements() + unavailableFacilities.getTotalElements();
        return new PageImpl<>(combinedList, pageable, totalElements);

    }
}


