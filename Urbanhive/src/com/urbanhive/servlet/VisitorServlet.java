package com.urbanhive.servlet;

import com.urbanhive.dao.VisitorLogDao;
import com.urbanhive.model.User;
import com.urbanhive.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/visitors")
public class VisitorServlet extends HttpServlet {
    private final VisitorLogDao visitorLogDao = new VisitorLogDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null || !AuthUtil.hasRole(user, "GUARD")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String residentIdValue = request.getParameter("residentId");
        String visitorName = request.getParameter("visitorName");
        String purpose = request.getParameter("purpose");
        if (visitorName == null || visitorName.isBlank() || purpose == null || purpose.isBlank()) {
            request.getSession().setAttribute("flash", "Visitor name and purpose are required.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        Integer residentId = null;
        if (residentIdValue != null && !residentIdValue.isBlank()) {
            residentId = Integer.parseInt(residentIdValue);
        }

        try {
            visitorLogDao.createVisitorEntry(residentId, visitorName, purpose, user.getId());
            request.getSession().setAttribute("flash", "Visitor logged.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (SQLException ex) {
            throw new ServletException("Unable to log visitor", ex);
        }
    }
}
