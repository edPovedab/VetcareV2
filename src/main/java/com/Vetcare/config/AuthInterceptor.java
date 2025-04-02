package com.Vetcare.config;

import com.Vetcare.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        
        // Permitir acceso a páginas públicas
        if (path.startsWith("/login") || path.startsWith("/registro") || 
            path.startsWith("/css") || path.startsWith("/js") || 
            path.startsWith("/img") || path.equals("/")) {
            return true;
}
        
        // Verificar acceso a rutas de administrador
        if (path.startsWith("/admin")) {
            if (!SessionUtils.isAdmin(request)) {
                response.sendRedirect("/login");
                return false;
            }
        }
        
        // Verificar si el usuario está logueado para otras rutas protegidas
        if (!SessionUtils.isLoggedIn(request)) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}
