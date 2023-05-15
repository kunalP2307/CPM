package com.example.cpm.model;

import java.io.Serializable;

public class Activity implements Serializable {

    private String id, taskName, duration, startDate ,finishDate;
    private int delay;
    public Activity(){}

    public Activity(String id, String taskName, String duration, String startDate, String finishDate) {
        this.id = id;
        this.taskName = taskName;
        this.duration = duration;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", taskName='" + taskName + '\'' +
                ", duration='" + duration + '\'' +
                ", startDate='" + startDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", delay='" + delay + '\'' +
                '}';
    }
}
