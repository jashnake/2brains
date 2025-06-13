package com.employee.manager.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

import static com.employee.manager.application.constants.ApplicationConstants.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
public class EmployeeRequest {
    @NotBlank(message = PRIMER_NOMBRE_ES_OBLIGATORIO)
    @Schema(requiredMode = REQUIRED, example = "Juan",description = "Primer Nombre")
    private String primerNombre;

    @Schema(requiredMode = NOT_REQUIRED, example = "Manuel",description = "Segundo Nombre")
    private String segundoNombre;

    @NotBlank(message = APELLIDO_PATERNO_ES_OBLIGATORIO)
    @Schema(requiredMode = REQUIRED, example = "Romero",description = "Apellido Paterno")
    private String apellidoPaterno;

    @NotBlank(message = APELLIDO_MATERNO_ES_OBLIGATORIO)
    @Schema(requiredMode = REQUIRED, example = "Rendón",description = "Apellido Materno")
    private String apellidoMaterno;

    @NotNull(message = EDAD_ES_OBLIGATORIO)
    @Min(value = 18, message = EDAD_MINIMA)
    @Max(value = 100, message = EDAD_MAXIMA)
    @Schema(requiredMode = REQUIRED, example = "40",description = "Edad")
    private Integer edad;

    @NotBlank(message = SEXO_ES_OBLIGATORIO)
    @Pattern(regexp = "^[MF]$", message = "El sexo debe ser 'M' o 'F'")
    @Schema(requiredMode = REQUIRED, example = "M",description = "Sexo")
    private String sexo;

    @NotNull(message = FECHA_NACIMIENTO_ES_OBLIGATORIO)
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$")
    @Schema(requiredMode = REQUIRED, example = "13-11-1984",description = "Fecha de Nacimiento")
    private String fechaNacimiento;

    @NotBlank(message = PUESTO_ES_OBLIGATORIO)
    @Schema(requiredMode = REQUIRED, example = "Developer",description = "Puesto de Trabajo")
    private String puesto;

    @NotNull(message = ESTADO_ES_OBLIGATORIO)
    @Schema(requiredMode = REQUIRED, example = "true",description = "Estado del empleado")
    private Boolean estado;

}