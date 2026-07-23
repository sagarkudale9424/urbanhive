package com.urbanhive.servlet;

import com.urbanhive.dao.BillDao;
import com.urbanhive.dao.ComplaintDao;
import com.urbanhive.dao.NoticeDao;
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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final BillDao billDao = new BillDao();
    private final ComplaintDao complaintDao = new ComplaintDao();
    private final NoticeDao noticeDao = new NoticeDao();
    private final VisitorLogDao visitorLogDao = new VisitorLogDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            request.setAttribute("currentUser", user);
            request.setAttribute("notices", noticeDao.findAll());
            request.setAttribute("visitorLogs", visitorLogDao.findRecent());

            if (AuthUtil.hasRole(user, "RESIDENT")) {
                if (user.getFlatId() != null) {
                    request.setAttribute("bills", billDao.findByFlatId(user.getFlatId()));
                }
                request.setAttribute("complaints", complaintDao.findByResidentId(user.getId()));
            } else {
                request.setAttribute("complaints", complaintDao.findAll());
            }

            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (SQLException ex) {
            throw new ServletException("Unable to load dashboard", ex);
        }
    }
}
