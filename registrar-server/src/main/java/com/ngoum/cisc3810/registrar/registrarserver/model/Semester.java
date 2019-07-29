package com.ngoum.cisc3810.registrar.registrarserver.model;

import java.util.Date;

public class Semester {
    private int periodNo;
    private String semester;
    private Date startDate;
    private Date endDate;
    private int year;

    public Semester(int periodNo, String semester, Date startDate, Date endDate) {
        this.periodNo = periodNo;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(int periodNo) {
        this.periodNo = periodNo;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
