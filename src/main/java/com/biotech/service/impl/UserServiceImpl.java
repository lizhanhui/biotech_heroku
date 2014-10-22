package com.biotech.service.impl;

import com.biotech.model.User;
import com.biotech.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Produces("application/json")
public class UserServiceImpl implements UserService {

    @GET
    @Path("/user/{id}")
    @Override
    public User get(@PathParam("id")long id) {
        User user = new User();
        user.setId(id);
        user.setName("Test-Name");
        user.setEmail("user@company.com");
        return user;
    }


    @GET
    @Path("/users/topN/{n}")
    @Override
    public List<User> topN(@PathParam("n") int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Top N directly implies N > 0");
        }

        List<User> userList = new ArrayList<User>(n);
        User user = null;
        for (int i = 0; i < n; i++) {
            user = new User();
            user.setId(i);
            user.setName("User " + i);
            user.setEmail("user" + i + "@company.com");
            userList.add(user);
        }

        return userList;
    }
}
