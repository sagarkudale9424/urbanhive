package com.urbanhive.model;

public class VisitorLog {
    private int id;
    private Integer residentId;
    private String visitorName;
    private String purpose;
    private String entryTime;
    private String exitTime;
    private Integer approvedByGuard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public Integer getApprovedByGuard() {
        return approvedByGuard;
    }

    public void setApprovedByGuard(Integer approvedByGuard) {
        this.approvedByGuard = approvedByGuard;
    }
}
