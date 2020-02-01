package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

//@Entity
//@Table(name ="student_classes")
public class StudentClasses {
    enum CompletionStatus {
        COMPLETED,
        ENROLLED,
        FORGIVEN
    }

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @NotNull
    private Student student;

    @NotNull
    private ClassEntity classEntity;

    @NotNull
    private CompletionStatus status;

    @PositiveOrZero
    @Max(100)
    private double grade;
}
