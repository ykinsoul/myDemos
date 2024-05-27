package com.example.servicenovigrad;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void userTest() {
        String username = "Loki the great";
        String password = "password";

        User me = new User(username, password);
        assertSame("User creation works", me.getUsername(), username);

    }

    @Test
    public void userTest2() {
        String username = "Loki the great";
        String password = "password";
        User me = new User(username, password);
        UserList list = new UserList();
        list.addUser(me);
        assertTrue("Adding user works fine", list.userExist(me));

    }

    @Test
    public void userTest3() {
        String username = "Loki the great";

        String password = "password";
        User me = new User(username, password);

        assertTrue("user validation works", me.equals(new User("Loki the great", "password")));

    }

    @Test
    public void userListTest() {
        String username = "Loki the great";
        String password = "password";
        User me = new User(username, password);
        UserList list = new UserList();
        list.addUser(me);
        assertTrue("user validation works", list.userExist(new User("Loki the great", "password")));
    }

    @Test
    public void userListTest2() {
        String username = "Loki the great";
        String password = "password";
        User me = new User(username, password);
        UserList list = new UserList();
        list.addUser(me);
        assertTrue("user validation works", list.userValidated(new User("Loki the great", "password")));
    }

    @Test
    public void userListTest3() {
        User usr1 = new User("Loki the great", "password");
        User usr2 = new User("Thor the great", "password2");
        User usr3 = new User("great", "password");
        UserList list = new UserList();
        list.addUser(usr1);
        list.addUser(usr3);
        list.addUser(usr2);
        assertTrue("user validation works", list.userValidated(new User("Loki the great", "password")));
        assertTrue("user validation works", list.userValidated(new User("Thor the great", "password2")));
        assertFalse("user validation works", list.userValidated(new User("random", "password")));
    }

    @Test
    public void employeeTest1() {
        ServiceProviderUser user1 = new ServiceProviderUser("username", "password");
        ServiceProviderUser user2 = new ServiceProviderUser("username", "password");
        user2.setCompleted();
        assertFalse("Profile is incomplete", user1.getCompleted());
        assertTrue("Profile is set to complete", user2.getCompleted());
    }

    @Test
    public void employeeTest2() {
        ServiceProviderUser user1 = new ServiceProviderUser("username", "password");
        ServiceProviderUser user2 = new ServiceProviderUser("username", "password");
        Service service = new Service("service", 60);
        user2.addService(service);
        assertTrue(user1.isServiceEmpty());
        assertFalse(user2.isServiceEmpty());
    }

    @Test
    public void dev4Test1() {
        CustomerUser user = new CustomerUser("name", "password");
        assertTrue(user.getRequests().isEmpty());
    }

    @Test
    public void dev4Test2() {
        ServiceRequestList list = new ServiceRequestList();
        ArrayList<String> list2 = new ArrayList<>();
        ServiceRequest request = new ServiceRequest("name", "username", 8.00f, list2, "service");
        list.request(request);
        assertFalse(list.getRequests().isEmpty());

    }

    @Test
    public void dev4Test3() {
        ServiceProviderUser user2 = new ServiceProviderUser("username", "password");
        assertTrue(user2.getRequests().isEmpty());

    }

    @Test
    public void dev4Test4() {
        CustomerUser user = new CustomerUser("name", "password");
        assertTrue(user.searchThroughAddress("address") == 0);
    }

    @Test
    public void dev4Test6() {
        ServiceRequestList list = new ServiceRequestList();
        ArrayList<String> list2 = new ArrayList<>();
        ServiceRequest request = new ServiceRequest("name", "username", 8.00f, list2, "service");
        assertFalse(request.getStatus() == Status.ACCEPTED);
    }

    @Test
    public void dev4Test7() {
        ArrayList<String> list2 = new ArrayList<>();
        ServiceRequest request = new ServiceRequest("name", "username", 8.00f, list2, "service");
        assertFalse(request.getReviewed());
    }

    @Test
    public void dev4Test8() {
        CustomerUser user = new CustomerUser("name", "password");
        assertTrue(user.getCurrentViewedBranches().getList().isEmpty());
    }

    @Test
    public void dev4Test9() {
        ArrayList<String> list2 = new ArrayList<>();
        ServiceRequest request = new ServiceRequest("name", "username", 8.00f, list2, "service");
        assertTrue(request.getStatus() == Status.PENDING);

    }

    @Test
    public void dev4Test10() {
        ServiceRequestList list = new ServiceRequestList();
        ArrayList<String> list2 = new ArrayList<>();
        ServiceRequest request = new ServiceRequest("name", "username", 8.00f, list2, "service");
        request.setReviewed();
        assertTrue(request.getReviewed());
    }
}
