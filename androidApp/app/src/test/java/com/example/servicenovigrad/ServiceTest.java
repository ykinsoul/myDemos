package com.example.servicenovigrad;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    @Test
    public void serviceTest() {
        Service service = new Service("Service1",2000);
        service.addDocument("doc1");
        service.addDocument("doc2");
        service.addInfo("info1");
        ArrayList<String> docs = new ArrayList<>();
        docs.add("doc1");
        docs.add("doc2");
        assertEquals(docs, service.getDocs());
        assertEquals("Service1", service.getName());
        assertEquals(2000.0, service.getPrice(),0);
    }

    @Test
    public void serviceListTest() {
        Service service = new Service("Service1",2000);
        service.addDocument("doc1");
        service.addDocument("doc2");
        service.addInfo("info1");
        Service service2 = new Service("Service2",2000);
        service.addDocument("doc1s");
        service.addDocument("doc2s");
        service.addInfo("info1s");
        ServiceList list = new ServiceList();
        list.add(service);
        list.add(service2);
        assertEquals(list.getSize(),2);
        list.remove(service);
        assertEquals(list.get(0),service2);
        list.add(service2);
        assertEquals(list.get(1).getName(),"Service2");
        list.clear();
        assertEquals(list.getSize(),0);
    }
}
