package controller;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import models.*;
import services.*;

import java.util.Scanner;

public class DoctorPortal {
    private boolean isActive;
    private Doctor doctor;
    private static UserDao userDao = new UserDao();
    private static PatientDoctorDao patientDoctorDao = new PatientDoctorDao();
    private static HealthDataDao healthDataDao = new HealthDataDao();
    private static MedicineReminderDao reminderDao = new MedicineReminderDao();
    private static RecommendationDao recommendationDao = new RecommendationDao();
    Scanner terminalInput = new Scanner(System.in);
    /** Portal Object Constructor. 
     * @param doctor Doctor
     */
    public DoctorPortal(Doctor doctor){
        this.doctor = doctor;
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
        System.out.print("Hello Dr."+doctor.getLastName());
        System.out.println("");
        System.out.println("Please select an option (1-8)");
        System.out.println("===========================================");
        System.out.println("1. View Personal Information");
        System.out.println("2. Change Personal Information");
        System.out.println("3. View Personal Patients");
        System.out.println("4. Add New Personal Patient");
        System.out.println("5. View Health Data of Patient");
        System.out.println("6. Create New Recommendation for Patient");
        System.out.println("7. Create New Medicine Reminder for Patient");
        System.out.println("8. Logout");

        String input = terminalInput.next();

        if(input.equals("1")){
            try{
                System.out.println("");
                System.out.print("Account Details: ");
                System.out.println("");
                System.out.println("First Name: "+ doctor.getFirstName());
                System.out.println("Last Name: "+ doctor.getLastName());
                System.out.println("Email: "+doctor.getEmail());
                System.out.println("Password: ************");
                System.out.println("Medical License Number: "+doctor.getMedicalLicenseNumber());
                System.out.println("Specialization: "+doctor.getSpecialization());
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
        else if(input.equals("2")){
            try{
                String newFName;
                String newLName;
                String newEmail;
                String newPassword;
                String newMedicalLicenseNumber;
                String newSpecialization;

                System.out.println("");
                System.out.println("Update Account Details: ");
                System.out.println("");
                System.out.println("New First Name: ");
                newFName = terminalInput.next();
                System.out.println("New Last Name: ");
                newLName = terminalInput.next();
                System.out.println("New Email: ");
                newEmail = terminalInput.next();
                System.out.println("New Password: ");
                newPassword = BCrypt.hashpw(terminalInput.next(), BCrypt.gensalt());
                System.out.println("New Medical License Number: ");
                newMedicalLicenseNumber = terminalInput.next();
                System.out.println("New Specialization: ");
                terminalInput.nextLine();
                newSpecialization = terminalInput.nextLine();
                System.out.println("");
                Doctor updatedDoctor = new Doctor(this.doctor.getId(), newFName, newLName, newEmail, newPassword, true, newMedicalLicenseNumber, newSpecialization);
                boolean success = userDao.updateDoctor(updatedDoctor);
                if(success){
                    this.doctor = updatedDoctor;
                    System.out.println("The database was updated!");
                }
                else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }
                System.out.println("");
                System.out.println("Press Enter to return to main menu...");
                System.out.println("");
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
        else if(input.equals("3")){
            try{
                ArrayList<PatientDoctor> patients = patientDoctorDao.getPatientDoctorByDoctorId(doctor.getId());
                for (PatientDoctor patientDoctor : patients) {
                    User patient = userDao.getUserById(patientDoctor.getPatientId());
                    System.out.println("==============================================");
                    System.out.println("Patient ID: " + patient.getId());
                    System.out.println("First Name: " + patient.getFirstName());
                    System.out.println("Last Name: " + patient.getLastName());
                    System.out.println("Email: " + patient.getEmail());
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
            System.out.println("==============================================");
        }
        else if(input.equals("4")){
            try{
                System.out.println("==============================================");
                System.out.print("Please enter patient ID: ");
                int id = terminalInput.nextInt();
                if(patientDoctorDao.createPatientDoctor(new PatientDoctor(id, doctor.getId()))){
                    System.out.println("The database was updated!");
                }
                else{
                    System.out.println("The database was not updated. Changes were discarded!");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(input.equals("5")){
            try{
                System.out.println("==============================================");
                System.out.print("Please enter patient ID: ");
                int id = terminalInput.nextInt();
                ArrayList<HealthData> healthData = healthDataDao.getHealthDataByUserId(id);
                for (HealthData record : healthData) {
                    System.out.println("==============================================");
                    System.out.println("Health Data Id: "+record.getId());
                    System.out.println("Health Data Date: "+record.getDate());
                    System.out.println("Recorded Height: "+record.getHeight());
                    System.out.println("Recorded Weight: "+record.getWeight());
                    System.out.println("Recorded Steps: "+record.getSteps());
                    System.out.println("Recorded Hearth Rate: "+record.getHeartRate());
                }
                System.out.println("==============================================");
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
                int patientId;
                String recommendationText;

                System.out.println("");
                System.out.println("Create new recommendation: ");
                System.out.println("");
                System.out.print("Patient id: ");
                patientId = terminalInput.nextInt();
                System.out.print("Reccomendation : ");
                terminalInput.nextLine();
                recommendationText = terminalInput.nextLine();

                if(recommendationDao.createRecommendation(new Recommendation(0, patientId, recommendationText, new Date(System.currentTimeMillis())))){
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
        else if(input.equals("7")){
            try{
                int patientId;
                String medicineName;
                String dosage;
                String schedule;
                String startDate;
                String endDate;

                System.out.println("");
                System.out.println("Create new Medicine Reminder: ");
                System.out.println("");
                System.out.print("Patient id: ");
                patientId = terminalInput.nextInt();
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
                MedicineReminder reminder = new MedicineReminder(0, patientId, medicineName, dosage, schedule, Date.valueOf(startDate), Date.valueOf(endDate));
                reminderDao.createMedicineReminder(reminder);
                
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
            this.isActive = false;
        }
        else{
            System.out.println("");
        }
    }

}
