package com.enigmacamp.reservationcampus.model.request;

import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStatusRequest {
    private EStatusReservation status;
}
