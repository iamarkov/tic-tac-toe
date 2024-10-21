package com.scentbird.player.command;

import com.scentbird.common.command.Command;
import com.scentbird.player.stomp.StompClientService;
import lombok.Setter;

public abstract class PlayerCommand implements Command {

    @Setter
    protected StompClientService stompClientService;

}
