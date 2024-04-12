package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.MedicineReminder;

public class MedicineReminderDao {
    
    /** 
     * Add a medicine remider to the database
     * @param medicineReminder new medicine reminder
     * @return boolean success of the operation
     */
    public boolean createMedicineReminder(MedicineReminder medicineReminder) {
        String queryString = "INSERT INTO Medicine_Reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        boolean inserted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, medicineReminder.getUserId());
            preparedStatement.setString(2, medicineReminder.getMedicineName());
            preparedStatement.setString(3, medicineReminder.getDosage());
            preparedStatement.setString(4, medicineReminder.getSchedule());
            preparedStatement.setDate(5, medicineReminder.getStartDate());
            preparedStatement.setDate(6, medicineReminder.getEndDate());
            inserted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return inserted;
    }
    
    /** Returns all medicine reminders
     * @return ArrayList<MedicineReminder> list of reminders
     */
    public ArrayList<MedicineReminder> getMedicineReminder(){
        String queryString = "SELECT * FROM Medicine_Reminders";
        ArrayList<MedicineReminder> medicineReminder = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                medicineReminder.add(new MedicineReminder(result.getInt("reminder_id"), result.getInt("user_id"), result.getString("medicine_name"), result.getString("dosage"), result.getString("dosage"), result.getDate("start_date"), result.getDate("end_date")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return medicineReminder;
    }

    /** Returns medicine reminder data by reminder id.
     * @param id id of medicine reminder
     * @return medicine reminder singular health data
     */
    public MedicineReminder getMedicineReminderById(int id) { //get medicineReminder by id from database 
        String queryString = "SELECT * FROM Medicine_Reminders WHERE reminder_id = ?";
        MedicineReminder medicineReminder = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            medicineReminder = new MedicineReminder(result.getInt("reminder_id"), result.getInt("user_id"), result.getString("medicine_name"), result.getString("dosage"), result.getString("dosage"), result.getDate("start_date"), result.getDate("end_date"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return medicineReminder;
    }

    /** Returns all reminders by a user id
     * @param user_id
     * @return ArrayList<Medicine Reminder> list of reminders belonging to user
     */
    public ArrayList<MedicineReminder> getMedicineReminderByUserId(int user_id){
      String queryString = "SELECT * FROM Medicine_Reminders WHERE user_id = ?";
      ArrayList<MedicineReminder> medicineReminder = new ArrayList<>();
      try {
          Connection con = DatabaseConnection.getCon();
          PreparedStatement queryStatement = con.prepareStatement(queryString);
          queryStatement.setInt(1, user_id);
          ResultSet result = queryStatement.executeQuery();
          while(result.next()){
              medicineReminder.add(new MedicineReminder(result.getInt("reminder_id"), result.getInt("user_id"), result.getString("medicine_name"), result.getString("dosage"), result.getString("dosage"), result.getDate("start_date"), result.getDate("end_date")));
          }
      }catch(Exception e){
          e.printStackTrace();
      }
      return medicineReminder;
    }

    /** Updates a medicine reminder on the database. returns true if operation was sucessful
     * @param medicineReminder
     * @return boolean success of operation
     */
    public boolean updateMedicineReminder(MedicineReminder medicineReminder) {
        String queryString = "UPDATE Medicine_Reminders SET user_id = ?, medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? WHERE reminder_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, medicineReminder.getUserId());
            preparedStatement.setString(2, medicineReminder.getMedicineName());
            preparedStatement.setString(3, medicineReminder.getDosage());
            preparedStatement.setString(4, medicineReminder.getSchedule());
            preparedStatement.setDate(5, medicineReminder.getStartDate());
            preparedStatement.setDate(6, medicineReminder.getEndDate());
            preparedStatement.setInt(7, medicineReminder.getId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return updated;
    }

    /** deletes a medicine reminder on the database. returns true if operation was sucessful
     * @param id
     * @return boolean success of operation
     */
    public boolean deleteMedicineReminder(int id) { // delete medicineReminder from the database 
        String queryString = "DELETE FROM Medicine_Reminders WHERE reminder_id = ?";
        boolean deleted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return deleted;
    }
}
