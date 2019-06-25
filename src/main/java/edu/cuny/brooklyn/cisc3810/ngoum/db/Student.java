package edu.cuny.brooklyn.cisc3810.ngoum.db;

import java.util.Date;

public class Student {
    private int studentno;
    /* student info */
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private char sex;
    private String email;
    /* student academics */
    private String degree;
    private String major;
    private char division;
    private double earnedCr;
    private double attemptCr;
    private double transfCr;
    private double gpa;

    public Student(int studentno, String firstName, String lastName) {
        this.studentno = studentno;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int studentno, String firstName, String lastName, Date dateOfBirth, char sex, String email) {
        this.studentno = studentno;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.email = email;
    }

    public Student(int studentno, String firstName, String lastName, String degree, String major, char division, double earnedCr, double attemptCr, double transfCr, double gpa) {
        this.studentno = studentno;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.major = major;
        this.division = division;
        this.earnedCr = earnedCr;
        this.attemptCr = attemptCr;
        this.transfCr = transfCr;
        this.gpa = gpa;
    }

    public int getStudentno() {
        return studentno;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDegree() {
        return degree;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    public double getTotalCredits(){
        return transfCr + attemptCr + earnedCr;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
