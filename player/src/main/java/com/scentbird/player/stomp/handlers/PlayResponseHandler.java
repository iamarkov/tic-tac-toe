package com.scentbird.player.stomp.handlers;

import com.scentbird.common.payload.responses.PlayResponse;
import com.scentbird.player.command.PlayCommand;
import com.scentbird.player.command.PlayerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

import static com.scentbird.common.stomp.StompDestinations.*;

@Component
@RequiredArgsConstructor
public class PlayResponseHandler implements StompResponseHandler<PlayResponse> {

    @Value("${player.username}")
    private String playerUsername;

    @Value("${player.delay.seconds}")
    private int playerDelaySeconds;

    @Override
    public String getSupportedDestination() {
        return USER_PREFIX + QUEUE_PREFIX + PLAY;
    }

    @Override
    public Type getSupportedType() {
        return PlayResponse.class;
    }

    @Override
    public PlayerCommand convert(PlayResponse response) {
        return PlayCommand.builder()
                .username(playerUsername)
                .roomId(response.getRoomId())
                .gameField(response.getGameField())
                .playerDelaySeconds(playerDelaySeconds)
                .build();
    }
}