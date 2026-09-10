package com.example.centrol_fee.services;

import org.springframework.stereotype.Service;
import com.example.centrol_fee.models.Fee;
import com.example.centrol_fee.mappers.FeeMapper;
import com.example.centrol_fee.config.AuditLoggable; 

import java.util.List;
import java.util.Optional;
@Service
public class FeeService {
    private final FeeMapper feeMapper;
    public FeeService(FeeMapper feeMapper) {
        this.feeMapper = feeMapper;
    }
    @AuditLoggable(action = "CREATE", entityName = "Fee", description = "ສ້າງລາຍການຄ່າທຳນຽມໃໝ່")
    public Fee createFee(Fee fee) {
    
        if (feeMapper.existsByServiceCode(fee.getServiceCode())) {
            throw new RuntimeException("ລະຫັດບໍລິການນີ້ຖືກນຳໃຊ້ແລ້ວ!");
        }
        feeMapper.insert(fee);
        return fee;
    }

    public List<Fee> getAllFees() {
        return feeMapper.findAll();
    }

    public Optional<Fee> getFeeById(Long id) {
        return feeMapper.findByIdOptional(id);
    }
    @AuditLoggable(action = "UPDATE", entityName = "Fee", description = "ອັບເດດຂໍ້ມູນຄ່າທຳນຽມ")
    public Fee updateFee(Long id, Fee feeDetails) {
        Fee fee = feeMapper.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("ບໍ່ພົບຂໍ້ມູນ Fee ID: " + id));

        fee.setType(feeDetails.getType());
        fee.setFeeAmount(feeDetails.getFeeAmount());
        fee.setMinAmount(feeDetails.getMinAmount());
        fee.setMaxAmount(feeDetails.getMaxAmount());
        fee.setStatus(feeDetails.getStatus());
        fee.setServiceName(feeDetails.getServiceName());
        fee.setServiceCode(feeDetails.getServiceCode());
        fee.setRemark(feeDetails.getRemark());
        fee.setChannel(feeDetails.getChannel());

        if (!fee.getServiceCode().equals(feeDetails.getServiceCode())) {
            if (feeMapper.existsByServiceCode(feeDetails.getServiceCode())) {
                throw new RuntimeException("ລະຫັດບໍລິການນີ້ຖືກນຳໃຊ້ແລ້ວ!");
            }
            fee.setServiceCode(feeDetails.getServiceCode());
        }

        feeMapper.update(fee);
        return fee;
    }

}
