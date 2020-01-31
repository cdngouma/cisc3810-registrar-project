package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

//@Entity
//@Table(name = "students")
public class Student {
    enum DegreeTypes {
        MASTER,
        BACHELOR,
        ASSOCIATE
    }

    @Id
    @Column(updatable = false, nullable = false)
    private int id;

    //@OneToOne
    //private String username;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String gender;

    @NotBlank
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING) @NotBlank
    private DegreeTypes degreeType;

    private String major;

    @NotBlank
    private String career;

    @NotBlank @PositiveOrZero @Max(4)
    private double gpa;

    @Transient
    private short earnedCredits;

    @Transient
    private short attemptedCredits;

    private Date graduationDate;

    public Student(int id, String email, String firstName, String lastName, String gender, Date dateOfBirth,
                   DegreeTypes degreeType, String major, String career, double gpa, Date graduationDate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.degreeType = degreeType;
        this.major = major;
        this.career = career;
        this.gpa = gpa;
        this.graduationDate = graduationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DegreeTypes getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeTypes degreeType) {
        this.degreeType = degreeType;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public short getEarnedCredits() {
        return earnedCredits;
    }

    public void setEarnedCredits(short earnedCredits) {
        this.earnedCredits = earnedCredits;
    }

    public short getAttemptedCredits() {
        return attemptedCredits;
    }

    public void setAttemptedCredits(short attemptedCredits) {
        this.attemptedCredits = attemptedCredits;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String toString() {
        return String.format("Student : { id : %s, PersonalInfo: {email : \"%s\", firstName : \"%s\", lastName : \"%s\", gender : \"%s\", dateOfBirth : %s}, " +
                "AcademicInfo : {degreeType : %s, major : \"%s\", career : %s, gpa : %f, earnedCr : %d, attemptedCr : %d, graduationDate : %s}}",
                this.id, this.email, this.firstName, this.lastName, this.gender, this.dateOfBirth, this.degreeType, this.major, this.career, this.gpa,
                this.earnedCredits, this.attemptedCredits, this.graduationDate);    }
}
