package com.urbanhive.dao;

import com.urbanhive.model.Notice;
import com.urbanhive.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDao {
    public List<Notice> findAll() throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = "SELECT id, title, content, posted_by, posted_at FROM notices ORDER BY posted_at DESC";

        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Notice notice = new Notice();
                notice.setId(resultSet.getInt("id"));
                notice.setTitle(resultSet.getString("title"));
                notice.setContent(resultSet.getString("content"));
                notice.setPostedBy(resultSet.getInt("posted_by"));
                notice.setPostedAt(resultSet.getString("posted_at"));
                notices.add(notice);
            }
        }

        return notices;
    }

    public void createNotice(String title, String content, int postedBy) throws SQLException {
        String sql = "INSERT INTO notices (title, content, posted_by) VALUES (?, ?, ?)";
        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, postedBy);
            statement.executeUpdate();
        }
    }
}
