package com.eltobeski.invoicingapp.model;

import java.util.Date;

public class Timesheet {

    private Long employeeId;

    private Long billableRate;

    private String project;

    private Date workDate;

    private Date startTime;

    private Date endTime;

    private Long workDuration;

    public Timesheet() {
    }

    public Timesheet(Long employeeId, Long billableRate, String project, Date workDate, Date startTime, Date endTime, Long workDuration) {
        this.employeeId = employeeId;
        this.billableRate = billableRate;
        this.project = project;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDuration = workDuration;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getBillableRate() {
        return billableRate;
    }

    public void setBillableRate(Long billableRate) {
        this.billableRate = billableRate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(Long workDuration) {
        this.workDuration = workDuration;
    }
}
