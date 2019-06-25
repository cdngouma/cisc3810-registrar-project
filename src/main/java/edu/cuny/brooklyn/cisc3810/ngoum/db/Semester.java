package edu.cuny.brooklyn.cisc3810.ngoum.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Semester {
    private int periodno;
    private String semesterAbv;
    private Date startDate;
    private Date endDate;

    public Semester(int periodno, String semesterAbv, Date startDate, Date endDate) {
        this.periodno = periodno;
        this.semesterAbv = semesterAbv;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSemesterAbv() {
        return semesterAbv;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getYear(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.startDate);
        return calendar.get(Calendar.YEAR);
    }

    public String getSemester(){
        if(semesterAbv.compareTo("FA") == 0) return "Fall";
        if(semesterAbv.compareTo("SP") == 0) return "Spring";
        if(semesterAbv.compareTo("SU") == 0) return "Summer";
        if(semesterAbv.compareTo("WI") == 0) return "Winter";
        else return "N/A";
    }
}
