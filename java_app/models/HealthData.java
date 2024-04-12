package models;

import java.sql.Date;

public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private Date date;

    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, Date date){
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public double getWeight() {
        return weight;
    }
    public double getHeight() {
        return height;
    }
    public int getSteps() {
        return steps;
    }
    public int getHeartRate() {
        return heartRate;
    }
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "health_data_id: "+id+", user_id: "+userId+", weight: "+weight+", height: "+height+", steps: "+steps+", heartrate: "+heartRate+", date: "+date;
    }
}
