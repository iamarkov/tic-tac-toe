package com.scentbird.server.game;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.server.game.command.PlayCommand;
import com.scentbird.server.lobby.LobbyService;
import com.scentbird.server.stomp.StompMessageSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class PlayingStateTest {


    private StompMessageSender stompMessageSender;
    private LobbyService lobbyService;
    private TicTacToeRoom gameRoomSpy;
    private PlayingState playingState;
    private Player player1;
    private Player player2;


    @BeforeEach
    public void setup() {
        //arrange
        stompMessageSender = Mockito.mock(StompMessageSender.class);
        lobbyService = Mockito.mock(LobbyService.class);
        gameRoomSpy = Mockito.spy(new TicTacToeRoom(stompMessageSender, lobbyService));

        player1 = new Player("player1");
        player1.setSymbol(TicTacToeSymbol.X);
        player2 = new Player("player2");
        player2.setSymbol(TicTacToeSymbol.O);
        Map<String, Player> players = new HashMap<>();
        players.put(player1.getUsername(), player1);
        players.put(player2.getUsername(), player2);

        Mockito.doReturn(players).when(gameRoomSpy).getPlayers();
        Mockito.doReturn(player1).when(gameRoomSpy).getCurrentPlayer();
        Mockito.doReturn(player2).when(gameRoomSpy).getNextPlayer();

        playingState = new PlayingState(gameRoomSpy);
    }

    @Test
    public void wrongUsernameTest() {
        //arrange
        PlayCommand playCommand = PlayCommand.builder()
                .gameRoom(gameRoomSpy)
                .username("player2")
                .build();

        //act
        //assert
        Assertions.assertFalse(playingState.play(playCommand));
    }

    @Test
    public void occupiedCellTest() {
        //arrange
        gameRoomSpy.markOnField(TicTacToeSymbol.X, 0, 0);

        PlayCommand playCommand = PlayCommand.builder()
                .gameRoom(gameRoomSpy)
                .username("player1")
                .rowIndex(0)
                .columnIndex(0)
                .build();

        //act
        //assert
        Assertions.assertFalse(playingState.play(playCommand));
    }

    @Test
    public void firstRowWinTest() {
        //arrange
        gameRoomSpy.markOnField(TicTacToeSymbol.X, 0, 0);
        gameRoomSpy.markOnField(TicTacToeSymbol.X, 0, 1);

        PlayCommand playCommand = PlayCommand.builder()
                .gameRoom(gameRoomSpy)
                .username("player1")
                .rowIndex(0)
                .columnIndex(2)
                .build();

        //act
        playingState.play(playCommand);

        //assert
        Assertions.assertEquals(player1, gameRoomSpy.getWinner());
    }

    //TODO test other win conditions

}
