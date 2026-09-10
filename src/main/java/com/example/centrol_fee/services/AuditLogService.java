package com.example.centrol_fee.services;

import org.springframework.stereotype.Service;
import com.example.centrol_fee.models.AuditLog;
import com.example.centrol_fee.mappers.AuditLogMapper;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogMapper auditLogMapper;

    public AuditLogService(AuditLogMapper auditLogMapper) {
        this.auditLogMapper = auditLogMapper;
    }

    public void log(Long userId, String username, String action, String entityName,
                     String entityId, String description, String ipAddress) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setUsername(username);
        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);
        auditLog.setDescription(description);
        auditLog.setIpAddress(ipAddress);
        auditLogMapper.insert(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogMapper.findAll();
    }

    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogMapper.findByUserId(userId);
    }

    public List<AuditLog> getLogsByEntity(String entityName, String entityId) {
        return auditLogMapper.findByEntity(entityName, entityId);
    }
}