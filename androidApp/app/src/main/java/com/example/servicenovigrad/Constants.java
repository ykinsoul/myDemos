package com.example.servicenovigrad;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static ArrayList<String> WEEK = new ArrayList<>();
    public static String CITY = "Novigrad";
    public static String PROVINCE = "Redania";
    public static Profile DEFAULTPROFILE = new Profile();
    private static boolean initialized = false;

    private Constants() {
    }

    public static void initializeData() {

        WEEK = new ArrayList<>(Arrays.asList
                ("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        DEFAULTPROFILE.setAddressLine("Not set");
        DEFAULTPROFILE.setFirstName("Not set");
        DEFAULTPROFILE.setLastName("Not set");
        DEFAULTPROFILE.setPhoneNumber("Not set");
        DEFAULTPROFILE.setPostalCode("Not set");

        AdminUser admin = new AdminUser();
        UserDatabase.users.addUser(admin);

        Service driverLicense = new Service("Driver's License", 30);
        driverLicense.addInfo("First name");
        driverLicense.addInfo("Last name");
        driverLicense.addInfo("Date of birth");
        driverLicense.addInfo("Address");
        driverLicense.addInfo("License type");
        driverLicense.addDocument("Proof of residence");
        UserDatabase.services.add(driverLicense);

        Service healthCard = new Service("Health card", 20);
        healthCard.addInfo("First name");
        healthCard.addInfo("Last name");
        healthCard.addInfo("Date of birth");
        healthCard.addInfo("Address");
        healthCard.addDocument("Proof of residence");
        healthCard.addDocument("Proof of status");
        UserDatabase.services.add(healthCard);

        Service photoID = new Service("Photo ID", 10);
        photoID.addInfo("First name");
        photoID.addInfo("Last name");
        photoID.addInfo("Date of birth");
        photoID.addInfo("Address");
        photoID.addDocument("Proof of residence");
        photoID.addDocument("Proof of the customer");
        UserDatabase.services.add(photoID);

        initialized = true;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
