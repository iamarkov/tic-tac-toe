package com.scentbird.common.servicediscovery;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringCloudServiceDiscovery implements ServiceDiscovery {

    private final DiscoveryClient discoveryClient;

    public SpringCloudServiceDiscovery(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<ServiceInstance> discover(String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }

}
