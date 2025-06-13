package com.employee.manager.controller;

import com.employee.manager.application.dto.EmployeeRequest;
import com.employee.manager.application.dto.EmployeeResponse;
import com.employee.manager.application.exception.EmployeeException;
import com.employee.manager.application.service.EmployeeServiceImpl;
import com.employee.manager.application.util.ManageErrorsUtil;
import com.employee.manager.presentation.controller.EmployeeController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeServiceImpl employeeService;

    private List<EmployeeRequest> employeeRequests;
    private List<EmployeeResponse> employeeResponses;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer test-token");
        headers.add("X-Correlation-ID", "123456");
        headers.add("Accept-Language", "en");

        employeeRequests = Arrays.asList(
                EmployeeRequest.builder()
                        .primerNombre("Juan")
                        .segundoNombre("Manuel")
                        .apellidoPaterno("Romero")
                        .apellidoMaterno("Fuentes")
                        .edad(44)
                        .sexo("M")
                        .fechaNacimiento("13-13-1984")
                        .puesto("Developer")
                        .build(),
                EmployeeRequest.builder()
                        .primerNombre("Dario")
                        .apellidoPaterno("Romero")
                        .apellidoMaterno("Fuentes")
                        .edad(40)
                        .sexo("M")
                        .fechaNacimiento("13-13-1984")
                        .puesto("Director")
                        .build()
        );

        employeeResponses = Arrays.asList(
                EmployeeResponse.builder()
                        .id(1L)
                        .primerNombre("Juan")
                        .segundoNombre("Manuel")
                        .apellidoPaterno("Romero")
                        .apellidoMaterno("Fuentes")
                        .edad(44)
                        .sexo("M")
                        .fechaNacimiento("13-13-1984")
                        .puesto("Developer")
                        .build(),
                EmployeeResponse.builder()
                        .id(2L)
                        .primerNombre("Dario")
                        .apellidoPaterno("Romero")
                        .apellidoMaterno("Fuentes")
                        .edad(40)
                        .sexo("M")
                        .fechaNacimiento("13-13-1984")
                        .puesto("Director")
                        .build()
        );
    }

    @Test
    void createEmployees_ok() {
        when(employeeService.createEmployees(anyList()))
                .thenReturn(employeeResponses);

        ResponseEntity<List<EmployeeResponse>> response = employeeController.createEmployees(employeeRequests, headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        EmployeeResponse firstEmployee = response.getBody().get(0);
        assertThat(firstEmployee.getId()).isEqualTo(1L);
        assertThat(firstEmployee.getPrimerNombre()).isEqualTo("Juan");
        assertThat(firstEmployee.getPuesto()).isEqualTo("Developer");

        EmployeeResponse secondEmployee = response.getBody().get(1);
        assertThat(secondEmployee.getId()).isEqualTo(2L);
        assertThat(secondEmployee.getPrimerNombre()).isEqualTo("Dario");
        assertThat(secondEmployee.getPuesto()).isEqualTo("Director");
    }

    @Test
    @SneakyThrows
    void deleteEmployee_NoContent() {
        Long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployee(employeeId);

        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId, headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    @SneakyThrows
    void deleteEmployee_WhenNotFound_ShouldThrowException() {
        var employeeId = 4L;

        var manageErrorsUtil =  new ManageErrorsUtil();

        doThrow(new EmployeeException(manageErrorsUtil.getError404("Employee not found with id: 4")))
                .when(employeeService).deleteEmployee(employeeId);
        assertThrows(EmployeeException.class, () ->
                employeeController.deleteEmployee(employeeId, headers));
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    @SneakyThrows
    void putEmployee_ShouldReturnUpdatedEmployee() {
        // Given
        Long employeeId = 1L;
        var employeeRequestsUpdate =
                EmployeeRequest.builder()
                        .puesto("Director")
                        .build();

        var updatedEmployee =
                EmployeeResponse.builder()
                .id(employeeId)
                .puesto("Director")
                .build();

        when(employeeService.updateEmployee(anyLong(), any(EmployeeRequest.class)))
                .thenReturn(updatedEmployee);

        ResponseEntity<EmployeeResponse> response = employeeController.updateEmployee(employeeId, employeeRequestsUpdate, headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(employeeId);
        assertThat(response.getBody().getPuesto()).isEqualTo("Director");
        verify(employeeService, times(1)).updateEmployee(employeeId, employeeRequestsUpdate);
    }

    @Test
    @SneakyThrows
    void getAllEmployees_ok() {
        when(employeeService.getAllEmployees())
                .thenReturn(employeeResponses);

        ResponseEntity<List<EmployeeResponse>> response = employeeController.getAllEmployees(headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getPrimerNombre()).isEqualTo("Juan");
        assertThat(response.getBody().get(1).getPrimerNombre()).isEqualTo("Dario");
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    @SneakyThrows
    void getAllEmployee_By_Id() {
        when(employeeService.getEmployeeById(1L))
                .thenReturn(employeeResponses.get(0));

        ResponseEntity<EmployeeResponse> response = employeeController.getEmployeeById(1L, headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPrimerNombre()).isEqualTo("Juan");
        verify(employeeService, times(1)).getEmployeeById(1l);
    }

    @Test
    @SneakyThrows
    void getAllEmployees_By_Name() {
        when(employeeService.getEmployeesByName("juan"))
                .thenReturn(employeeResponses);

        ResponseEntity<List<EmployeeResponse>> response = employeeController.getEmployeesByName(headers, "juan");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getPrimerNombre()).isEqualTo("Juan");
        verify(employeeService, times(1)).getEmployeesByName("juan");
    }


    @Test
    @SneakyThrows
    void getAllEmployees_Empty() {
        when(employeeService.getAllEmployees())
                .thenReturn(List.of());

        ResponseEntity<List<EmployeeResponse>> response = employeeController.getAllEmployees(headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
        verify(employeeService, times(1)).getAllEmployees();
    }

}
