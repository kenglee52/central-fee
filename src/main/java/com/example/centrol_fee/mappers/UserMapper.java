package com.example.centrol_fee.mappers;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.centrol_fee.models.User;

@Mapper
public interface UserMapper {
         @Select("SELECT COUNT(*) > 0 FROM users WHERE tel = #{tel}")
         boolean existsByTel(String tel);

         @Select("SELECT COUNT(*) > 0 FROM users WHERE tel = #{email}")
         boolean existsByEmail(String email);

         @Insert("INSERT INTO users(name, lastname, gender, tel, email, password, role, created_at, updated_at) " +
                           "VALUES(#{name}, #{lastname}, #{gender}, #{tel}, #{email}, #{password}, #{role}, NOW(), NOW())")
         @Options(useGeneratedKeys = true, keyProperty = "id")
         void insert(User user);

         @Select("SELECT id, name, lastname, gender, tel, email, password, role, created_at AS createAt, updated_at AS updatedAt FROM users")
         List<User> findAll();

         @Select("SELECT id, name, lastname, gender, tel, email, password, role, created_at AS createAt, updated_at AS updatedAt FROM users WHERE id = #{id}")
         Optional<User> findById(Long id);

         @Update("UPDATE users SET name = #{name}, lastname = #{lastname}, gender = #{gender}, " +
                           "tel = #{tel}, email = #{email}, role = #{role}, updated_at = NOW() WHERE id = #{id}")
         void update(User user);

         @Delete("DELETE FROM users WHERE id = #{id}")
         void deleteById(Long id);
}
