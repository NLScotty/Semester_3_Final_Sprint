package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.HealthData;

public class HealthDataDao {
    
    /** Creates a HealthData Object, then pushes it to database
     * @param healthData
     * @return boolean returns if operation was sucessful or not
     */
    public boolean createHealthData(HealthData healthData) {
        String queryString = "INSERT INTO Health_Data (user_id, weight, height, steps , heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";
        boolean inserted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, healthData.getUserId());
            preparedStatement.setDouble(2, healthData.getWeight());
            preparedStatement.setDouble(3, healthData.getHeight());
            preparedStatement.setInt(4, healthData.getSteps());
            preparedStatement.setInt(5, healthData.getHeartRate());
            preparedStatement.setDate(6, healthData.getDate());
            inserted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return inserted;
    }
    
    /** Returns all health datas
     * @return ArrayList<HealthData> list of helath data
     */
    public ArrayList<HealthData> getHealthData(){
        String queryString = "SELECT * FROM Health_Data";
        ArrayList<HealthData> healthData = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                healthData.add(new HealthData(result.getInt("data_id"), result.getInt("user_id"), result.getDouble("weight"), result.getDouble("height"), result.getInt("steps"), result.getInt("heart_rate"), result.getDate("date")));
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        return healthData;
    }

    /** Returns singular helath data by data id.
     * @param id of health data
     * @return HealthData singular health data
     */
    public HealthData getHealthDataById(int id) { //get healthData by id from database 
        String queryString = "SELECT * FROM Health_Data WHERE data_id = ?";
        HealthData healthData = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            healthData = new HealthData(result.getInt("data_id"), result.getInt("user_id"), result.getDouble("weight"), result.getDouble("height"), result.getInt("steps"), result.getInt("heart_rate"), result.getDate("date"));
        }catch(Exception e){
            //e.printStackTrace();;
        }
        return healthData;
    }

    /** Returns all health datas by a user id
     * @param user_id of user
     * @return ArrayList<HealthData> list of health data of user id
     */
    public ArrayList<HealthData> getHealthDataByUserId(int user_id){
      String queryString = "SELECT * FROM Health_Data WHERE user_id = ?";
      ArrayList<HealthData> healthData = new ArrayList<>();
      try {
          Connection con = DatabaseConnection.getCon();
          PreparedStatement queryStatement = con.prepareStatement(queryString);
          queryStatement.setInt(1, user_id);
          ResultSet result = queryStatement.executeQuery();
          while(result.next()){
              healthData.add(new HealthData(result.getInt("data_id"), result.getInt("user_id"), result.getDouble("weight"), result.getDouble("height"), result.getInt("steps"), result.getInt("heart_rate"), result.getDate("date")));
          }
      }catch(Exception e){
          //e.printStackTrace();
      }
      return healthData;
    }
    
    /** Updates a health data on the database. returns true if operation was sucessful
     * @param healthData 
     * @return boolean success of operation
     */
    public boolean updateHealthData(HealthData healthData) {
        String queryString = "UPDATE Health_Data SET user_id = ?, weight = ?, height = ?, steps = ?, heart_rate= ?, date = ? WHERE data_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, healthData.getUserId());
            preparedStatement.setDouble(2, healthData.getWeight());
            preparedStatement.setDouble(3, healthData.getHeight());
            preparedStatement.setInt(4, healthData.getSteps());
            preparedStatement.setInt(5, healthData.getHeartRate());
            preparedStatement.setDate(6, healthData.getDate());
            preparedStatement.setInt(7, healthData.getId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return updated;
    }

    /** deletes a health data on the database. returns true if operation was sucessful
     * @param id of health data
     * @return boolean success of operation
     */
    public boolean deleteHealthData(int id) { // delete healthData from the database 
        String queryString = "DELETE FROM Health_Data WHERE data_id = ?";
        boolean deleted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return deleted;
    }
}
