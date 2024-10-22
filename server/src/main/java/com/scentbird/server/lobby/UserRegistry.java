package com.scentbird.server.lobby;

import com.scentbird.server.game.Player;

public interface UserRegistry {
    Player getOnlinePlayer(String username);

    void markPlayerOnline(Player player);

    void markPlayerOffline(String sessionId);
}
