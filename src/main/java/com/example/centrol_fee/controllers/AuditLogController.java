package com.example.centrol_fee.controllers;

import com.example.centrol_fee.models.AuditLog;
import com.example.centrol_fee.services.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
    }

    @GetMapping("/entity")
    public ResponseEntity<List<AuditLog>> getLogsByEntity(
            @RequestParam String entityName, @RequestParam String entityId) {
        return ResponseEntity.ok(auditLogService.getLogsByEntity(entityName, entityId));
    }
}