package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import java.util.Date;

public class Semester {
    private Long id;
    private String semester;
    private Date startDate;
    private Date endDate;

    public Semester(Long id, String semester, Date startDate, Date endDate) {
        this.id = id;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
