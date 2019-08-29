package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import java.util.Date;

public class Student {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBith;
    private String degree;
    private String major;
    private String division;
    private double gpa;

    public Student(){ }

    public Student(Integer id, String email, String firstName, String lastName, String gender,
                   Date dateOfBith, String degree, String major, String division, double gpa) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBith = dateOfBith;
        this.degree = degree;
        this.major = major;
        this.division = division;
        this.gpa = gpa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBith() {
        return dateOfBith;
    }

    public void setDateOfBith(Date dateOfBith) {
        this.dateOfBith = dateOfBith;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
