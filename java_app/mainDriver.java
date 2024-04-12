
import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

import controller.DoctorPortal;
import controller.UserPortal;
import models.User;
import services.UserDao;
public class mainDriver {
    static UserDao userDao = new UserDao();
    
    /** 
     * The Main Driver Method. It also has the logic/controller for user login
     * @param args command line arguments; unused in this application
     */
    public static void main(String[] args) {
        Scanner terminalInput = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Smart Health Monitoring System Terminal!");
            System.out.println("");
            System.out.println("Please Enter your Email, or type 'QUIT' to exit. Type 'NEW' for first time user: ");
            System.out.println("");
            System.out.print("Email: ");
            String email = terminalInput.next();
            if(email.equals("QUIT")){
                break;
            }
            else if(email.equals("NEW")){
                String fName;
                String lName;
                String newEmail;
                String password;

                System.out.println("");
                System.out.println("New Account Creation: ");
                System.out.print("");
                System.out.print("Enter your first name: ");
                fName = terminalInput.next();
                System.out.print("Enter your last name: ");
                lName = terminalInput.next();
                System.out.print("Enter your email: ");
                newEmail = terminalInput.next();
                System.out.print("Enter your password: ");
                password = terminalInput.next();
                String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                System.out.println("");
                if(userDao.createUser(new User(0, fName, lName, newEmail, encryptedPassword, false))){
                    System.out.println("Account Created! ");
                }else{
                    System.out.println("Failed to create an account");
                }
                System.out.println("");
                continue;
            }
            System.out.println("Please Enter your Password: ");
            System.out.print("Password: ");
            String password = terminalInput.next();
            User user = userDao.getUserByEmail(email);
            if(userDao.verifyPassword(user, password)){
                if(user.isDoctor()){
                    DoctorPortal doctorPortal = new DoctorPortal(userDao.getDoctorById(user.getId()));
                    while(doctorPortal.isActive()){
                        doctorPortal.run();
                    }
                }

                else{
                    UserPortal userPortal = new UserPortal(user);
                    while(userPortal.isActive()){
                        userPortal.run();
                    }
                }
            }
            else{
                System.out.println("");
                System.out.println("Invalid Login, please try again!");
                System.out.println("");
            }
        }
    }
}
