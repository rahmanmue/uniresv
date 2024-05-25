package com.enigmacamp.reservationcampus.model.entity;

import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.model.entity.constant.TypeFacilities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private String id;

    @Column(name = "email",  nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role roles;
}
