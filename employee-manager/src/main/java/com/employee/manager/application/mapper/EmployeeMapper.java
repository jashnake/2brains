package com.employee.manager.application.mapper;

import com.employee.manager.application.dto.EmployeeRequest;
import com.employee.manager.application.dto.EmployeeResponse;
import com.employee.manager.domain.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequest request);
    EmployeeResponse toResponse(Employee employee);

}
