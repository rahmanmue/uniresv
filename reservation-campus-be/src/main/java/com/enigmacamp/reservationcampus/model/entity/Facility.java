package com.enigmacamp.reservationcampus.model.entity;


import com.enigmacamp.reservationcampus.model.entity.constant.Availability;
import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_facility")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_fac")
    private String id;

    @Column(name = "name_facility", nullable = false)
    private String name;

    @Column(name = "information")
    private String information;

    @Column(name = "picture")
    private String picture;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "id_typefac")
    private TypeFacilities typeFacilities;

    @ManyToOne
    @JoinColumn(name = "id_avail")
    private Availability availability;

}
