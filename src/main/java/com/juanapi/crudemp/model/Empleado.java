package com.juanapi.crudemp.model;


import com.juanapi.crudemp.model.enums.EstadoEmpleado;
import com.juanapi.crudemp.model.enums.TipoContrato;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Empleado {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\\\s]+$", message = "El apellido solo puede contener letras y espacios")
    @Column(nullable = false)
    private String apellido;


    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    @Column(nullable = false, unique = true)
    private String email;


    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\s()-]+$", message = "El teléfono solo puede contener números, espacios y caracteres especiales permitidos")
    @Column(nullable = false)
    private String telefono;


    @NotNull(message = "La fecha de contratación es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaContratacion;

    @Min(value = 0, message = "El salario base no puede ser negativo")
    @Column(nullable = false)
    private double salarioBase;

    @Min(value = 0, message = "Las horas extras no pueden ser negativas")
    @Column
    private double horasExtra;

    @Min(value = 0, message = "Las deducciones no pueden ser negativas")
    @Column
    private double deducciones;

    @Min(value = 0, message = "Los impuestos no pueden ser negativos")
    @Column
    private double impuestos;

    @Min(value = 0, message = "Las bonificaciónes no pueden ser negativas")
    @Column
    private double bonificaciones;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    @Column(nullable = false)
    private EstadoEmpleado estado;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de contrato es obligatorio")
    @Column(nullable = false)
    private TipoContrato tipoContrato;

}