package com.ley.nacos.provider.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.nacos.client.NacosPropertySource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RefreshScope
class EchoController {

    @Value("${server.port:8080}")
    private Integer port;

    @Value("${user.age:10}")
    private Integer userAge;

    @Value("${user.name:default}")
    private String userName;

    @Resource
    private ConfigurableEnvironment environment;

    @GetMapping(value = "/hi")
    public String sayHi() {
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        while (iterator.hasNext()) {
            PropertySource<?> propertySource = iterator.next();
            if (propertySource instanceof CompositePropertySource) {
                CompositePropertySource compositePropertySource = (CompositePropertySource) propertySource;
                Set<PropertySource<?>> propertySourceList=Sets.newLinkedHashSet(compositePropertySource.getPropertySources());
                CompositePropertySource compositePropertySource1= (CompositePropertySource) propertySourceList.stream().filter(n-> n.getName().contains("NACOS")).collect(Collectors.toList()).get(0);
                Collection<PropertySource<?>> nacosPropertySources=compositePropertySource1.getPropertySources();
                nacosPropertySources.forEach(n->{
                    NacosPropertySource nacosPropertySource= (NacosPropertySource) n;
                    System.out.println(nacosPropertySource.getDataId()+":"+nacosPropertySource.getGroup()+":"
                    +nacosPropertySource.getName()+":"+nacosPropertySource.isRefreshable()+":"+ Arrays.toString(nacosPropertySource.getPropertyNames()));
                });
            }
        }
        return "年龄是: " + userAge + ";" + "姓名是: " + userName;
    }

    @GetMapping(value = "/echo/{message}")
    public String echo(@PathVariable String message) {
        return "Hello Nacos Discovery " + message + " i am from port " + port;
    }


}