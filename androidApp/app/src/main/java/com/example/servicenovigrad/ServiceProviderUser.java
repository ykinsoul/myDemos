package com.example.servicenovigrad;


import java.sql.Time;
import java.util.ArrayList;

public class ServiceProviderUser extends User {

    private final ArrayList<Time> workingHoursFrom = new ArrayList<>();
    private final ArrayList<Time> workingHoursTo = new ArrayList<>();
    private final ServiceList registeredServices = new ServiceList();
    private boolean profileCompleted = false;
    private Profile profile = new Profile();
    private final ArrayList<Float> ratings = new ArrayList<>();
    private final ArrayList<String> reviews = new ArrayList<>();

    public ServiceProviderUser(String username, String password) {
        super(username, password);
        Time time = new Time(0, 0, 0);
        for (int i = 0; i < 7; i++) {
            workingHoursFrom.add(time);
            workingHoursTo.add(time);
        }
        setRole("Employee");
        setProfile(Constants.DEFAULTPROFILE);
    }

    public boolean getCompleted() {
        return profileCompleted;
    }

    public void setCompleted() {
        profileCompleted = true;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void addService(Service service) {
        registeredServices.add(service);
    }

    public ArrayList<String> getRegisteredServicesName() {
        return registeredServices.getList();
    }

    public ArrayList<Service> getRegisteredServices() {
        return registeredServices.getServices();
    }

    public boolean isServiceEmpty() {
        return registeredServices.isEmpty();
    }

    public ArrayList<Time> getWorkingHoursFrom() {
        return workingHoursFrom;
    }

    public ArrayList<Time> getWorkingHoursTo() {
        return workingHoursTo;
    }

    public void clearServices() {
        registeredServices.clear();
    }

    public void addRating(Float i) {
        ratings.add(i);
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public Service getServiceAt(int i) {
        return registeredServices.get(i);
    }

    public ArrayList<ServiceRequest> getRequests() {
        ArrayList<ServiceRequest> employeeRequests = new ArrayList<>();
        for (ServiceRequest request : UserDatabase.requests.getRequests()) {
            if (request.getEmployeeUsername().equals(getUsername()) && request.getStatus().equals(Status.PENDING)) {
                employeeRequests.add(request);
            }
        }
        return employeeRequests;
    }

    public ArrayList<String> getRequestsName() {
        ArrayList<String> employeeRequests = new ArrayList<>();
        for (ServiceRequest request : UserDatabase.requests.getRequests()) {
            if (request.getEmployeeUsername().equals(getUsername()) && request.getStatus().equals(Status.PENDING)) {
                employeeRequests.add("From user " + request.getCustomerUsername() + ", requesting a " + request.getServiceName());
            }
        }
        return employeeRequests;
    }

    public void accept(ServiceRequest request) {
        for (ServiceRequest request2 : UserDatabase.requests.getRequests()) {
            if (request.equals(request2)) {
                request2.accept();
                break;
            }
        }
    }

    public void refuse(ServiceRequest request) {
        for (ServiceRequest request2 : UserDatabase.requests.getRequests()) {
            if (request.equals(request2)) {
                request2.refuse();
                break;
            }
        }
    }
}