package lk.ijse.controller;

import lk.ijse.dao.UserDAO;
import lk.ijse.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String name = firstName + " " + lastName;
        String address = req.getParameter("address");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String department = req.getParameter("department");
        String jobRole = req.getParameter("jobRole");

        if (!password.equals(confirmPassword)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/signup.jsp?signup=pass_mismatch");
            return;
        }


        String userId = "U" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        User user = new User(
                userId,
                name,
                address,
                mobile,
                email,
                username,
                password,
                department,
                jobRole
        );


        ServletContext context = getServletContext();
        DataSource ds = (DataSource) context.getAttribute("ds");


        UserDAO userDAO = new UserDAO(ds);

        if (userDAO.isUsernameTaken(username)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/signup.jsp?signup=username_taken");
            return;
        }

        boolean success = userDAO.saveUser(user);

        if (success) {

            resp.sendRedirect(req.getContextPath() + "/jsp/signin.jsp?signup=success");
        } else {

            resp.sendRedirect(req.getContextPath() + "/jsp/signup.jsp?signup=fail");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        DataSource ds = (DataSource) context.getAttribute("ds");
        UserDAO userDAO = new UserDAO(ds);
        List<String> usernames = userDAO.getAllUsernames();

        req.setAttribute("existingUsernames", usernames);
        req.getRequestDispatcher("/jsp/signup.jsp").forward(req, resp);

    }
}
