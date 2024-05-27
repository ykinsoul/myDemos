package com.example.servicenovigrad;

public class UserDatabase {

    public static UserList users = new UserList();
    public static ServiceList services = new ServiceList();
    public static ServiceRequestList requests = new ServiceRequestList();

    public static User loggedInUser;
    public static Service serviceToUpdate;
    public static ServiceProviderUser selectedBranch;
    public static ServiceRequest currentRequest;
}
