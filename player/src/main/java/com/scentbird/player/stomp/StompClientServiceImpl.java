package com.scentbird.player.stomp;

import com.scentbird.common.loadbalancing.GameServerLoadBalancer;
import com.scentbird.common.payload.requests.JoinGameRequest;
import com.scentbird.common.payload.requests.StompRequest;
import com.scentbird.common.payload.responses.StompResponse;
import com.scentbird.common.servicediscovery.ServiceDiscovery;
import com.scentbird.player.command.PlayerCommand;
import com.scentbird.player.stomp.handlers.StompResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;

import static com.scentbird.common.stomp.StompDestinations.APP_PREFIX;

@Service
@Slf4j
public class StompClientServiceImpl implements StompClientService {

    private static final String WEB_SOCKET_URL_TEMPLATE = "ws://%s:%s%s";

    @Value("${player.username}")
    private String playerUsername;

    private StompSession session;
    private final ServiceDiscovery serviceDiscovery;
    private final GameServerLoadBalancer gameServerLoadBalancer;

    public StompClientServiceImpl(@Value("${game.server.name}") String gameServerName,
                                  ServiceDiscovery serviceDiscovery,
                                  GameServerLoadBalancer gameServerLoadBalancer,
                                  List<StompResponseHandler> stompResponseHandlers) {
        this.serviceDiscovery = serviceDiscovery;
        this.gameServerLoadBalancer = gameServerLoadBalancer;
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new GsonMessageConverter());

        List<ServiceInstance> gameServerInstances = serviceDiscovery.discover(gameServerName);
        ServiceInstance gameServer = gameServerLoadBalancer.choose(gameServerInstances);

        String serverHost = gameServer.getHost();
        int serverPort = gameServer.getPort();
        String serverStompEndpoint = gameServer.getMetadata().get("stomp-endpoint");
        String gameServerStompURL = String.format(WEB_SOCKET_URL_TEMPLATE, serverHost, serverPort, serverStompEndpoint);
        StompSessionHandler sessionHandler = new PlayerStompSessionHandler(stompResponseHandlers);
        stompClient.connectAsync(gameServerStompURL, sessionHandler);
    }

    @Override
    public void send(StompRequest request) {
        String destination = APP_PREFIX + request.getDestination();
        log.info("Sending message to server on destination {}: {}", destination, request);
        session.send(destination, request);
    }

    private class PlayerStompSessionHandler extends StompSessionHandlerAdapter {

        private final List<StompResponseHandler> stompResponseHandlers;

        private PlayerStompSessionHandler(List<StompResponseHandler> stompResponseHandlers) {
            this.stompResponseHandlers = stompResponseHandlers;
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            StompClientServiceImpl.this.session = session;
            for (StompResponseHandler stompResponseHandler : stompResponseHandlers) {
                session.subscribe(stompResponseHandler.getSupportedDestination(), new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return stompResponseHandler.getSupportedType();
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        PlayerCommand command = stompResponseHandler.convert((StompResponse) payload);
                        command.setStompClientService(StompClientServiceImpl.this);
                        command.execute();
                    }
                });
            }
            send(JoinGameRequest.builder().username(playerUsername).build());
        }
    }

}