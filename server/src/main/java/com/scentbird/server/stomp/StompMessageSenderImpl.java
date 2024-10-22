package com.scentbird.server.stomp;

import com.scentbird.common.payload.responses.StompResponse;
import com.scentbird.server.lobby.UserRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.scentbird.common.stomp.StompDestinations.QUEUE_PREFIX;
import static com.scentbird.common.stomp.StompDestinations.TOPIC_PREFIX;

@Service
@RequiredArgsConstructor
@Slf4j
public class StompMessageSenderImpl implements StompMessageSender {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRegistry userRegistry;

    @Override
    public void sendToAll(StompResponse response) {
        String destination = TOPIC_PREFIX + response.getDestination();
        log.info("Sending STOMP message to all users: {}", response);
        messagingTemplate.convertAndSend(destination, response);
    }

    @Override
    public void sendToUser(String username, StompResponse response) {
        String sessionId = userRegistry.getOnlinePlayer(username).getSessionId();
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        String destination = QUEUE_PREFIX + response.getDestination();
        log.info("Sending STOMP message to {}: {}", sessionId, response);
        messagingTemplate.convertAndSendToUser(sessionId, destination, response, headerAccessor.getMessageHeaders());
    }

}
