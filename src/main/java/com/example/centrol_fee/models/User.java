package com.example.centrol_fee.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ຊື່ຫ້າມຫວ່າງ")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "gender", length = 10)
    private String gender;

    @NotBlank(message = "ກະລຸນາປ້ອນເບີໂທລະສັບ")
    @Size(message="ເບີໂທລະສັບຕ້ອງບໍ່ເກີນ 11 ຕົວ ແລະ ບໍ່ຫຼຸດ 10 ຕົວ", min = 10, max = 11)
    @Column(name = "tel", length = 11, unique = true)
    private String tel;

    @Email(message = "ຮູບແບບອີເມວບໍ່ຖືກຕ້ອງ")
    @Column(name = "email", unique = true)
    private String email;


    @NotBlank(message = "ກະລຸນາກໍານົດລະຫັດຜ່ານ")
    @Size(min = 8, message = "ລະຫັດຜ່ານຕ້ອງມີຢ່າງນ້ອຍ 8 ຕົວອັກສອນ")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "ລະຫັດຜ່ານຕ້ອງມີ: ຕົວພິມໃຫຍ່ (A-Z), ຕົວພິມນ້ອຍ (a-z), ຕົວເລກ (0-9) ແລະ ສັນຍາລັກ (@#$%^&+=!) ຢ່າງນ້ອຍຢ່າງລະ 1 ຕົວ"
    )
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", length = 20)
    private String role; 

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}