package com.enigmacamp.reservationcampus.model.entity.constant;

import com.enigmacamp.reservationcampus.utils.constant.EAvailability;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_avail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_avail")
    private String id;
    @Enumerated(EnumType.STRING)
    private EAvailability name;
}
