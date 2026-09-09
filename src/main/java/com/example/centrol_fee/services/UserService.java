package com.example.centrol_fee.services;

import org.springframework.stereotype.Service;
import com.example.centrol_fee.models.User;
import com.example.centrol_fee.mappers.UserMapper;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

         private final UserMapper userMapper;

         public UserService(UserMapper userMapper) {
                  this.userMapper = userMapper;
         }

         public User createUser(User user) {
                  if (userMapper.existsByEmail(user.getEmail())) {
                           throw new RuntimeException("Email ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                  }
                  if (userMapper.existsByTel(user.getTel())) {
                           throw new RuntimeException("ເບີໂທລະສັບ ນີ້ຖືກນຳໃຊ້ແລ້ວ!");
                  }
                  userMapper.insert(user);
                  return user;
         }

         public List<User> getAllUsers() {
                  return userMapper.findAll();
         }

         public Optional<User> getUserById(Long id) {
                  return userMapper.findById(id);
         }

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

         public void deleteUser(Long id) {
                  userMapper.deleteById(id);
         }
}