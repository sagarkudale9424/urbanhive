package com.urbanhive.servlet;

import com.urbanhive.dao.ComplaintDao;
import com.urbanhive.model.User;
import com.urbanhive.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/complaints")
public class ComplaintServlet extends HttpServlet {
    private final ComplaintDao complaintDao = new ComplaintDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null || !AuthUtil.hasRole(user, "RESIDENT")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (title == null || title.isBlank() || description == null || description.isBlank()) {
            request.getSession().setAttribute("flash", "Title and description are required.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            complaintDao.createComplaint(user.getId(), title, description);
            request.getSession().setAttribute("flash", "Complaint submitted.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (SQLException ex) {
            throw new ServletException("Unable to submit complaint", ex);
        }
    }
}
