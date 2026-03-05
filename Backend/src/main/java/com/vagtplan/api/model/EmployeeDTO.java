package com.vagtplan.api.model;

import com.vagtplan.api.utils.EmployeeType;

public record EmployeeDTO(long id, EmployeeType employeeType, String firstName, String lastName, String email, String phoneNumber, String password) { }