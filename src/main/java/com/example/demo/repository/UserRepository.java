package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.List;

public interface UserRepository {
    User saveUser(User user);
    User updateUser(User user);
    User getById(int id);
    String deleteById(int id);
    List<User> allUsers();
    String deleteAll();
    String resetUsersTable();
}
