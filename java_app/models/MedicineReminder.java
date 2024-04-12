package models;

import java.sql.Date;

public class MedicineReminder {
    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private Date startDate;
    private Date endDate;

    public MedicineReminder(int id, int userId, String medicineName, String dosage, String schedule, Date startDate, Date endDate){
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public String getMedicineName() {
        return medicineName;
    }
    public String getDosage() {
        return dosage;
    }
    public String getSchedule() {
        return schedule;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reminder Id: "+id+", User Id: "+userId+", Medicine Name: "+medicineName+", Dosage: "+dosage+", Schedule: "+schedule+", Start Date: "+startDate+", End Date: "+endDate;
    }
}
