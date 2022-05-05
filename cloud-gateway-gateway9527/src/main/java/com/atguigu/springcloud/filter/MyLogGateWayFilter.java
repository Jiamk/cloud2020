package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * author admin
 * date 2022/5/4 - 11:56
 * description
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("*********************come in MyLogGateWayFilter:  "+ new Date());
        //取出请求参数的uname对应的值
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");

        //如果uanme为空，就直接过滤掉，不走路由
        if(uname == null){
            log.info("************* 用户名为Null 非法用户 o(╥﹏╥)o");

            //判断该请求不通过时：给一个回应，返回
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }

        //反之，调用下一个过滤器，也就是放行：在该环节判断通过的exchange放行，交给下一个filter判断
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
