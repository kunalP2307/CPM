package com.example.cpm.model;

import com.example.cpm.utils.ActivityUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {

    private String projectName, startDate, expectedEndDate;
    private int  durationInDays ,noActCompletedOnTime, noActCompletedWithDelay, noOfActYetToStart;
    ArrayList<ArrayList<Activity>> listActivities;

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

    public void setDelay(){
        for(int i=0; i<listActivities.size(); i++){
            for(int j=0; j<listActivities.get(i).size(); j++){
                Activity activity = listActivities.get(i).get(j);
                listActivities.get(i).get(j).setDelay(ActivityUtils.getDelay(activity));
            }
        }
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
