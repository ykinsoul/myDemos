package com.example.servicenovigrad;

import java.util.ArrayList;

public class ServiceList {
    private final ArrayList<Service> services = new ArrayList<>();
    private final ArrayList<String> list = new ArrayList<>();

    public void add(Service service) {
        services.add(service);
        list.add(service.getName());
    }

    public void delete(int i) {
        services.remove(i);
        list.remove(i);
    }

    public void edit(Service newService, int i) {
        if (i < services.size()) {
            services.set(i, newService);
            list.set(i, newService.getName());
        }
    }

    public void remove(Service service) {
        services.remove(service);
        list.remove(service.getName());
    }

    public ArrayList<String> getList() {
        return list;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public int getSize() {
        return services.size();
    }

    public Service get(int i) {
        return services.get(i);
    }

    public int indexAt(Service service) {
        return services.indexOf(service);
    }

    public boolean nameExists(String name) {
        for (Service service : services) {
            if (service.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String[] getArray() {
        return list.toArray(new String[0]);
    }

    public void clear() {
        services.clear();
        list.clear();
    }

    public boolean isEmpty() {
        return services.isEmpty();
    }

}
