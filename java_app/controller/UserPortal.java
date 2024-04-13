package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

import models.User;
import models.Doctor;
import models.HealthData;
import models.MedicineReminder;
import models.Recommendation;
import models.PatientDoctor;

import services.UserDao;
import services.MedicineReminderDao;
import services.HealthDataDao;
import services.RecommendationDao;
import services.PatientDoctorDao;

import java.util.Scanner;

public class UserPortal {
    private boolean isActive;
    private User user;
    private static UserDao userDao = new UserDao();
    private static MedicineReminderDao reminderDao = new MedicineReminderDao();
    private static HealthDataDao dataDao = new HealthDataDao();
    private static RecommendationDao recommendationDao = new RecommendationDao();
    private static PatientDoctorDao patientDoctorDao = new PatientDoctorDao();
    Scanner terminalInput = new Scanner(System.in);

    /** Portal Object Constructor. 
     * @param user User
     */
    public UserPortal(User user){
        this.user = user;
        this.isActive = true;
    }

    
    /** Sets the status of the session.
     * @param isActive
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    /** Returns if session is active. Used by main method to continue running
     * the session
     * @param isActive
     */
    public boolean isActive(){
        return isActive;
    }

    /**
     * The major method of the portal. It runs the commandline and uses
     * the DAO objets to make changes to teh database and give the application
     * functionality. In hindsight; I should have split this into a proper dedicated controller
     * and a view.
     */
    public void run(){
        System.out.println("Hello "+user.getFirstName() +" "+user.getLastName());
        System.out.println("");
        System.out.println("Please select an option (1-12)");
        System.out.println("===========================================");
        System.out.println("1. View Personal Information");
        System.out.println("2. Change Personal Information");
        System.out.println("3. Create a Reminder");
        System.out.println("4. View Reminders");
        System.out.println("5. Delete a Reminder");
        System.out.println("6. View Health Data");
        System.out.println("7. Insert Health Data");
        System.out.println("8. Generate Reccomendations");
        System.out.println("9. View Reccomendations");
        System.out.println("10. Clear Recommendations");
        System.out.println("11. View Assigned Doctors");
        System.out.println("12. Logout");

        String input = terminalInput.next();
        if(input.equals("1")){
            try{
                System.out.println("");
                System.out.println("Account Details: ");
                System.out.println("");
                System.out.println("First Name: "+ user.getFirstName());
                System.out.println("Last Name: "+ user.getLastName());
                System.out.println("Email: "+user.getEmail());
                System.out.println("Password: ************");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
            catch(Exception e){
                e.printStackTrace(System.out);
            }
        }
        else if(input.equals("2")){
            try{
                String newFName;
                String newLName;
                String newEmail;
                String newPassword;

                System.out.println("");
                System.out.println("Update Account Details: ");
                System.out.println("");
                System.out.print("New First Name: ");
                newFName = terminalInput.next();
                System.out.print("New Last Name: ");
                newLName = terminalInput.next();
                System.out.print("New Email: ");
                newEmail = terminalInput.next();
                System.out.print("New Password: ");
                newPassword = BCrypt.hashpw(terminalInput.next(), BCrypt.gensalt());
                System.out.println("");
                User updatedUser = new User(this.user.getId(), newFName, newLName, newEmail, newPassword, false);
                boolean success = userDao.updateUser(updatedUser);
                if(success){
                    this.user = updatedUser;
                    System.out.println("The database was updated!");
                }
                else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.print("Unkown Error has occured");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("3")){
            try{
                String medicineName;
                String dosage;
                String schedule;
                String startDate;
                String endDate;

                System.out.println("");
                System.out.println("Create new Medicine Reminder: ");
                System.out.println("");
                System.out.print("Medicine Name: ");
                medicineName = terminalInput.next();
                System.out.print("Dosage Amount: ");
                dosage = terminalInput.next();
                System.out.print("Frequency/schedule: ");
                schedule = terminalInput.next();
                System.out.print("Start Date ('YYYY-MM-DD' format): ");
                startDate = terminalInput.next();
                System.out.print("End Date ('YYYY-MM-DD' format): ");
                endDate = terminalInput.next();
                MedicineReminder reminder = new MedicineReminder(0, this.user.getId(), medicineName, dosage, schedule, Date.valueOf(startDate), Date.valueOf(endDate));
                if(reminderDao.createMedicineReminder(reminder)){
                    System.out.println("The database was updated!");
                }else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }

                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.print("Unkown Error has occured");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("4")){
            try{
                ArrayList<MedicineReminder> reminders = reminderDao.getMedicineReminderByUserId(this.user.getId());
                for (MedicineReminder reminder : reminders) {
                    System.out.println("==============================================");
                    System.out.println("Reminder Id: "+reminder.getId());
                    System.out.println("");
                    System.out.println("Medicine Name: "+reminder.getMedicineName());
                    System.out.println("Medicine Dosage: "+reminder.getDosage());
                    System.out.println("Schedule: "+reminder.getSchedule());
                    System.out.println("Start Date: "+reminder.getStartDate());
                    System.out.println("End Date: "+reminder.getEndDate());                
                }
            }catch(Exception e){
                System.out.print("Unkown Error has occured");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
            System.out.println("==============================================");
            System.out.println("");
            System.out.println("Press Enter to return to main menu...");
            System.out.println("");
            terminalInput.nextLine();
            terminalInput.nextLine();
        }
        else if(input.equals("5")){
            try{
                System.out.println("");
                System.out.print("Enter Reminder id: ");
                int id = terminalInput.nextInt();
                if(reminderDao.deleteMedicineReminder(id)){
                    System.out.println("The database was updated!");
                }
                else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("6")){
            try{
                ArrayList<HealthData> healthData = dataDao.getHealthDataByUserId(this.user.getId());
                for (HealthData record : healthData) {
                    System.out.println("Health Data Id: "+record.getId());
                    System.out.println("Health Data Date: "+record.getDate());
                    System.out.println("Recorded Height: "+record.getHeight());
                    System.out.println("Recorded Weight: "+record.getWeight());
                    System.out.println("Recorded Steps: "+record.getSteps());
                    System.out.println("Recorded Hearth Rate: "+record.getHeartRate());
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("7")){
            try{
                double height;
                double weight;
                int steps;
                int heartRate;

                System.out.print("Insert Current Height: ");
                height = terminalInput.nextDouble();
                System.out.print("Insert Current Weight: ");
                weight = terminalInput.nextDouble();
                System.out.print("Insert Today's Steps: ");
                steps = terminalInput.nextInt();
                System.out.print("Insert Current Hearth Rate: ");
                heartRate = terminalInput.nextInt();
                HealthData newHData = new HealthData(0, this.user.getId(), weight, height, steps, heartRate, new Date(System.currentTimeMillis()));
                if(dataDao.createHealthData(newHData)){
                    System.out.println("The database was updated!");
                }else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("8")){
            try{
                ArrayList<HealthData> healthData = dataDao.getHealthDataByUserId(user.getId());
                if(healthData.size() == 0){
                    System.out.println("No health data in the database, please enter health data and try again.");
                }
                else{
                    HealthData latestData = healthData.get(0);
                    
                    for (HealthData data : healthData) {
                        if(data.getDate().after(latestData.getDate())){
                            latestData = data;
                        }
                    }

                    double BMI = latestData.getWeight() / Math.pow(latestData.getHeight(), 2);
                    if(BMI < 18.5){
                        recommendationDao.createRecommendation(new Recommendation(0, user.getId(), "You are underweight!", new Date(System.currentTimeMillis())));
                    }
                    else if(BMI >24.8){
                        recommendationDao.createRecommendation(new Recommendation(0, user.getId(), "You are overweight!", new Date(System.currentTimeMillis())));
                    }
                    if(latestData.getSteps() < 7000){
                        recommendationDao.createRecommendation(new Recommendation(0, user.getId(), "You are still off the recommended steps for today; get walking!", new Date(System.currentTimeMillis())));
                    }
                    if(latestData.getHeartRate() < 60){
                        recommendationDao.createRecommendation(new Recommendation(0, user.getId(), "You have a low heart rate, look into it!", new Date(System.currentTimeMillis())));
                    }
                    else if(latestData.getHeartRate() > 100){
                        recommendationDao.createRecommendation(new Recommendation(0, user.getId(), "You have a high heart rate, look into it!", new Date(System.currentTimeMillis())));
                    }
                    System.out.println("Recommendations Generated!");
                    System.out.println("");
                    System.out.println("Press Enter to return to main menu...");
                    System.out.println("");
                    terminalInput.nextLine();
                    terminalInput.nextLine();
                }
            }catch(Exception e){
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("9")){
            try{
                ArrayList<Recommendation> recommendations = recommendationDao.getRecommendationByUserId(this.user.getId());
                for (Recommendation record : recommendations) {
                    System.out.println("Recommendation Id: "+record.getId());
                    System.out.println("Recommendation Text: "+record.getRecommendationText());
                    System.out.println("Recommendation Date: "+record.getDate());
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("10")){
            try{
                ArrayList<Recommendation> recommendations = recommendationDao.getRecommendationByUserId(this.user.getId());
                for (Recommendation recommendation : recommendations) {
                    recommendationDao.deleteRecommendation(recommendation.getId());
                }
                System.out.println("Recommendations cleared!");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.println("Unkown error has occured");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("11")){
            try{
                ArrayList<PatientDoctor> doctorPatients = patientDoctorDao.getPatientDoctorByPatientId(this.user.getId());
                for (PatientDoctor patientDoctor : doctorPatients) {
                    Doctor doctor = userDao.getDoctorById(patientDoctor.getDoctorId());
                    System.out.println("--------------------------------");
                    System.out.println("Dr."+doctor.getLastName());
                    System.out.println("Field: "+doctor.getSpecialization());
                }
                System.out.println("--------------------------------");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }catch(Exception e){
                System.out.println("");
                System.out.println("Unable to complete operation");
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
                terminalInput.nextLine();
                terminalInput.nextLine();
            }
        }
        else if(input.equals("12")){
            this.isActive = false;
        }
        else{
            System.out.println("");
        }
    }

}
