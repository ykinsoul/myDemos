package com.example.servicenovigrad;

import java.util.ArrayList;

public class ServiceRequestList {
    private final ArrayList<ServiceRequest> requests = new ArrayList<>();

    public void request(ServiceRequest serviceRequest) {
        requests.add(serviceRequest);
    }

    public ArrayList<ServiceRequest> getRequests() {
        return requests;
    }
}
