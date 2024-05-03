package com.example.demo.repository;

import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.util.List;

@Repository
public class UserRepo implements UserRepository {

    private static final String INSERT_USER_QUERY = "INSERT INTO users(name, password, email) VALUES (?, ?, ?)";
    private static final String GET_USER_QUERY_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE users SET name=? WHERE id=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
    private static final String GET_USER_QUERY = "SELECT * FROM users";
    private static final String DELETE_ALL = "DELETE FROM users";
    private static final String GET_USER_BY_NAME_AND_PASSWORD = "SELECT * FROM users WHERE name = :name AND password = :password";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public UserRepo(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //saving the user
    public User saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY, user.getName(),
                user.getPassword(), user.getEmail());
        return user;
    }


    //finding user by id
    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_QUERY_BY_ID, (rs, rowNum) -> {
            User user = new User(rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"));
            user.setId(Integer.valueOf(rs.getString("id")));
            return user;
        }, id);
    }

    //update user
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, user.getName(),
                user.getId());
        return user;
    }

    //delete
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
        return "User got delete with id" + id;
    }

    //delete all
    public String deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
        return "all users in this table is deleted";
    }

    //creates a list
    public List<User> allUsers() {
        return jdbcTemplate.query(GET_USER_QUERY, (rs, rowNum) -> {
            User user = new User(rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"));
            user.setId(Integer.valueOf(rs.getString("id")));
            return user;
        });
    }

    //resets the table
    public String resetUsersTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL UNIQUE)");
        return "all users are deleted and the table is reset";
    }

    // Finds the user that matches the given name and password
    public User getByNameAndPassword(String name, String password) {
        String sql = "SELECT * FROM users WHERE name = :name AND password = :password";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("password", password);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                User user = new User(rs.getString("name"), rs.getString("password"), rs.getString("email"));
                user.setId(rs.getInt("id"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no user found
        }
    }

}
