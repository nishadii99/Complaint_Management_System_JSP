package lk.ijse.controller;


import lk.ijse.dao.UserDAO;
import lk.ijse.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");


        ServletContext context = getServletContext();
        javax.sql.DataSource ds = (javax.sql.DataSource) context.getAttribute("ds");

        UserDAO userDAO = new UserDAO(ds);
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            HttpSession session = req.getSession();


            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("username", user.getName());

            if ("ADMIN".equalsIgnoreCase(user.getJobRole())) {
                resp.sendRedirect(req.getContextPath() + "/jsp/admindashboard.jsp");

            } else {
                resp.sendRedirect(req.getContextPath() + "/jsp/employeedashboard.jsp");
            }
        } else {
            req.setAttribute("error", "Invalid username or password");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/signin.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
