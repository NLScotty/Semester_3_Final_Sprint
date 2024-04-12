// Some files I used for initial testing


package tests;

import services.UserDao;

import java.util.ArrayList;

import models.User;

public class userdaotests {
    static UserDao userDAO = new UserDao();
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("get users:");
        System.out.println("====================");
        ArrayList<User> users = userDAO.getUsers();
        System.out.println(users);

        System.out.println("");
        System.out.println("get user by id:");
        System.out.println("====================");
        User user = userDAO.getUserById(2);
        System.out.println(user);

        System.out.println("");
        System.out.println("get user by email:");
        System.out.println("====================");
        user = userDAO.getUserByEmail("jdoe@gmail.com");
        System.out.println(user);

        System.out.println("");
        System.out.println("insert user test:");
        System.out.println("====================");
        boolean inserted = userDAO.createUser(new User(0, "New", "Guy", "newguy@email.com", "password", false));
        System.out.println(inserted);
        System.out.println(userDAO.getUserByEmail("newguy@email.com"));

        System.out.println("");
        System.out.println("update user test:");
        System.out.println("====================");
        User desiredUser = userDAO.getUserByEmail("newguy@email.com");
        boolean updated = userDAO.updateUser(new User(desiredUser.getId(), "Old", "Guy", "newguy@email.com", "password", false));
        System.out.println(updated);
        System.out.println(userDAO.getUserByEmail("newguy@email.com"));

        System.out.println("");
        System.out.println("verify password test:");
        System.out.println("====================");
        boolean bool1 = userDAO.verifyPassword(desiredUser, "password");
        boolean bool2 = userDAO.verifyPassword(desiredUser, "wrongpassword");
        System.out.println("bool1: "+bool1+" bool2: "+bool2);

        System.out.println("");
        System.out.println("get delete user test:");
        System.out.println("====================");
        boolean deleted = userDAO.deleteUser(desiredUser.getId());
        System.out.println(deleted);

    }
}
