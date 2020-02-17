package cisc3810.ngoum.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name ="student_classes")
public class StudentClass {
    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassEntity classEntity;

    @NotNull
    private String status;

    @PositiveOrZero
    @Max(100)
    private Double grade;

    protected StudentClass() {}

    public StudentClass(Integer id, Student student, ClassEntity classEntity, String status, Double grade) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
