package com.scentbird.server.game;

@FunctionalInterface
interface StateFactoryMethod {
    TicTacToeState create(TicTacToeRoom gameRoom);
}