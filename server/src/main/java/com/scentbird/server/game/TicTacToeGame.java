package com.scentbird.server.game;

import com.scentbird.server.game.command.ChooseSymbolCommand;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.game.command.PlayCommand;

//here we apply the State design pattern to handle different commands issued to the game differently based on the
//current state of the game
public interface TicTacToeGame {

    boolean registerPlayer(JoinGameCommand joinGameCommands);

    boolean chooseSymbol(ChooseSymbolCommand chooseSymbolCommand);

    boolean play(PlayCommand playCommand);

}
