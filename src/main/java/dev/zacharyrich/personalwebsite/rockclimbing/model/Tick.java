package dev.zacharyrich.personalwebsite.rockclimbing.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Tick implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(name = "boulder_name", nullable = false)
    private String boulderName;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "times_sent", nullable = false)
    private int timesSent;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "send_date")
    private Date sendDate;

    public Tick() {
    }

    public Tick(Long id, String routeName, String boulderName, String grade, int timesSent, String videoUrl, Date sendDate) {
        this.id = id;
        this.routeName = routeName;
        this.boulderName = boulderName;
        this.grade = grade;
        this.timesSent = timesSent;
        this.videoUrl = videoUrl;
        this.sendDate = sendDate;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBoulderName() {
        return boulderName;
    }

    public void setBoulderName(String boulderName) {
        this.boulderName = boulderName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getTimesSent() {
        return timesSent;
    }

    public void setTimesSent(int timesSent) {
        this.timesSent = timesSent;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "tick{" +
                "id=" + id +
                ", routeName='" + routeName + '\'' +
                ", boulderName='" + boulderName + '\'' +
                ", grade='" + grade + '\'' +
                ", timesSent=" + timesSent +
                ", videoUrl='" + videoUrl + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }

    public Tick withRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public Tick withBoulderName(String boulderName) {
        this.boulderName = boulderName;
        return this;
    }

    public Tick withGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public Tick withTimesSent(int timesSent) {
        this.timesSent = timesSent;
        return this;
    }

    public Tick withVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Tick withSendDate(Date sendDate) {
        this.sendDate = sendDate;
        return this;
    }
}
