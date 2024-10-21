package com.scentbird.server.game;

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

    public TicTacToeRoom(StompMessageSender stompMessageSender) {
        this.roomLock = new ReentrantReadWriteLock();
        this.roomCounter = new AtomicInteger();
        this.roomId = generateRoomId();
        this.players = new HashMap<>();
        this.stompMessageSender = stompMessageSender;
        this.roomState = new WaitingRegistrationState(this);
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

    void transit(TicTacToeState nextState) {
        try {
            roomLock.writeLock().lock();
            log.info("Room {} is moving from state {} to state {}", roomId, roomState.getClass().getSimpleName(), nextState.getClass().getSimpleName());
            roomState = nextState;
        } finally {
            roomLock.writeLock().unlock();
        }
    }

    private String generateRoomId() {
        String uuidInput = Instant.now().toString() + roomCounter.getAndIncrement();
        return UUID.nameUUIDFromBytes(uuidInput.getBytes()).toString();
    }

}
