package com.employee.manager.domain.repository;

import com.employee.manager.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    List<Employee> findByPrimerNombreContainingIgnoreCase(String name);

    @Query("SELECT e FROM Employee e WHERE CONCAT(e.primerNombre, ' ', e.segundoNombre) LIKE %:name%")
    List<Employee> findByFullName(@Param("name") String name);
}
