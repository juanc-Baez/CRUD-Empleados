package com.juanapi.crudemp.service;


import com.juanapi.crudemp.exception.ResourceConflictException;
import com.juanapi.crudemp.exception.ResourceNotFoundException;
import com.juanapi.crudemp.model.Empleado;
import com.juanapi.crudemp.repository.EmpleadoRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmpleadoService {


    private final EmpleadoRepo empleadoRepo;;

    @Autowired
    public EmpleadoService(EmpleadoRepo empleadoRepo, View error) {
        this.empleadoRepo = empleadoRepo;
    }

    public Empleado crearEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        return empleadoRepo.save(empleado);
    }

    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id" + id));
    }

    public Empleado obtenerEmpleadoPorApellido(String apellido) {
        return empleadoRepo.findByApellido(apellido)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con apellido" + apellido));
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoRepo.findAll();
    }

    public boolean eliminarEmpleado(Long id) {
        Empleado empleado = empleadoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id" + id));
        empleadoRepo.delete(empleado);
        return true;
    }


    public Empleado actualizarEmpleado(Long id, Empleado detallesEmpleado) {
        Empleado empleadoExistente = empleadoRepo.findById((id))
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id" + id));

        validarEmpleado(detallesEmpleado);

        //copiar las propiedades de un objeto de origen a un objeto de destino
        BeanUtils.copyProperties(detallesEmpleado, empleadoExistente, getNullPropertyNames(detallesEmpleado));

        return empleadoRepo.save(empleadoExistente);
    }

//        getNullPropertyNames es excluir las propiedades que están null en el objeto de origen
//        para que no sobreescriban valores existentes en el objeto de destino
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object value = src.getPropertyValue(pd.getName());
            if (value == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    private void validarEmpleado(Empleado empleado) {
        List<String> errors = new ArrayList<>();

        if (empleado.getNombre() == null || empleado.getNombre().isEmpty()){
            errors.add("Nombre no valido");
        }
        if (empleado.getApellido() == null || empleado.getApellido().isEmpty()) {
            errors.add("Apellido no valido");
        }
        if (empleado.getEmail() == null || !empleado.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.add("El email debe ser válido");
        }
        if (empleadoRepo.existsByEmail(empleado.getEmail())){
            throw new ResourceConflictException("El email ya existe");
        }
        if (empleado.getSalarioBase() < 1200){
            errors.add("Salario Base debe ser mayor a 1200");
        }
        if (empleado.getFechaContratacion() != null && empleado.getFechaContratacion().isAfter(LocalDate.now())){
            errors.add("La fecha de contratación no puede ser futura");
        }

        if (!errors.isEmpty()){
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }



}
