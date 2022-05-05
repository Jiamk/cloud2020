package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import rx.internal.schedulers.SchedulerLifecycle;

import java.util.concurrent.TimeUnit;

/**
 * author admin
 * date 2022/4/30 - 2:50
 * description
 */
@Service
public class PaymentService {
    /*正常访问，肯定OK*/
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O哈哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",
            value = "5000")})
    public String paymentInfo_TimeOut(Integer id){
       //int age = 10/0; //计算异常
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+" id: "+id+"\t"+"O(∩_∩)O哈哈哈~"+", 耗时3秒钟";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" 8001系统繁忙或运行报错，请稍后再试,id: "+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    //==================服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器

            ////当在配置时间窗口内达到此数量的失败后，进行短路。10个/10s 注意分母是10s。 默认20个/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //短路多久以后开始尝试是否恢复，默认5s
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            //失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号： "+serialNumber;
    }
    //服务降级方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试， o(╥﹏╥)o  id: "+id;
    }

}
