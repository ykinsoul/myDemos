package com.example.servicenovigrad;

import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;

public class CustomerUser extends User {

    private final UserList currentViewedBranches = new UserList();
    public boolean requestNotification = false;

    public CustomerUser(String username, String password) {
        super(username, password);
        setRole("Customer");
    }

    public UserList getCurrentViewedBranches() {
        return currentViewedBranches;
    }

    public int searchThroughAddress(String address) {
        int results = 0;
        currentViewedBranches.clear();
        for (User i : UserDatabase.users.getList()) {
            if (i.getClass().equals(ServiceProviderUser.class)) {
                Profile p = ((ServiceProviderUser) i).getProfile();
                if (p.getAddressLine().equalsIgnoreCase(address) || p.getPostalCode().equalsIgnoreCase(address)
                        || (address.length() >= 3 && (p.getAddressLine().contains(address) || p.getPostalCode().contains(address)))) {
                    if (!currentViewedBranches.userExist(i)) {
                        currentViewedBranches.addUser(i);
                        results++;
                    }
                }
            }
        }
        return results;
    }

    public int searchThroughService(String service) {
        int results = 0;
        currentViewedBranches.clear();
        for (User i : UserDatabase.users.getList()) {
            if (i.getClass().equals(ServiceProviderUser.class)) {
                ArrayList<String> services = ((ServiceProviderUser) i).getRegisteredServicesName();
                for (String str : services) {
                    if (str.equalsIgnoreCase(service) || (service.length() >= 4 && str.contains(service))) {
                        if (!currentViewedBranches.userExist(i)) {
                            currentViewedBranches.addUser(i);
                            results++;
                        }
                    }
                }
            }
        }
        return results;
    }

    public int searchThroughTime(String time) throws IllegalArgumentException {
        int results = 0;
        Time t = Time.valueOf(time);
        currentViewedBranches.clear();
        for (User i : UserDatabase.users.getList()) {
            if (i.getClass().equals(ServiceProviderUser.class)) {
                if (checkTime(t, (ServiceProviderUser) i)) {
                    if (!currentViewedBranches.userExist(i)) {
                        currentViewedBranches.addUser(i);
                        results++;
                    }
                }
            }
        }
        return results;
    }

    public ArrayList<ServiceRequest> getRequests() {
        ArrayList<ServiceRequest> employeeRequests = new ArrayList<>();
        for (ServiceRequest request : UserDatabase.requests.getRequests()) {
            if (request.getCustomerUsername().equals(getUsername())) {
                employeeRequests.add(request);
            }
        }
        return employeeRequests;
    }

    public ArrayList<String> getRequestsName() {
        ArrayList<String> customerRequests = new ArrayList<>();
        for (ServiceRequest request : UserDatabase.requests.getRequests()) {
            if (request.getCustomerUsername().equals(getUsername())) {
                customerRequests.add("Requested service " + request.getServiceName() + " from branch: " + request.getEmployeeUsername() + "\nStatus: " + request.getStatus().name());
            }
        }
        return customerRequests;
    }

    public boolean checkTime(Time time, @NotNull ServiceProviderUser employee) {
        ArrayList<Time> from = employee.getWorkingHoursFrom();
        ArrayList<Time> to = employee.getWorkingHoursTo();
        for (int i = 0; i < 7; i++) {
            Time fromTime = from.get(i);
            Time toTime = to.get(i);
            if (time.after(fromTime) && time.before(toTime)) {
                return true;
            }
        }
        return false;
    }
}