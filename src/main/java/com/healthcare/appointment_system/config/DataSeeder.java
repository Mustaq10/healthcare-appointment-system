package com.healthcare.appointment_system.config;

import com.healthcare.appointment_system.model.Role;
import com.healthcare.appointment_system.model.Specialization;
import com.healthcare.appointment_system.repository.RoleRepository;
import com.healthcare.appointment_system.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public void run(String... args) {
        seedRoles();
        seedSpecializations();
    }

    private void seedRoles() {
        for (Role.RoleName roleName : Role.RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Seeded role: " + roleName);
            }
        }
    }

    private void seedSpecializations() {
        List<String> specializations = List.of(
                "Cardiology", "Dermatology", "Neurology",
                "Orthopedics", "Pediatrics", "Psychiatry",
                "Radiology", "General Practice", "Oncology",
                "Gynecology"
        );

        for (String name : specializations) {
            if (specializationRepository.findByName(name).isEmpty()) {
                Specialization spec = new Specialization();
                spec.setName(name);
                specializationRepository.save(spec);
                System.out.println("Seeded specialization: " + name);
            }
        }
    }
}