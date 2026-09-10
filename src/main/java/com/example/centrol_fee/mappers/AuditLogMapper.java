package com.example.centrol_fee.mappers;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.example.centrol_fee.models.AuditLog;

@Mapper
public interface AuditLogMapper {

    @Insert("INSERT INTO audit_logs(user_id, username, action, entity_name, entity_id, description, ip_address, created_at) " +
            "VALUES(#{userId}, #{username}, #{action}, #{entityName}, #{entityId}, #{description}, #{ipAddress}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(AuditLog log);

    @Select("SELECT id, user_id AS userId, username, action, entity_name AS entityName, entity_id AS entityId, " +
            "description, ip_address AS ipAddress, created_at AS createdAt " +
            "FROM audit_logs ORDER BY created_at DESC")
    List<AuditLog> findAll();

    @Select("SELECT id, user_id AS userId, username, action, entity_name AS entityName, entity_id AS entityId, " +
            "description, ip_address AS ipAddress, created_at AS createdAt " +
            "FROM audit_logs WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<AuditLog> findByUserId(@Param("userId") Long userId);

    @Select("SELECT id, user_id AS userId, username, action, entity_name AS entityName, entity_id AS entityId, " +
            "description, ip_address AS ipAddress, created_at AS createdAt " +
            "FROM audit_logs WHERE entity_name = #{entityName} AND entity_id = #{entityId} ORDER BY created_at DESC")
    List<AuditLog> findByEntity(@Param("entityName") String entityName, @Param("entityId") String entityId);
}