package com.enigmacamp.reservationcampus.model.response;

import com.enigmacamp.reservationcampus.utils.constant.ETypeFacilities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityResponse {
    private String name;
    private String typeFacilities;
}
