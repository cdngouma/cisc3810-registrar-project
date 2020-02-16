package cisc3810.ngoum.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "subjects")
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class Subject {
    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @NotBlank
    private String subjectName;

    @NotBlank
    @Length(min = 4, max = 4)
    private String subjectCode;

    @OneToMany(mappedBy = "subject")
    private List<Course> courses;

    @OneToMany(mappedBy = "department")
    private List<Instructor> instructors;

    protected Subject() {}

    public Subject(int id, String subjectName, String subjectCode) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return String.format("Subject : {id : %d, subjectName : \"%s\", subjectCode : \"%s\"}",
                this.id, this.subjectName, this.subjectCode);
    }
}
