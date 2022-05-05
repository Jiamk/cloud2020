package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author admin
 * date 2022/4/27 - 3:01
 * description
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    public PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    };
    public Payment getPaymentById(Long id){
        return paymentDao.getPaymentById(id);
    };
}
