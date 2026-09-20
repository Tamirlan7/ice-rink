package by.tami.pricingservice.interceptors;

import by.tami.pricingservice.annotations.RequiredRole;
import by.tami.pricingservice.exception.AccessDeniedException;
import by.tami.pricingservice.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    private static final String ROLE_HEADER = "X-User-Role";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequiredRole required = handlerMethod.getMethodAnnotation(RequiredRole.class);
        if (required == null) {
            return true;
        }

        String role = request.getHeader(ROLE_HEADER);
        if (role == null) {
            throw new BadRequestException("Missing " + ROLE_HEADER + " Header");
        }

        if (!Arrays.asList(required.value()).contains(role)) {
            throw new AccessDeniedException("У вас недастоточно прав!");
        }

        return true;
    }
}
