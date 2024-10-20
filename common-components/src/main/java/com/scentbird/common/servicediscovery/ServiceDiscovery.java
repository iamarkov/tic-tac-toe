package com.scentbird.common.servicediscovery;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface ServiceDiscovery {
    List<ServiceInstance> discover(String serviceName);
}
