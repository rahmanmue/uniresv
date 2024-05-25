package com.enigmacamp.reservationcampus.model.entity.constant;


import com.enigmacamp.reservationcampus.utils.constant.EPenalties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_penalties")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Penalties {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_penalties")
    private String id;

    @Enumerated(EnumType.STRING)
    private EPenalties name;

}
