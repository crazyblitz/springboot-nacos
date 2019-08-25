package com.ley.nacos.consumer.service.fallback;

import com.ley.nacos.consumer.service.EchoService;
import org.springframework.stereotype.Component;

@Component
public class EchoServiceFallback implements EchoService {

    @Override
    public String echo(String str) {
        return "echo error!";
    }
}
