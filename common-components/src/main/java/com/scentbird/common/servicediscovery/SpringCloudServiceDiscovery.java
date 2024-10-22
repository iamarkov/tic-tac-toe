package com.scentbird.common.servicediscovery;

import com.scentbird.common.exceptions.NoInstancesFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpringCloudServiceDiscovery implements ServiceDiscovery {

    public static final String ERROR_MESSAGE_TEMPLATE = "No instances found for service %s";
    private final DiscoveryClient discoveryClient;

    public SpringCloudServiceDiscovery(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    @Retryable(maxAttempts = 10, backoff = @Backoff(5000))
    public List<ServiceInstance> discover(String serviceName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.isEmpty()) {
            throw new NoInstancesFoundException(String.format(ERROR_MESSAGE_TEMPLATE, serviceName));
        }
        return instances;
    }

    @Recover
    public void printError() {
        log.error("Could not find game server to connect to!");
    }

}
