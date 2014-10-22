package com.biotech.service.impl;

import com.biotech.model.User;
import com.biotech.service.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
}
