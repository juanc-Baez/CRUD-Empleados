package com.juanapi.crudemp.repository;


import com.juanapi.crudemp.model.Empleado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByApellido(String apellido);

    boolean existsByEmail(String email);
}
