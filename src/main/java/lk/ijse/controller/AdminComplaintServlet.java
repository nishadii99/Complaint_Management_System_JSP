package lk.ijse.controller;

import lk.ijse.dao.ComplaintDAO;
import lk.ijse.model.Complaint;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin-complaint")
public class AdminComplaintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        loadComplaints(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/signin.jsp");
            return;
        }

        String action = req.getParameter("action");
        String complaintId = req.getParameter("complaint_id");

        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        ComplaintDAO complaintDAO = new ComplaintDAO(dataSource);

        try {
            if ("update".equals(action)) {

                String status = req.getParameter("status");
                String remarks = req.getParameter("remarks");

                Complaint complaint = new Complaint();
                complaint.setComplaintId(complaintId);
                complaint.setStatus(status);
                complaint.setRemarks(remarks);

                boolean updated = complaintDAO.updateComplaintStatusAndRemarks(complaint);
                if (updated) {
                    resp.sendRedirect(req.getContextPath() + "/admin-complaint?message=Complaint+updated");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin-complaint?error=Update+failed");
                }
            } else if ("delete".equals(action)) {

                boolean deleted = complaintDAO.deleteComplaintByAdmin(complaintId);
                if (deleted) {
                    resp.sendRedirect(req.getContextPath() + "/admin-complaint?message=Complaint+deleted");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin-complaint?error=Delete+failed");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin-complaint?error=Invalid+action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin-complaint?error=Server+error");
        }
    }

    private void loadComplaints(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/signin.jsp");
            return;
        }
        DataSource dataSource = (DataSource) getServletContext().getAttribute("ds");
        try {
            ComplaintDAO complaintDAO = new ComplaintDAO(dataSource);
            List<Complaint> complaintList = complaintDAO.getAllComplaints();
            req.setAttribute("complaintList", complaintList);
            req.getRequestDispatcher("/jsp/admindashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to load complaints.");
            req.getRequestDispatcher("/jsp/admindashboard.jsp").forward(req, resp);
        }
    }
}