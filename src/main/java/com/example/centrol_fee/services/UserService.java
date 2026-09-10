package com.example.centrol_fee.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.centrol_fee.models.User;
import com.example.centrol_fee.mappers.UserMapper;
import com.example.centrol_fee.config.AuditLoggable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

         private final UserMapper userMapper;
         private final PasswordEncoder hashPassword;

         public UserService(UserMapper userMapper, PasswordEncoder hashPassword) {
                  this.userMapper = userMapper;
                  this.hashPassword = hashPassword;
         }
         @AuditLoggable(action = "CREATE", entityName = "User", description = "ສ້າງຜູ້ໃຊ້ໃໝ່")
         public User createUser(User user) {
                  if (userMapper.existsByEmail(user.getEmail())) {
                           throw new RuntimeException("Email ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                  }
                  if (userMapper.existsByTel(user.getTel())) {
                           throw new RuntimeException("ເບີໂທລະສັບ ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                  }
                  String hashedPassword = hashPassword.encode(user.getPassword());
                  user.setPassword(hashedPassword);
                  userMapper.insert(user);
                  return user;
         }

         public List<User> getAllUsers() {
                  return userMapper.findAll();
         }

         public Optional<User> getUserById(Long id) {
                  return userMapper.findById(id);
         }
         @AuditLoggable(action = "UPDATE", entityName = "User", description = "ອັບເດດຂໍ້ມູນຜູ້ໃຊ້")
         public User updateUser(Long id, User userDetails) {
                  User user = userMapper.findById(id)
                                    .orElseThrow(() -> new RuntimeException("ບໍ່ພົບຂໍ້ມູນ User ID: " + id));

                  user.setName(userDetails.getName());
                  user.setLastname(userDetails.getLastname());
                  user.setGender(userDetails.getGender());
                  user.setTel(userDetails.getTel());
                  user.setRole(userDetails.getRole());
                  if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
                           if (userMapper.existsByTel(user.getTel())) {
                                    throw new RuntimeException("ເບີໂທລະສັບ ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                           }
                           if (userMapper.existsByEmail(userDetails.getEmail())) {
                                    throw new RuntimeException("Email ໃໝ່ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                           }
                           user.setEmail(userDetails.getEmail());
                  }

                  userMapper.update(user);
                  return user;
         }
         
         @AuditLoggable(action = "DELETE", entityName = "User", description = "ລົບຜູ້ໃຊ້")
         public void deleteUser(Long id) {
                  userMapper.deleteById(id);
         }
}