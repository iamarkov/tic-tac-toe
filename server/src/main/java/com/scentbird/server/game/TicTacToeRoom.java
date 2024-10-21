package com.scentbird.server.game;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.server.game.command.ChooseSymbolCommand;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.game.command.PlayCommand;
import com.scentbird.server.stomp.StompMessageSender;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class TicTacToeRoom implements TicTacToeGame {

    private final ReentrantReadWriteLock roomLock;
    private final AtomicInteger roomCounter;
    @Getter
    private final String roomId;
    @Getter
    private final Map<String, Player> players;
    private TicTacToeState roomState;
    @Getter
    private final StompMessageSender stompMessageSender;
    private final TicTacToeSymbol[][] gameField;
    @Getter
    private Player winner;

    public TicTacToeRoom(StompMessageSender stompMessageSender) {
        this.roomLock = new ReentrantReadWriteLock();
        this.roomCounter = new AtomicInteger();
        this.roomId = generateRoomId();
        this.players = new HashMap<>();
        this.stompMessageSender = stompMessageSender;
        this.roomState = new WaitingRegistrationState(this);
        this.gameField = new TicTacToeSymbol[3][3];
    }

    @Override
    public boolean registerPlayer(JoinGameCommand joinGameCommand) {
        try {
            roomLock.writeLock().lock();
            return roomState.registerPlayer(joinGameCommand);
        } finally {
            roomLock.writeLock().unlock();
        }
    }

    @Override
    public boolean chooseSymbol(ChooseSymbolCommand chooseSymbolCommand) {
        try {
            roomLock.writeLock().lock();
            return roomState.chooseSymbol(chooseSymbolCommand);
        } finally {
            roomLock.writeLock().unlock();
        }
    }

    @Override
    public boolean play(PlayCommand playCommand) {
        try {
            roomLock.writeLock().lock();
            return roomState.play(playCommand);
        } finally {
            roomLock.writeLock().unlock();
        }
    }

    public TicTacToeSymbol[][] getGameField() {
        //we create a deep copy of the game field board so that it is not modified by the outer world
        TicTacToeSymbol[][] gameFieldCopy = new TicTacToeSymbol[gameField.length][gameField[0].length];
        for (int rowIndex = 0; rowIndex < gameField.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameField[0].length; columnIndex++) {
                gameFieldCopy[rowIndex][columnIndex] = gameField[rowIndex][columnIndex];
            }
        }
        return gameFieldCopy;
    }

    public void printGameBoard() {
        for (int rowIndex = 0; rowIndex < gameField.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameField[0].length; columnIndex++) {
                TicTacToeSymbol symbol = gameField[rowIndex][columnIndex];
                if (symbol == null) {
                    System.out.print("    ");
                } else {
                    System.out.print(gameField[rowIndex][columnIndex] + "   ");
                }
            }
            System.out.println();
        }
    }

    void markOnField(TicTacToeSymbol symbol, int rowIndex, int columnIndex) {
        gameField[rowIndex][columnIndex] = symbol;
    }

    boolean hasWinner() {
        //we can combine rows and columns checks to reduce iterations
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            if (gameField[rowIndex][0] != null &&
                    gameField[rowIndex][0] == gameField[rowIndex][1] &&
                    gameField[rowIndex][0] == gameField[rowIndex][2]) {
                return true;
            }
            if (gameField[0][rowIndex] != null &&
                    gameField[0][rowIndex] == gameField[1][rowIndex] &&
                    gameField[0][rowIndex] == gameField[2][rowIndex]) {
                return true;
            }
        }

        //and check diagonals
        if (gameField[0][0] != null &&
                gameField[0][0] == gameField[1][1] &&
                gameField[0][0] == gameField[2][2]) {
            return true;
        }

        if (gameField[0][2] != null &&
                gameField[0][2] == gameField[1][1] &&
                gameField[0][2] == gameField[2][0]) {
            return true;
        }

        return false;
    }

    void markWinner(Player player) {
        if (winner != null || !players.containsKey(player.getUsername())) {
            log.error("Winner not valid!");
            return;
        }
        winner = player;
    }

    void transit(StateFactoryMethod factoryMethod) {
        try {
            roomLock.writeLock().lock();
            String oldStateName = roomState.getClass().getSimpleName();
            roomState = factoryMethod.create(this);
            String newStateName = roomState.getClass().getSimpleName();
            log.info("Room {} is moving from state {} to state {}", roomId, oldStateName, newStateName);
            roomState.markStateReady();
        } finally {
            roomLock.writeLock().unlock();
        }
    }

    private String generateRoomId() {
        String uuidInput = Instant.now().toString() + roomCounter.getAndIncrement();
        return UUID.nameUUIDFromBytes(uuidInput.getBytes()).toString();
    }

}
