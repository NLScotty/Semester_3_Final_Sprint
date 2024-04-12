// Some files I used for initial testing

package tests;

import services.HealthDataDao;

import java.sql.Date;
import java.util.ArrayList;

import models.HealthData;

public class healthdaotests {
    static HealthDataDao healthDAO = new HealthDataDao();
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("get health data:");
        System.out.println("====================");
        ArrayList<HealthData> hdata = healthDAO.getHealthData();
        System.out.println(hdata);

        System.out.println("");
        System.out.println("get health data by id:");
        System.out.println("====================");
        HealthData desiredHData = healthDAO.getHealthDataById(1);
        System.out.println(desiredHData);

        System.out.println("");
        System.out.println("get health data by user id:");
        System.out.println("====================");
        hdata = healthDAO.getHealthDataByUserId(2);
        System.out.println(hdata);

        System.out.println("");
        System.out.println("insert health data:");
        System.out.println("====================");
        Boolean created = healthDAO.createHealthData(new HealthData(0, 2, 160, 1.90, 21340, 74, Date.valueOf("2024-04-03")));
        System.out.println(created);
        System.out.println(healthDAO.getHealthDataByUserId(2));
    }
}
