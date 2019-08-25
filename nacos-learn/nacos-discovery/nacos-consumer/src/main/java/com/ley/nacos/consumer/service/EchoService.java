package com.ley.nacos.consumer.service;

import com.ley.nacos.consumer.config.FeignConfig;
import com.ley.nacos.consumer.service.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "nacos-provider", fallback = EchoServiceFallback.class, configuration = FeignConfig.class)
public interface EchoService {

    /**
     * echo
     *
     * @param message
     * @return
     **/
    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}
