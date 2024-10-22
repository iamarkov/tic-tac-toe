package com.scentbird.server.stomp;

import com.scentbird.server.lobby.UserRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class GameChannelInterceptor implements ChannelInterceptor {

    private static final String SIMP_SESSION_ID_HEADER = "simpSessionId";
    public static final String SIMP_DESTINATION_HEADER = "simpDestination";

    private final UserRegistry userRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();
        return switch (command) {
            case CONNECT -> handleConnectCommand(message, channel);
            case DISCONNECT -> handleDisconnectCommand(message, channel);
            case SUBSCRIBE -> handleSubscribeCommand(message, channel);
            default -> ChannelInterceptor.super.preSend(message, channel);
        };
    }

    protected Message<?> handleConnectCommand(Message<?> message, MessageChannel channel) {
        String sessionId = (String) message.getHeaders().get(SIMP_SESSION_ID_HEADER);
        log.info("User connected to STOMP with sessionId: {}", sessionId);
        return ChannelInterceptor.super.preSend(message, channel);
    }

    protected Message<?> handleDisconnectCommand(Message<?> message, MessageChannel channel) {
        String sessionId = (String) message.getHeaders().get(SIMP_SESSION_ID_HEADER);
        log.info("User with sessionId {} disconnected from STOMP", sessionId);
        userRegistry.markPlayerOffline(sessionId);
        return ChannelInterceptor.super.preSend(message, channel);
    }

    protected Message<?> handleSubscribeCommand(Message<?> message, MessageChannel channel) {
        String sessionId = (String) message.getHeaders().get(SIMP_SESSION_ID_HEADER);
        String destination = message.getHeaders().get(SIMP_DESTINATION_HEADER).toString();
        log.info("User with sessionId {} connected to destination {}", sessionId, destination);
        return ChannelInterceptor.super.preSend(message, channel);
    }

}