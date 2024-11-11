package com.juanapi.crudemp.controller;

import com.juanapi.crudemp.exception.ResourceNotFoundException;
import com.juanapi.crudemp.model.Empleado;
import com.juanapi.crudemp.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import java.util.List;

@RestController
@RequestMapping("/empleado")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpleadoController {


    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    //Crear Empleados
    @PostMapping("/crear")
    public ResponseEntity<Empleado> crearEmpleado(@Valid @RequestBody Empleado empleado) {
        Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleado);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    //Consulta Empleados
    @GetMapping(value = "/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(id);
        return ResponseEntity.ok(empleado);
    }

    @GetMapping(value = "/apellido")
    public ResponseEntity<Empleado> obtenerEmpleadoPorApellido(@Valid @RequestParam String apellido) throws ResourceNotFoundException {
        Empleado empleado = empleadoService.obtenerEmpleadoPorApellido(apellido);
        return ResponseEntity.ok(empleado);
    }

    @GetMapping(value = "/obtEmpleados")
    public ResponseEntity<List<Empleado>> obtenerEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerEmpleados();
        return ResponseEntity.ok(empleados);
    }

    //Eliminar empleado
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Empleado> elimEmpleado(@Valid @PathVariable Long id) throws ResourceNotFoundException {
        boolean eliminado = empleadoService.eliminarEmpleado(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }

    //Actualizar empleado
    @PutMapping(value = "/actualizar/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id,@Valid @RequestBody Empleado empleadoDetalles, BindingResult result) throws ResourceNotFoundException {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        Empleado empleadoAct = empleadoService.actualizarEmpleado(id, empleadoDetalles);
        return ResponseEntity.ok(empleadoAct);
    }


    @GetMapping(value = "/error")
    public ResponseEntity<Empleado> pruebaError() {
        throw new ResourceNotFoundException("Empleado no encontrado");
    }

}