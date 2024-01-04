package com.example.votingapp.DAO;

import com.example.votingapp.model.User;
import java.util.List;

public interface UserDAO {
    void addUser(User user);

    User findUser(int id);
    List<User> getAllUsers();
    public boolean verifyLogin(String username, String password);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void updateUser(User user);
    void deleteUser(int id);
}