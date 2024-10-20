package com.scentbird.common.loadbalancing;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface GameServerLoadBalancer {
    ServiceInstance choose(List<ServiceInstance> serviceInstances);
}
