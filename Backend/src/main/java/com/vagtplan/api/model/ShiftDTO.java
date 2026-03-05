package com.vagtplan.api.model;

import com.vagtplan.api.utils.EmployeeType;

import java.util.Date;

public record ShiftDTO(long id, Date start, Date end, EmployeeType employeeType) {
    
}