package com.example.servicenovigrad;

import java.util.ArrayList;

public class ServiceRequest {
    private final ArrayList<String> customerInputs;
    private final String customerUsername;
    private final String employeeUsername;
    private final String serviceName;
    private final float paid;
    private boolean reviewed;
    private Status status = Status.PENDING;
    private String appointmentDate;
    private String appointmentTime;

    public ServiceRequest(String customerUsername, String employeeUsername, float paid, ArrayList<String> customerInputs, String serviceName) {
        this.customerUsername = customerUsername;
        this.employeeUsername = employeeUsername;
        this.paid = paid;
        this.customerInputs = customerInputs;
        this.serviceName = serviceName;
    }

    public ArrayList<String> getCustomerInputs() {
        return customerInputs;
    }

    public float getPaid() {
        return paid;
    }

    public void accept() {
        status = Status.ACCEPTED;
        CustomerUser customer = ((CustomerUser) UserDatabase.users.getUserByName(customerUsername));
        customer.requestNotification = true;
    }

    public void refuse() {
        status = Status.REFUSED;
        CustomerUser customer = ((CustomerUser) UserDatabase.users.getUserByName(customerUsername));
        customer.requestNotification = true;
    }

    public Status getStatus() {
        return status;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setReviewed() {
        reviewed = true;
    }

    public boolean getReviewed() {
        return reviewed;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String time) {
        appointmentTime = time;
    }
}
