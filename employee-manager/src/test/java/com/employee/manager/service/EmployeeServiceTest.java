package com.employee.manager.service;

import com.employee.manager.application.dto.EmployeeRequest;
import com.employee.manager.application.dto.EmployeeResponse;
import com.employee.manager.application.mapper.EmployeeMapper;
import com.employee.manager.application.service.EmployeeServiceImpl;
import com.employee.manager.domain.entity.Employee;
import com.employee.manager.domain.repository.EmployeeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Employee employeeTwo;
    List<Employee> employees;
    EmployeeResponse employeeResponse;
    private List<EmployeeRequest> employeeRequests;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setPrimerNombre("Juan");
        employee.setSegundoNombre("Manuel");
        employee.setApellidoPaterno("Romero");
        employee.setApellidoMaterno("Fuentes");
        employee.setEdad(44);
        employee.setSexo("M");
        employee.setFechaNacimiento("13-13-1984");
        employee.setPuesto("Developer");
        employeeTwo = new Employee();
        employeeTwo.setId(2L);
        employeeTwo.setPrimerNombre("Dario");
        employeeTwo.setApellidoPaterno("Romero");
        employeeTwo.setApellidoMaterno("Fuentes");
        employeeTwo.setEdad(44);
        employeeTwo.setSexo("M");
        employeeTwo.setFechaNacimiento("13-13-1984");
        employeeTwo.setPuesto("Director");
        employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employeeTwo);

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
                        .build()
        );

        employeeResponse = EmployeeResponse.builder()
                .id(1L)
                .primerNombre("Juan")
                .segundoNombre("Manuel")
                .apellidoPaterno("Romero")
                .apellidoMaterno("Fuentes")
                .edad(44)
                .sexo("M")
                .fechaNacimiento("13-13-1984")
                .puesto("Developer")
                .build();

    }

    @Test
    void whenSaveEmployee_thenReturnSavedEmployee() {
        when(employeeMapper.toEntity(any(EmployeeRequest.class))).thenReturn(employee);
        when(employeeMapper.toResponse(any())).thenReturn(employeeResponse);
        when(employeeRepository.saveAll(anyList())).thenReturn(employees);

        List<EmployeeResponse> savedEmployees = employeeService.createEmployees(employeeRequests);

        assertThat(savedEmployees).isNotNull();
        assertThat(savedEmployees.get(0).getPrimerNombre()).isEqualTo("Juan");
        verify(employeeRepository).saveAll(anyList());
    }

    @Test
    @SneakyThrows
    void whenGetAllEmployees_thenReturnEmployeesList() {
        List<Employee> employees = Arrays.asList(
                employee,
                employee);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeResponse> foundEmployees = employeeService.getAllEmployees();

        assertThat(foundEmployees).isNotNull().hasSize(2);
        verify(employeeRepository).findAll();
    }

    @Test
    @SneakyThrows
    void whenGetEmployeesByName_thenReturnEmployeesList() {
        List<Employee> employees = Arrays.asList(
                employee,
                employee);

        when(employeeRepository.findByPrimerNombreContainingIgnoreCase("Juan")).thenReturn(employees);

        List<EmployeeResponse> foundEmployees = employeeService.getEmployeesByName("Juan");

        assertThat(foundEmployees).isNotNull().hasSize(2);
        verify(employeeRepository).findByPrimerNombreContainingIgnoreCase("Juan");
    }

    @Test
    @SneakyThrows
    void whenGetEmployeeById_thenReturnEmployee() {

        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponse(any())).thenReturn(employeeResponse);
        var foundEmployee = employeeService.getEmployeeById(1L);
        verify(employeeRepository).findById(1L);
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getPrimerNombre()).isEqualTo("Juan");

    }

    @Test
    @SneakyThrows
    void whenDeleteEmployee_thenVerifyDeletion() {
        Long employeeId = 1L;
        doNothing().when(employeeRepository).deleteById(employeeId);
        when(employeeRepository.existsById(any())).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    @SneakyThrows
    void whenUpdateEmployee_thenReturnSavedEmployee() {
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeMapper.toResponse(any())).thenReturn(employeeResponse);
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        var employeeRequest = EmployeeRequest.builder()
                .primerNombre("Juan")
                .segundoNombre("Manuel")
                .apellidoPaterno("Romero")
                .apellidoMaterno("Fuentes")
                .edad(44)
                .sexo("M")
                .fechaNacimiento("13-13-1984")
                .puesto("Developer")
                .build();

        var employeeUpdated = employeeService.updateEmployee(1L, employeeRequest);

        assertThat(employeeUpdated).isNotNull();
        assertThat(employeeUpdated.getPrimerNombre()).isEqualTo("Juan");
    }

}
