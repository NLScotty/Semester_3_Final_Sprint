package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.PatientDoctor;

public class PatientDoctorDao {
    
    /** creates a patient doctor relationship on the database
     * @param patientDoctor
     * @return boolean succuss of the operation
     */
    public boolean createPatientDoctor(PatientDoctor patientDoctor) {
        String queryString = "INSERT INTO Doctor_Patient (doctor_id, patient_id) VALUES (?, ?)";
        boolean inserted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, patientDoctor.getDoctorId());
            preparedStatement.setInt(2, patientDoctor.getPatientId());
            inserted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return inserted;
    }
    
    /** Returns all Doctor Patient Relationships
     * @return ArrayList<PatiendDoctor> list of relationships
     */
    public ArrayList<PatientDoctor> getPatientDoctor(){
        String queryString = "SELECT * FROM Doctor_Patient";
        ArrayList<PatientDoctor> patientDoctor = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                patientDoctor.add(new PatientDoctor(result.getInt("patient_id"), result.getInt("doctor_id")));
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        return patientDoctor;
    }

    /** Returns relationship by patient id.
     * @param id id of patient
     * @return PatientDoctor doctor patient relationship
     */
    public ArrayList<PatientDoctor> getPatientDoctorByPatientId(int id) { //get patientDoctor by id from database 
        String queryString = "SELECT * FROM Doctor_Patient WHERE patient_id = ?";
        ArrayList<PatientDoctor> patientDoctors = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                patientDoctors.add(new PatientDoctor(result.getInt("patient_id"), result.getInt("doctor_id")));
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        return patientDoctors;
    }

    /** Returns relationship by doctor id.
     * @param id of doctor
     * @return PatientDoctor doctor patient relationship
     */
    public ArrayList<PatientDoctor> getPatientDoctorByDoctorId(int id) { //get patientDoctor by id from database 
        String queryString = "SELECT * FROM Doctor_Patient WHERE doctor_id = ?";
        ArrayList<PatientDoctor> patientDoctors = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                patientDoctors.add(new PatientDoctor(result.getInt("patient_id"), result.getInt("doctor_id")));
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        return patientDoctors;
    }
    
    /** Updates the doctor in the realtionship
     * @param patientDoctor new patientDoctor
     * @return boolean success of operation
     */
    public boolean updatePatientDoctor(PatientDoctor patientDoctor) {
        String queryString = "UPDATE Doctor_Patient SET doctor_id = ? WHERE patient_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, patientDoctor.getDoctorId());
            preparedStatement.setInt(2, patientDoctor.getPatientId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return updated;
    }

    /** Updates the patient in the realtionship
     * @param patientDoctor updated relationship
     * @return boolean success of operation
     */
    public boolean updateDoctorPatient(PatientDoctor patientDoctor) {
        String queryString = "UPDATE Doctor_Patient SET patient_id = ? WHERE doctor_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(2, patientDoctor.getDoctorId());
            preparedStatement.setInt(1, patientDoctor.getPatientId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return updated;
    }

    /** deletes a relationship. returns true if operation was sucessful
     * @param patientId
     * @param doctorId
     * @return boolean success of operation
     */
    public boolean deletePatientDoctor(int doctorId, int patientId) { // delete patientDoctor from the database 
        String queryString = "DELETE FROM Doctor_Patient WHERE doctor_id = ? AND patient_id = ?";
        boolean deleted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setInt(2, patientId);
            deleted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            //e.printStackTrace();
        }
        return deleted;
    }
}
