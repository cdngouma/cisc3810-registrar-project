package com.ngoum.cisc3810.registrar.registrarserver.model;

public class CourseSubject {
    private int subjectNo;
    private String subjectAbv;
    private String subjectName;

    public CourseSubject(int subjectNo, String subjectAbv, String subjectName) {
        this.subjectNo = subjectNo;
        this.subjectAbv = subjectAbv;
        this.subjectName = subjectName;
    }

    public int getSubjectNo() {
        return subjectNo;
    }

    public String getSubjectAbv() {
        return subjectAbv;
    }

    public void setSubjectAbv(String dept_abv) {
        this.subjectAbv = dept_abv;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
