package com.scentbird.common.servicediscovery;


import com.scentbird.common.exceptions.NoInstancesFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.client.discovery.DiscoveryClient;


public class SpringCloudServiceDiscoveryTest {

    @Test
    public void discoverTest() {
        //arrange
        DiscoveryClient discoveryClient = Mockito.mock(DiscoveryClient.class);
        SpringCloudServiceDiscovery serviceDiscovery = new SpringCloudServiceDiscovery(discoveryClient);

        //act
        //assert
        Assertions.assertThrows(NoInstancesFoundException.class, () -> serviceDiscovery.discover("sample-service-name"));
    }

}
