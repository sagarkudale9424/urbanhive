package com.urbanhive.dao;

import com.urbanhive.model.Complaint;
import com.urbanhive.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDao {
    public List<Complaint> findByResidentId(int residentId) throws SQLException {
        String sql = "SELECT id, resident_id, title, description, status, assigned_to, created_at FROM complaints WHERE resident_id = ? ORDER BY created_at DESC";
        return findComplaints(sql, residentId);
    }

    public List<Complaint> findAll() throws SQLException {
        String sql = "SELECT id, resident_id, title, description, status, assigned_to, created_at FROM complaints ORDER BY created_at DESC";
        return findComplaints(sql, null);
    }

    public void createComplaint(int residentId, String title, String description) throws SQLException {
        String sql = "INSERT INTO complaints (resident_id, title, description, status, assigned_to) VALUES (?, ?, ?, 'OPEN', 'Unassigned')";
        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, residentId);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.executeUpdate();
        }
    }

    private List<Complaint> findComplaints(String sql, Integer residentId) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (residentId != null) {
                statement.setInt(1, residentId);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Complaint complaint = new Complaint();
                    complaint.setId(resultSet.getInt("id"));
                    complaint.setResidentId(resultSet.getInt("resident_id"));
                    complaint.setTitle(resultSet.getString("title"));
                    complaint.setDescription(resultSet.getString("description"));
                    complaint.setStatus(resultSet.getString("status"));
                    complaint.setAssignedTo(resultSet.getString("assigned_to"));
                    complaint.setCreatedAt(resultSet.getString("created_at"));
                    complaints.add(complaint);
                }
            }
        }
        return complaints;
    }
}
