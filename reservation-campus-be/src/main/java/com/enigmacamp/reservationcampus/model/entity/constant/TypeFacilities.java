package com.enigmacamp.reservationcampus.model.entity.constant;

import com.enigmacamp.reservationcampus.utils.constant.ETypeFacilities;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "mst_typefac")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TypeFacilities {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_typefac")
    private String id;
    @Enumerated(EnumType.STRING)
    private ETypeFacilities name;
}
