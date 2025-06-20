package lk.ijse.dao;

import lk.ijse.model.Complaint;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    private final DataSource dataSource;

    public ComplaintDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public boolean saveComplaint(Complaint complaint) throws Exception {
        String sql = "INSERT INTO complaints (complaint_id, user_id, title, description, status, remarks) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, complaint.getComplaintId());
            stmt.setString(2, complaint.getUserId());
            stmt.setString(3, complaint.getTitle());
            stmt.setString(4, complaint.getDescription());
            stmt.setString(5, "PENDING");
            stmt.setString(6, null);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Complaint> getComplaintsByUserId(String userId) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE user_id = ?";

        System.out.println("DEBUG - Fetching complaints for userId: " + userId);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(rs.getString("complaint_id"));
                complaint.setUserId(rs.getString("user_id"));
                complaint.setTitle(rs.getString("title"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaint.setRemarks(rs.getString("remarks"));
                complaint.setCreatedAt(rs.getString("created_at"));
                complaints.add(complaint);
            }
        }

        return complaints;
    }
    public boolean updateComplaint(Complaint complaint) throws Exception {
        String checkSql = "SELECT status FROM complaints WHERE complaint_id = ?";
        String updateSql = "UPDATE complaints SET title = ?, description = ?, status = ?, remarks = ? WHERE complaint_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, complaint.getComplaintId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("status");
                if ("RESOLVED".equalsIgnoreCase(currentStatus) || "IN_PROGRESS".equalsIgnoreCase(currentStatus)) {
                    return false;
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, complaint.getTitle());
                updateStmt.setString(2, complaint.getDescription());
                updateStmt.setString(3, "PENDING");
                updateStmt.setString(4, complaint.getRemarks());
                updateStmt.setString(5, complaint.getComplaintId());

                return updateStmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteComplaint(String complaintId) throws Exception {
        String checkSql = "SELECT status FROM complaints WHERE complaint_id = ?";
        String deleteSql = "DELETE FROM complaints WHERE complaint_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, complaintId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("status");
                if ("RESOLVED".equalsIgnoreCase(currentStatus) || "IN_PROGRESS".equalsIgnoreCase(currentStatus)) {
                    return false;
                }
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, complaintId);
                return deleteStmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public List<Complaint> getAllComplaints() throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints ORDER BY created_at DESC";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(rs.getString("complaint_id"));
                complaint.setUserId(rs.getString("user_id"));
                complaint.setTitle(rs.getString("title"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaint.setRemarks(rs.getString("remarks"));
                complaint.setCreatedAt(rs.getString("created_at"));
                complaints.add(complaint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return complaints;
    }
    public boolean updateComplaintStatusAndRemarks(Complaint complaint) throws SQLException {
        String sql = "UPDATE complaints SET status = ?, remarks = ? WHERE complaint_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaint.getStatus());
            stmt.setString(2, complaint.getRemarks());
            stmt.setString(3, complaint.getComplaintId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteComplaintByAdmin(String complaintId) throws SQLException {
        String sql = "DELETE FROM complaints WHERE complaint_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaintId);
            return stmt.executeUpdate() > 0;
        }
    }

}
