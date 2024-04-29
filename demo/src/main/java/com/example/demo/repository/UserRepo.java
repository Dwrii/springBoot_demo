package com.example.demo.repository;

import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepo implements UserRepository{

    private static final String INSERT_USER_QUERY = "INSERT INTO USER (id, name, password, email) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_QUERY_BY_ID = "SELECT * FROM USER WHERE ID = ?";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE USER SET name=? WHERE ID=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM USER WHERE ID=?";
    private static final String GET_USER_QUERY = "SELECT * FROM USER";

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                return new User(rs.getString( "name"),
                                rs.getString("password"),
                                rs.getString("email"));
        });
    }

    //update user
    public User updateUser(User user){
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, user.getName(),
                user.getId());
        return user;
    }

    //delete
    public String deleteById(int id){
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
        return"User got delete with id" + id;
    }

    //creates a list
    public List<User> allUsers(){
    return jdbcTemplate.query(GET_USER_QUERY, (rs, rowNum) -> {
         return new User (rs.getString( "name"),
                rs.getString("password"),
                rs.getString("email"));
    });
    }
}
