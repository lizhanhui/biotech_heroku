package com.biotech.service;

import com.biotech.model.User;

import java.util.List;

public interface UserService {

    public User get(long id);

    public List<User> topN(int n);
}
