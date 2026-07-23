package com.urbanhive.util;

import com.urbanhive.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    private AuthUtil() {
    }

    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("loggedInUser");
    }

    public static boolean hasRole(User user, String role) {
        return user != null && role.equalsIgnoreCase(user.getRole());
    }
}
