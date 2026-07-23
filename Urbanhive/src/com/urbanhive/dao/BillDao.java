package com.urbanhive.dao;

import com.urbanhive.model.Bill;
import com.urbanhive.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    public List<Bill> findByFlatId(int flatId) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT id, flat_id, bill_month, amount, status, due_date FROM bills WHERE flat_id = ? ORDER BY due_date DESC";

        try (Connection connection = Db.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flatId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = new Bill();
                    bill.setId(resultSet.getInt("id"));
                    bill.setFlatId(resultSet.getInt("flat_id"));
                    bill.setBillMonth(resultSet.getString("bill_month"));
                    bill.setAmount(resultSet.getDouble("amount"));
                    bill.setStatus(resultSet.getString("status"));
                    bill.setDueDate(resultSet.getString("due_date"));
                    bills.add(bill);
                }
            }
        }

        return bills;
    }
}
