package com.enigmacamp.reservationcampus.model.entity.constant;

import com.enigmacamp.reservationcampus.utils.constant.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_role")
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole name;

}
