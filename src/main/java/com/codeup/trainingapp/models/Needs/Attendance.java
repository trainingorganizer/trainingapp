package com.codeup.trainingapp.models.Needs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date date;

    @Column
    private Boolean present;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;


    public Attendance(Date date, Boolean present, User user, Course course) {
        this.date = date;
        this.present = present;
        this.user = user;
        this.course = course;
    }

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPresent() {
        return present;
    }

    public String wasPresent() {
        if (present) {
            return "Present";
        } else {
            return "Absent";
        }
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
