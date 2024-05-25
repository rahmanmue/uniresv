package com.enigmacamp.reservationcampus.model.response;

import com.enigmacamp.reservationcampus.utils.constant.EAvailability;
import com.enigmacamp.reservationcampus.utils.constant.ETypeFacilities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDataResponse {
    private String id;
    private String name;
    private String information;
    private String picture;
    private Integer quantity;
    private Integer price;
    private ETypeFacilities typeFacilities;
    private EAvailability availability;

}
