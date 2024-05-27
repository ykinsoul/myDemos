package com.example.servicenovigrad;

import java.util.ArrayList;

public class Service {
    private final ArrayList<String> documents = new ArrayList<>();
    private final ArrayList<String> information = new ArrayList<>();
    private String serviceName;
    private float price;

    public Service(String name, float price) {
        serviceName = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void addDocument(String doc) {
        documents.add(doc);
    }

    public void addInfo(String info) {
        information.add(info);
    }

    public ArrayList<String> getInfo() {
        return information;
    }

    public ArrayList<String> getDocs() {
        return documents;
    }

    public String getName() {
        return serviceName;
    }

    public void setName(String name) {
        serviceName = name;
    }

    public void addAllDocs(ArrayList<String> documents) {
        this.documents.addAll(documents);
    }

    public void addAllInfo(ArrayList<String> information) {
        this.information.addAll(information);
    }
}
