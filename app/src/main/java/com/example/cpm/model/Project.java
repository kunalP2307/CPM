package com.example.cpm.model;

import java.util.ArrayList;

public class Project {

    private String projectName, startDate, expectedEndDate;
    private int  durationInDays ,noActCompletedOnTime, noActCompletedWithDelay, noOfActYetToStart;
    ArrayList listActivities;

    public Project(){}

    public Project(String projectName, String startDate, String expectedEndDate, int durationInDays, int noActCompletedOnTime, int noActCompletedWithDelay, int noOfActYetToStart, ArrayList<ArrayList<Activity>> listActivities) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.durationInDays = durationInDays;
        this.noActCompletedOnTime = noActCompletedOnTime;
        this.noActCompletedWithDelay = noActCompletedWithDelay;
        this.noOfActYetToStart = noOfActYetToStart;
        this.listActivities = listActivities;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getNoActCompletedOnTime() {
        return noActCompletedOnTime;
    }

    public void setNoActCompletedOnTime(int noActCompletedOnTime) {
        this.noActCompletedOnTime = noActCompletedOnTime;
    }

    public int getNoActCompletedWithDelay() {
        return noActCompletedWithDelay;
    }

    public void setNoActCompletedWithDelay(int noActCompletedWithDelay) {
        this.noActCompletedWithDelay = noActCompletedWithDelay;
    }

    public int getNoOfActYetToStart() {
        return noOfActYetToStart;
    }

    public void setNoOfActYetToStart(int noOfActYetToStart) {
        this.noOfActYetToStart = noOfActYetToStart;
    }

    public ArrayList<ArrayList<Activity>> getListActivities() {
        return listActivities;
    }

    public void setListActivities(ArrayList<ArrayList<Activity>> listActivities) {
        this.listActivities = listActivities;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", expectedEndDate='" + expectedEndDate + '\'' +
                ", durationInDays=" + durationInDays +
                ", noActCompletedOnTime=" + noActCompletedOnTime +
                ", noActCompletedWithDelay=" + noActCompletedWithDelay +
                ", noOfActYetToStart=" + noOfActYetToStart +
                ", listActivities=" + listActivities +
                '}';
    }
}
