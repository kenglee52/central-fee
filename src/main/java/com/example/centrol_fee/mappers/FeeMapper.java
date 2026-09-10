package com.example.centrol_fee.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;
import jakarta.annotation.Generated;

import com.example.centrol_fee.models.Fee;
@Mapper
public interface  FeeMapper {

    @Select("SELECT COUNT(*) > 0 FROM fees WHERE service_code = #{serviceCode}")
    boolean existsByServiceCode(String serviceCode);

    @Insert("INSERT INTO fees(type, fee_amount, min_amount, max_amount, status, service_name, service_code, remark, channel, created_at, updated_at) " +
            "VALUES(#{type}, #{feeAmount}, #{minAmount}, #{maxAmount}, #{status}, #{serviceName}, #{serviceCode}, #{remark}, #{channel}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Fee fee);

    @Select("SELECT id, type,fee_amount AS feeAmount,min_amount AS minAmount,max_amount AS maxAmount,status,service_name AS serviceName,service_code AS serviceCode,remark,channel,created_at AS createAt,updated_at AS updatedAt FROM fees") 
    List<Fee> findAll(); 

    @Select("SELECT id, type,fee_amount AS feeAmount,min_amount AS minAmount,max_amount AS maxAmount,status,service_name AS serviceName,service_code AS serviceCode,remark,channel,created_at AS createAt,updated_at AS updatedAt FROM fees WHERE id = #{id}")
    Optional<Fee> findByIdOptional(Long id);
    
    @Update("UPDATE fees SET type = #{type}, fee_amount = #{feeAmount}, min_amount = #{minAmount}, max_amount = #{maxAmount}, status = #{status}, service_name = #{serviceName}, service_code = #{serviceCode}, remark = #{remark}, channel = #{channel}, updated_at = NOW() WHERE id = #{id}")
    void update(Fee fee);
}   
