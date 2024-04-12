package services;
//import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import models.Doctor;
import models.User;

public class UserDao {
   
    
    /** uploads a user object to the database
     * @param user user to be uploaded
     * @return boolean sucess of the operation
     */
    public boolean createUser(User user) {
        //String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String queryString = "INSERT INTO Users (first_name, last_name, email, password, is_doctor) VALUES (?, ?, ?, ?, false)";
        boolean inserted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            inserted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return inserted;
    }
    
    /** Returns all users from the database
     * @return ArrayList<Users> list of Users
     */
    public ArrayList<User> getUsers(){
        String queryString = "SELECT * FROM Users";
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                users.add(new User(result.getInt("user_id"), result.getString("first_name"), result.getString("last_name") , result.getString("email"), result.getString("password"), result.getBoolean("is_doctor")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }

    /** Returns singular user by id.
     * @param id of user
     * @return User desired user
     */
    public User getUserById(int id) { //get user by id from database 
        String queryString = "SELECT * FROM Users WHERE user_id = ?";
        User user = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            user = new User(result.getInt("user_id"), result.getString("first_name"), result.getString("last_name") , result.getString("email"), result.getString("password"), result.getBoolean("is_doctor"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    /** Returns doctor by id.
     * @param id of doctor
     * @return Doctor desired doctor
     */
    public Doctor getDoctorById(int id){
        String queryString = "SELECT * FROM Users WHERE user_id = ?";
        Doctor doctor = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            doctor = new Doctor(result.getInt("user_id"), result.getString("first_name"), result.getString("last_name") , result.getString("email"), result.getString("password"), result.getBoolean("is_doctor"),result.getString("medical_license_number"),result.getString("specialization"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return doctor;
    }
    /** Returns singular user by email.
     * @param email email of user
     * @return User desired user
     */
    public User getUserByEmail(String email) { // get user by email from database 
        String queryString = "SELECT * FROM Users WHERE email = ?";
        User user = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setString(1, email);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            user = new User(result.getInt("user_id"), result.getString("first_name"), result.getString("last_name") , result.getString("email"), result.getString("password"), result.getBoolean("is_doctor"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    /** Updates a user on the database. returns true if operation was sucessful
     * @param User new user
     * @return boolean success of operation
     */
    public boolean updateUser(User user) {
        String queryString = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ? WHERE user_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.isDoctor());
            preparedStatement.setInt(6, user.getId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return updated;
    }

    /** Updates a doctor on the database. returns true if operation was sucessful
     * @param Doctor doctor to be updated
     * @return boolean success of operation
     */
    public boolean updateDoctor(Doctor doctor){
        String queryString = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ?, medical_license_number = ?, specialization = ? WHERE user_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getEmail());
            preparedStatement.setString(4, doctor.getPassword());
            preparedStatement.setBoolean(5, doctor.isDoctor());
            preparedStatement.setString(6, doctor.getMedicalLicenseNumber());
            preparedStatement.setString(7, doctor.getSpecialization());
            preparedStatement.setInt(8, doctor.getId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return updated;
    }

    /** Deletes a user on the database. returns true if operation was successful
     * @param id id of user
     * @return boolean success of operation
     */
    public boolean deleteUser(int id) { // delete user from the database 
        String queryString = "DELETE FROM Users WHERE user_id = ?";
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

    /** checks if given user's password matches with the given password.
     * the password on the database is encrypted, so it requires BCrypt to
     * verify the password
     * @param User user to be logged in
     * @param password to be checked
     * @return boolean result of the passwords matching
     */
    public boolean verifyPassword(User user, String password) {
        String queryString = "SELECT password FROM users WHERE email = ?"; 
        boolean match = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setString(1, user.getEmail());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                match = BCrypt.checkpw(password, result.getString("password"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return match;
    }

}
