package com.vagtplan.api.entities;


import com.vagtplan.api.utils.EmployeeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private EmployeeType employeeType;
    private List<Shift> shifts;
    private List<Shift> requestedShifts;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String name) { this.firstName = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public void setEmployeeType(EmployeeType employeeType) { this.employeeType = employeeType; }
    public EmployeeType getEmployeeType() { return employeeType; }

    public void setShifts(List<Shift> shifts) { this.shifts = shifts; }
    public List<Shift> getShifts() { return shifts; }
}
