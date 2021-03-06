package cisc3810.ngoum.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "academic_periods")
public class AcademicPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    @NotBlank
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotBlank
    private String semester;

    @OneToMany(mappedBy = "semester")
    private List<ClassEntity> classes;

    protected AcademicPeriod() {}

    public AcademicPeriod(int id, Date startDate, Date endDate, String semester) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return String.format("AcademicPeriod : {id : %d, startDate : %s, endDate : %s, semester : %s}",
                this.id, this.startDate.toLocalDate().toString(), this.endDate.toString(), this.semester);
    }
}
