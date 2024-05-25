package com.enigmacamp.reservationcampus.model.request;

import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityRequest {
    private String id;
    private String name;
    private String information;
    private String picture;
    private Integer quantity;
    private Integer price;
    private String typeFacilities;
    private String availability;
}
