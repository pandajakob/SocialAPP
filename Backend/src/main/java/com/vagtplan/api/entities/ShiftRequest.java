package com.vagtplan.api.entities;

import java.util.Date;

public class ShiftRequest {
    private Employee employee;
    final private Date requestDate = new Date();
    private String comment = "";

    public ShiftRequest(Employee employee) { this.employee = employee; }

    public String getComment() { return comment; }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Employee getEmployee() {
        return employee;
    }

}
