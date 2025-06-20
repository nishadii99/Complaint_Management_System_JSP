package lk.ijse.controller;

import lk.ijse.dao.ComplaintDAO;
import lk.ijse.model.Complaint;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/emp-complaint")
public class EmployeeComplaintServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/signin.jsp");
            return;
        }

        String userId = (String) request.getSession().getAttribute("user_id");
        System.out.println("DEBUG - userId from session: " + userId);

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        ComplaintDAO complaintDAO = new ComplaintDAO(ds);

        try {
            List<Complaint> complaints = complaintDAO.getComplaintsByUserId(userId);
            request.setAttribute("complaintList", complaints);
            request.getRequestDispatcher("/jsp/employeedashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load complaints");
            request.getRequestDispatcher("/jsp/employeedashboard.jsp").forward(request, response);
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/signin.jsp");
            return;
        }

        String userId = (String) session.getAttribute("user_id");
        String action = request.getParameter("action");
        String complaintId = request.getParameter("complaint_id");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/emp-complaint?error=Invalid+action");
            return;
        }

        DataSource ds = (DataSource) getServletContext().getAttribute("ds");
        ComplaintDAO complaintDAO = new ComplaintDAO(ds);

        try {
            boolean result = false;

            if (action.equalsIgnoreCase("insert")) {
                Complaint newComplaint = new Complaint();
                newComplaint.setComplaintId("CMP-" + UUID.randomUUID().toString().substring(0, 8));
                newComplaint.setUserId(userId);
                newComplaint.setTitle(request.getParameter("title"));
                newComplaint.setDescription(request.getParameter("description"));
                result = complaintDAO.saveComplaint(newComplaint);

            } else if (action.equalsIgnoreCase("update")) {
                Complaint updateComplaint = new Complaint();
                updateComplaint.setComplaintId(complaintId);
                updateComplaint.setTitle(request.getParameter("title"));
                updateComplaint.setDescription(request.getParameter("description"));
                result = complaintDAO.updateComplaint(updateComplaint);

            } else if (action.equalsIgnoreCase("delete")) {
                result = complaintDAO.deleteComplaint(complaintId);

            } else {
                response.sendRedirect(request.getContextPath() + "/emp-complaint?error=Invalid+action+type");
                return;
            }

            if (result) {
                response.sendRedirect(request.getContextPath() + "/emp-complaint?message=Operation+successful");
            } else {
                response.sendRedirect(request.getContextPath() + "/emp-complaint?error=Operation+failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/emp-complaint?error=Server+error");
        }
    }

}
