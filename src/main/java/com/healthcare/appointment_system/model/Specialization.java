package com.healthcare.appointment_system.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "specializations")
    private Set<Doctor> doctors = new HashSet<>();
}