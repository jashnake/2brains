package com.employee.manager.presentation.controller;

import com.employee.manager.application.dto.EmployeeRequest;
import com.employee.manager.application.dto.EmployeeResponse;
import com.employee.manager.application.exception.EmployeeException;
import com.employee.manager.application.exception.ErrorMessage;
import com.employee.manager.application.service.EmployeeServiceImpl;
import com.employee.manager.application.util.LogUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.employee.manager.application.constants.ApplicationConstants.*;

@RestController
@Validated
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @Operation(
            summary = GET_ALL_EMPLOYEES_DOC,
            description = GET_ALL_EMPLOYEES_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = RESPONSE_CODE_200,
                    description = SUCCESSFULLY_RETRIEVED
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND_CODE,
                    description = EMPLOYEE_NOT_FOUND,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestHeader HttpHeaders headers)  throws EmployeeException  {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(GET_ALL_EMPLOYEES, "");
        var employees = employeeService.getAllEmployees();
        LogUtil.printLogInfoResponse(employees);
        return ResponseEntity.ok(employees);
    }

    @Operation(
            summary = GET_ALL_EMPLOYEES_BY_NAME,
            description = GET_ALL_EMPLOYEES_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = RESPONSE_CODE_200,
                    description = SUCCESSFULLY_RETRIEVED
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND_CODE,
                    description = EMPLOYEE_NOT_FOUND,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByName(@RequestHeader HttpHeaders headers, @RequestParam String name)   throws EmployeeException {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(GET_EMPLOYEES_BY_NAME, name);
        var employees = employeeService.getEmployeesByName(name);
        LogUtil.printLogInfoResponse(employees);
        return ResponseEntity.ok(employees);
    }

    @Operation(
            summary = GET_EMPLOYEE_BY_ID_DOC,
            description = GET_EMPLOYEE_BY_ID_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = RESPONSE_CODE_200,
                    description = SUCCESSFULLY_RETRIEVED
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND_CODE,
                    description = EMPLOYEE_NOT_FOUND,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id, @RequestHeader HttpHeaders headers)  throws EmployeeException {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(GET_EMPLOYEE_BY_ID, id);
        var employees = employeeService.getEmployeeById(id);
        LogUtil.printLogInfoResponse(employees);
        return ResponseEntity.ok(employees);
    }



    @Operation(
            summary = DELETE_EMPLOYEE_DOC,
            description = DELETE_EMPLOYEE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = SUCCESSFULLY_DELETED,
                    description = SUCCESSFULLY_DELETED_DOC
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND_CODE,
                    description = EMPLOYEE_NOT_FOUND,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id, @RequestHeader HttpHeaders headers) throws EmployeeException {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(DELETE_EMPLOYEE, id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = UPDATE_EMPLOYEE_DOC,
            description = UPDATE_EMPLOYEE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = RESPONSE_CODE_200,
                    description = SUCCESSFULLY_UPDATED,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND_CODE,
                    description = EMPLOYEE_NOT_FOUND,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request,
            @RequestHeader HttpHeaders headers) throws EmployeeException {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(UPDATE_EMPLOYEE, request);
        var updatedEmployee = employeeService.updateEmployee(id, request);
        LogUtil.printLogInfoResponse(updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(
            summary = CREATE_EMPLOYEE_DOC,
            description = CREATE_EMPLOYEE_DESC
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = SUCCESSFULLY_CREATED,
                    description = SUCCESSFULLY_CREATED_DOC,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = INVALID_INPUT_CODE,
                    description = INVALID_INPUT_DATA,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    @PostMapping("/batch")
    public ResponseEntity<List<EmployeeResponse>> createEmployees(
            @Valid @RequestBody @NotNull List<EmployeeRequest> requests, @RequestHeader HttpHeaders headers) {
        LogUtil.printHeaders(headers);
        LogUtil.printLogInfoRequest(CREATE_EMPLOYEE, requests);
        var createdEmployees = employeeService.createEmployees(requests);
        LogUtil.printLogInfoResponse(createdEmployees);
        return new ResponseEntity<>(createdEmployees, HttpStatus.CREATED);
    }
}
