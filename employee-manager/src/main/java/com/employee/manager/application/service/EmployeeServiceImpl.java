package com.employee.manager.application.service;

import com.employee.manager.application.dto.EmployeeRequest;
import com.employee.manager.application.dto.EmployeeResponse;
import com.employee.manager.application.mapper.EmployeeMapper;
import com.employee.manager.application.util.ManageErrorsUtil;
import com.employee.manager.domain.entity.Employee;
import com.employee.manager.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.employee.manager.application.exception.EmployeeException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.employee.manager.application.constants.ApplicationConstants.*;
import static com.employee.manager.application.util.EmployeeManagerUtil.getErrorText;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl{

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ManageErrorsUtil manageErrorsUtil;

    public List<EmployeeResponse> getAllEmployees()  throws EmployeeException {
        var employees = employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());

        if(Objects.nonNull(employees) && !employees.isEmpty()){
            return employees;
        }else{
            throw new EmployeeException(manageErrorsUtil.getError404(EMPLOYEES_NOT_FOUND));
        }
    }

    public EmployeeResponse getEmployeeById(Long id) throws EmployeeException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException(manageErrorsUtil.getError404(getErrorText(EMPLOYEE_NOT_FOUND_ID, String.valueOf(id)))));
       return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponse> getEmployeesByName(String name) throws EmployeeException {
        var employees = employeeRepository.findByPrimerNombreContainingIgnoreCase(name)
        .stream()
        .map(employeeMapper::toResponse)
        .collect(Collectors.toList());
        if(Objects.nonNull(employees) && !employees.isEmpty()){
            return employees;
        }else{
            throw new EmployeeException(manageErrorsUtil.getError404(getErrorText(EMPLOYEE_NOT_FOUND_NAME, name)));
        }

    }

    public void deleteEmployee(Long id) throws EmployeeException {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeException(manageErrorsUtil.getError404(getErrorText(EMPLOYEE_NOT_FOUND_ID, String.valueOf(id))));
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) throws EmployeeException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException(manageErrorsUtil.getError404(getErrorText(EMPLOYEE_NOT_FOUND_ID, String.valueOf(id)))));

        if (Objects.nonNull(request.getPrimerNombre())) {
            employee.setPrimerNombre(request.getPrimerNombre());
        }
        if (Objects.nonNull(request.getSegundoNombre())) {
            employee.setSegundoNombre(request.getSegundoNombre());
        }
        if (Objects.nonNull(request.getApellidoPaterno())) {
            employee.setApellidoPaterno(request.getApellidoPaterno());
        }
        if (Objects.nonNull(request.getApellidoMaterno())) {
            employee.setApellidoMaterno(request.getApellidoMaterno());
        }
        if (Objects.nonNull(request.getEdad())) {
            employee.setEdad(request.getEdad());
        }
        if (Objects.nonNull(request.getSexo())) {
            employee.setSexo(request.getSexo());
        }
        if (Objects.nonNull(request.getFechaNacimiento())) {
            employee.setFechaNacimiento(request.getFechaNacimiento());
        }
        if (Objects.nonNull(request.getPuesto())) {
            employee.setPuesto(request.getPuesto());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(updatedEmployee);
    }

    public List<EmployeeResponse> createEmployees(List<EmployeeRequest> requests) {
        List<Employee> employees = requests.stream()
                .map(employeeMapper::toEntity)
                .collect(Collectors.toList());

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);

        return savedEmployees.stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}
