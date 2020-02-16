package cisc3810.ngoum.registrarservices.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;

@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @Column(updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "period_id", referencedColumnName = "id")
    private AcademicPeriod semester;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;

    @NotNull
    private Time startTime;

    @NotNull
    private Time endTime;

    private String room;

    @PositiveOrZero
    private short roomCapacity;

    @PositiveOrZero
    private short numEnrolled;

    @NotBlank
    private String meetingDays;

    protected  ClassEntity() {}

    public ClassEntity(int id, AcademicPeriod semester, Course course, Instructor instructor, Time startTime, Time endTime, String classRoom,
                       short roomCapacity, short numEnrolled, String meetingDays) {
        this.id = id;
        this.semester = semester;
        this.course = course;
        this.instructor = instructor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = classRoom;
        this.roomCapacity = roomCapacity;
        this.numEnrolled = numEnrolled;
        this.meetingDays = meetingDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AcademicPeriod getSemester() {
        return semester;
    }

    public void setSemester(AcademicPeriod semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public short getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(short roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public short getNumEnrolled() {
        return numEnrolled;
    }

    public void setNumEnrolled(short numEnrolled) {
        this.numEnrolled = numEnrolled;
    }

    public String getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }
}
