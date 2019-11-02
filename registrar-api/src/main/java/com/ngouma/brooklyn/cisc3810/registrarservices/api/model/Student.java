package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import java.util.Date;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String degree;
    private String major;
    private String division;
    private String status;
    private double gpa;
    private float transferredCredits;
    private float earnedCredits;
    private float attemptedCredits;

    public Student(){ }


    public Student(int id, String email_address, String first_name, String last_name, String gender, java.sql.Date dob, String degree, String major, String division, double gpa) {
    }
}
