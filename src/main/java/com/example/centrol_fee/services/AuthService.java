package com.example.centrol_fee.services;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.centrol_fee.mappers.UserMapper;
import com.example.centrol_fee.middleware.Jwt;
import com.example.centrol_fee.models.User;
import com.example.centrol_fee.utils.LoginRequest;
import com.example.centrol_fee.utils.LoginResponse;

@Service
public class AuthService {
         private final PasswordEncoder passwordEncoder;
         private final UserMapper userMapper;

         AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
                  this.userMapper = userMapper;
                  this.passwordEncoder = passwordEncoder;
         }

         private Jwt jwt;

         public LoginResponse login(LoginRequest request) {
                  User user = userMapper.findByEmail(request.getEmail())
                                    .orElseThrow(() -> new RuntimeException("Email ຫຼື Password ບໍ່ຖືກຕ້ອງ!"));

                  if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                           throw new RuntimeException("Email ຫຼື Password ບໍ່ຖືກຕ້ອງ!");
                  }
                  String token = jwt.generateToken(user.getEmail(), user.getRole());
                  return new LoginResponse(token, user.getEmail(), user.getRole());
         }
}
