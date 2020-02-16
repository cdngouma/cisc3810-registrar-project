package cisc3810.ngoum.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name ="student_classes")
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

    protected StudentClasses() {}

    public StudentClasses(Integer id, Student student, ClassEntity classEntity, CompletionStatus status, double grade) {
        this.id = id;
        this.student = student;
        this.classEntity = classEntity;
        this.status = status;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public CompletionStatus getStatus() {
        return status;
    }

    public void setStatus(CompletionStatus status) {
        this.status = status;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
