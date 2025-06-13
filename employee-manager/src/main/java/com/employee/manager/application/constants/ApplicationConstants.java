package com.employee.manager.application.constants;

public class ApplicationConstants {


    private ApplicationConstants(){
        throw new IllegalStateException("Utility class");
    }

    /**
     *  Errors
     * */
    public static final String ERROR_INVALID_HEADERS = "Invalid Headers";
    public static final String ERROR_INVALID_PARAMS = "Invalid Params";
    public static final String EMPLOYEES_NOT_FOUND = "Employees not found";
    public static final String EMPLOYEE_NOT_FOUND_ID = "Employee not found with id: ";
    public static final String EMPLOYEE_NOT_FOUND_NAME = "Employee not found with name: ";



    /**
     *  Operations
     * */
    public static final String GET_ALL_EMPLOYEES = "getAllEmployees";
    public static final String GET_EMPLOYEE_BY_ID = "getEmployeeById";
    public static final String GET_EMPLOYEES_BY_NAME = "getEmployeesByName";
    public static final String DELETE_EMPLOYEE = "deleteEmployee";
    public static final String UPDATE_EMPLOYEE = "updateEmployee";
    public static final String CREATE_EMPLOYEE = "createEmployees";

    /**
     *  Documentation
     * */
    public static final String GET_ALL_EMPLOYEES_DOC = "Get all employees";
    public static final String GET_ALL_EMPLOYEES_DESC = "Retrieves a list of all employees in the system";
    public static final String RESPONSE_CODE_200 = "200";
    public static final String SUCCESSFULLY_RETRIEVED = "Successfully retrieved the list of employees";
    public static final String NOT_FOUND_CODE = "404";
    public static final String INVALID_INPUT_CODE = "400";
    public static final String SUCCESSFULLY_DELETED = "204";
    public static final String SUCCESSFULLY_CREATED = "201";
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    public static final String INVALID_INPUT_DATA = "Invalid input data";
    public static final String GET_ALL_EMPLOYEES_BY_NAME = "Get employees filter by Name";
    public static final String GET_EMPLOYEE_BY_ID_DOC = "Get employee by Id";
    public static final String GET_EMPLOYEE_BY_ID_DESC = "Retrieve a employee in the system";
    public static final String DELETE_EMPLOYEE_DOC = "Delete an employee";
    public static final String DELETE_EMPLOYEE_DESC = "Deletes an employee based on the provided ID";
    public static final String SUCCESSFULLY_DELETED_DOC = "Employee successfully deleted";
    public static final String SUCCESSFULLY_UPDATED = "Employee successfully updated";
    public static final String SUCCESSFULLY_CREATED_DOC = "Employees successfully created";
    public static final String UPDATE_EMPLOYEE_DOC = "Update an employee";
    public static final String UPDATE_EMPLOYEE_DESC = "Updates an existing employee's information based on the provided ID";
    public static final String CREATE_EMPLOYEE_DOC = "Create a new employees";
    public static final String CREATE_EMPLOYEE_DESC = "Creates a new employees with the provided information";

    public static final String PRIMER_NOMBRE_ES_OBLIGATORIO = "El primer nombre es obligatorio";
    public static final String APELLIDO_PATERNO_ES_OBLIGATORIO = "El apellido paterno es obligatorio";
    public static final String APELLIDO_MATERNO_ES_OBLIGATORIO = "El apellido materno es obligatorio";
    public static final String EDAD_ES_OBLIGATORIO = "La edad es obligatoria";
    public static final String EDAD_MINIMA = "La edad mínima es 18 años";
    public static final String EDAD_MAXIMA = "La edad máxima es 100 años";
    public static final String SEXO_ES_OBLIGATORIO = "El sexo es obligatorio";
    public static final String FECHA_NACIMIENTO_ES_OBLIGATORIO = "La fecha de nacimiento es obligatoria";
    public static final String PUESTO_ES_OBLIGATORIO = "El puesto es obligatorio";
    public static final String ESTADO_ES_OBLIGATORIO = "El estado es obligatorio";















}
