package cisc3810.ngoum.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @NotBlank
    private String degree;

    @NotBlank
    @Column(name = "major_name")
    private String name;

    @OneToMany(mappedBy = "major")
    private List<Student> students;

    protected Major() {}

    public Major(Integer id, String degree, String name) {
        this.id = id;
        this.degree = degree;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("Major : {id : %s, degree : %s, majorName : %s}", this.id, this.degree, this.name);
    }
}
