package com.scentbird.common.payload.responses;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.stomp.StompDestinations;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServerStateResponse extends StompResponse {

    private List<RoomInfo> rooms;

    @Override
    public String getDestination() {
        return StompDestinations.STATE;
    }

    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class RoomInfo {
        private String id;
        private List<TicTacToeSymbol> gameField;
        private String state;
        private String winner;
        private List<PlayerInfo> players;
    }

    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class PlayerInfo {
        private String username;
        private TicTacToeSymbol symbol;
        private boolean isOnline;
    }

}