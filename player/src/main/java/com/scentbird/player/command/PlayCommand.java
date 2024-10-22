package com.scentbird.player.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.requests.PlayRequest;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Builder
@ToString
@Slf4j
@Getter
public class PlayCommand extends PlayerCommand {

    private final String username;
    private final String roomId;
    private final TicTacToeSymbol[][] gameField;
    private final int playerDelaySeconds;

    @Override
    public void execute() {
        try {
            Thread.sleep(1000L * playerDelaySeconds);
        } catch (InterruptedException e) {
            log.info("Player interrupted while thinking: {}", e.getMessage());
        }
        List<EmptyCell> emptyCells = findEmptyCells(gameField);
        Random random = new Random();
        EmptyCell randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));
        PlayRequest playRequest = PlayRequest.builder()
                .rowIndex(randomEmptyCell.rowIndex)
                .columnIndex(randomEmptyCell.columnIndex)
                .username(username)
                .roomId(roomId)
                .build();
        stompClientService.send(playRequest);
    }

    private List<EmptyCell> findEmptyCells(TicTacToeSymbol[][] gameField) {
        List<EmptyCell> emptyCells = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < gameField.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameField[0].length; columnIndex++) {
                if (gameField[rowIndex][columnIndex] == null) {
                    emptyCells.add(new EmptyCell(rowIndex, columnIndex));
                }
            }
        }
        return emptyCells;
    }

    @AllArgsConstructor
    private static class EmptyCell {
        private int rowIndex;
        private int columnIndex;
    }

}