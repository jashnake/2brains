package com.employee.manager.application.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class EmployeeResponse {
    private Long id;
    private String primerNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer edad;
    private String sexo;
    private String fechaNacimiento;
    private String puesto;
    private Date fechaAlta;
    private Boolean estado;

}
