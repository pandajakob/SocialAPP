package com.vagtplan.api.services;

import com.vagtplan.api.model.EmployeeDTO;
import com.vagtplan.api.entities.Employee;
import com.vagtplan.api.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getById(long id) {
        return employeeRepository.findById(id).map(this::convertToDTO);
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public void deleleteEmployeeWithId(long id) {
        employeeRepository.deleteById(id);
    }

    // Convert Employee Entity to ProductDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getEmployeeType(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPassword(), employee.getPassword());
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeType(employeeDTO.employeeType());
        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employee.setEmail(employeeDTO.email());
        employee.setPhoneNumber(employeeDTO.phoneNumber());
        employee.setPassword(employeeDTO.password());
        return employee;
    }

}
