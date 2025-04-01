package com.Vetcare.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuarioId") != null;
    }
    
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return isLoggedIn(request) && "ADMIN".equalsIgnoreCase((String) session.getAttribute("usuarioRol"));
    }
    
    public static Integer getUsuarioId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Integer) session.getAttribute("usuarioId");
        }
        return null;
    }
}