package playground;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Schedule_table")
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private String userName;
    private String date;
    private Long groundId;
    private String groundType;
    private Integer maxPlayer;
    private String status;

    @PostPersist
    public void onPostPersist() {
        //if(this.getStatus().equals("Available")) {
        System.out.println("^^^^^^ listener Schedule onPostPersist : ");
            ScheduleCreated scheduleCreated = new ScheduleCreated();
            BeanUtils.copyProperties(this, scheduleCreated);
            scheduleCreated.publishAfterCommit();
        //}
    }

    @PostUpdate
    public void onPostUpdate(){
        ScheduleUpdated scheduleUpdated = new ScheduleUpdated();
        BeanUtils.copyProperties(this, scheduleUpdated);
        scheduleUpdated.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist(){
        if(this.getStatus().equals("Deleted")) {
            ScheduleDeleted scheduleDeleted = new ScheduleDeleted();
            BeanUtils.copyProperties(this, scheduleDeleted);
            scheduleDeleted.publishAfterCommit();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public Long getGroundId() {
        return groundId;
    }

    public void setGroundId(Long groundId) {
        this.groundId = groundId;
    }
    public String getGroundType() {
        return groundType;
    }

    public void setGroundType(String groundType) {
        this.groundType = groundType;
    }
    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
