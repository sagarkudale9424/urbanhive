package com.urbanhive.dao;

import com.urbanhive.model.VisitorLog;
import com.urbanhive.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorLogDao {
    public List<VisitorLog> findRecent() throws SQLException {
        List<VisitorLog> logs = new ArrayList<>();
        String sql = "SELECT id, resident_id, visitor_name, purpose, entry_time, exit_time, approved_by_guard FROM visitor_logs ORDER BY entry_time DESC";

        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                VisitorLog log = new VisitorLog();
                log.setId(resultSet.getInt("id"));
                int residentId = resultSet.getInt("resident_id");
                log.setResidentId(resultSet.wasNull() ? null : residentId);
                log.setVisitorName(resultSet.getString("visitor_name"));
                log.setPurpose(resultSet.getString("purpose"));
                log.setEntryTime(resultSet.getString("entry_time"));
                log.setExitTime(resultSet.getString("exit_time"));
                int guardId = resultSet.getInt("approved_by_guard");
                log.setApprovedByGuard(resultSet.wasNull() ? null : guardId);
                logs.add(log);
            }
        }

        return logs;
    }

    public void createVisitorEntry(Integer residentId, String visitorName, String purpose, int guardId) throws SQLException {
        String sql = "INSERT INTO visitor_logs (resident_id, visitor_name, purpose, approved_by_guard) VALUES (?, ?, ?, ?)";
        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (residentId == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, residentId);
            }
            statement.setString(2, visitorName);
            statement.setString(3, purpose);
            statement.setInt(4, guardId);
            statement.executeUpdate();
        }
    }
}
