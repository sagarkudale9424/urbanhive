package com.urbanhive.dao;

import com.urbanhive.model.User;
import com.urbanhive.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public User findByCredentials(String email, String password) throws SQLException {
        String sql = "SELECT id, full_name, email, password, role, flat_id FROM users WHERE email = ? AND password = ?";
        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFullName(resultSet.getString("full_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    int flatId = resultSet.getInt("flat_id");
                    user.setFlatId(resultSet.wasNull() ? null : flatId);
                    return user;
                }
            }
        }
        return null;
    }
}
