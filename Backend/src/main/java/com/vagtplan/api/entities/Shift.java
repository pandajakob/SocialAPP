package com.vagtplan.api.entities;

import com.vagtplan.api.utils.EmployeeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date start;
    private Date end;

    EmployeeType shiftEmployeeType;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public EmployeeType getShiftEmployeeType() {
        return shiftEmployeeType;
    }

    public void setShiftEmployeeType(EmployeeType shiftEmployeeType) {
        this.shiftEmployeeType = shiftEmployeeType;
    }

}
