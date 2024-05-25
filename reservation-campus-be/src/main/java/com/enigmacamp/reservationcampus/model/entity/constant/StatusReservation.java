package com.enigmacamp.reservationcampus.model.entity.constant;
import com.enigmacamp.reservationcampus.utils.constant.EStatusReservation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatusReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_status")
    private String id;
    @Enumerated(EnumType.STRING)
    private EStatusReservation status;

}
