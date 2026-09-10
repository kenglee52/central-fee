package com.example.centrol_fee.config;

import com.example.centrol_fee.mappers.UserMapper;
import com.example.centrol_fee.models.User;
import com.example.centrol_fee.services.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogService auditLogService;
    private final UserMapper userMapper;

    public AuditAspect(AuditLogService auditLogService, UserMapper userMapper) {
        this.auditLogService = auditLogService;
        this.userMapper = userMapper;
    }

    @AfterReturning(pointcut = "@annotation(auditLoggable)", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, AuditLoggable auditLoggable, Object result) {
        writeLog(joinPoint, auditLoggable, result, auditLoggable.action(), null);
    }

    @AfterThrowing(pointcut = "@annotation(auditLoggable)", throwing = "ex")
    public void logAfterFailure(JoinPoint joinPoint, AuditLoggable auditLoggable, Exception ex) {
        writeLog(joinPoint, auditLoggable, null, auditLoggable.action() + "_FAILED", ex.getMessage());
    }

    private void writeLog(JoinPoint joinPoint, AuditLoggable auditLoggable, Object result,
                           String action, String errorMessage) {

        Long actorId = null;
        String actorName = "SYSTEM";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal())
                && auth.getPrincipal() instanceof String email) {

            Optional<User> userOpt = userMapper.findByEmail(email);
            if (userOpt.isPresent()) {
                User currentUser = userOpt.get();
                actorId = currentUser.getId();
                actorName = currentUser.getName();
            } else {
                actorName = email;
            }
        }

        String entityId = extractEntityId(joinPoint, result);
        String description = errorMessage != null ? "ລົ້ມເຫຼວ: " + errorMessage : auditLoggable.description();
        String ipAddress = getClientIp();

        auditLogService.log(actorId, actorName, action, auditLoggable.entityName(),
                entityId, description, ipAddress);
        
    }

    private String extractEntityId(JoinPoint joinPoint, Object result) {
        if (result != null) {
            try {
                Method getId = result.getClass().getMethod("getId");
                Object idVal = getId.invoke(result);
                if (idVal != null) return String.valueOf(idVal);
            } catch (Exception ignored) { }
        }
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && (args[0] instanceof Long || args[0] instanceof Integer || args[0] instanceof String)) {
            return String.valueOf(args[0]);
        }
        return null;
    }

    private String getClientIp() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return null;
            HttpServletRequest request = attributes.getRequest();

            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            } else {
                ip = ip.split(",")[0].trim();
            }
            return ip;
        } catch (Exception e) {
            return null;
        }
    }
}