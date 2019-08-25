package com.ley.nacos.consumer.controller;

import com.ley.nacos.consumer.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuenyuan
 **/
@RestController
public class NacosSentinelController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 使用RestTemplate访问服务提供者
     **/
    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String rest(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/echo/" + str,
                String.class);
    }

    /**
     * 使用Feign访问服务提供者
     **/
    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feign(@PathVariable String str) {
        return echoService.echo(str);
    }

    @RequestMapping(value = "/services/{service}", method = RequestMethod.GET)
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public Object services() {
        return discoveryClient.getServices();
    }
}
