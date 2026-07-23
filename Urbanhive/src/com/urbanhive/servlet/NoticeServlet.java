package com.urbanhive.servlet;

import com.urbanhive.dao.NoticeDao;
import com.urbanhive.model.User;
import com.urbanhive.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/notices")
public class NoticeServlet extends HttpServlet {
    private final NoticeDao noticeDao = new NoticeDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = AuthUtil.getLoggedInUser(request);
        if (user == null || !AuthUtil.hasRole(user, "ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            request.getSession().setAttribute("flash", "Notice title and content are required.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            noticeDao.createNotice(title, content, user.getId());
            request.getSession().setAttribute("flash", "Notice posted.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (SQLException ex) {
            throw new ServletException("Unable to post notice", ex);
        }
    }
}
