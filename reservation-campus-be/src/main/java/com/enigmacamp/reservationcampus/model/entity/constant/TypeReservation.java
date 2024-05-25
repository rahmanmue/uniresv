package com.enigmacamp.reservationcampus.model.entity.constant;
import com.enigmacamp.reservationcampus.utils.constant.ETypeReservation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_typeres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TypeReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_role")
    private String id;
    @Enumerated(EnumType.STRING)
    private ETypeReservation name;
}
