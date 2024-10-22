package com.scentbird.common.loadbalancing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class RandomServerLoadBalancer implements GameServerLoadBalancer {

    @Override
    //normally we should consider a lot of things and implement, either ourselves of using a dedicated tool,
    //an actual load balancing algorithm for client-side load-balancing or choose servers-side load balancer instead
    public ServiceInstance choose(List<ServiceInstance> serviceInstances) {
        Random random = new Random();
        return serviceInstances.get(random.nextInt(serviceInstances.size()));
    }

}
