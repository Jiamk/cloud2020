package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * author admin
 * date 2022/5/1 - 17:51
 * description
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "------PaymentFallbackService fallback paymentInfo_OK, /(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "------PaymentFallbackService fallback paymentInfo_Timeout, /(ㄒoㄒ)/~~";
    }
}
