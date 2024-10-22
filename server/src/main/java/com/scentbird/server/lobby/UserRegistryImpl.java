package com.scentbird.server.lobby;

import com.scentbird.server.game.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserRegistryImpl implements UserRegistry {

    private final Map<String, Player> onlinePlayers;
    private final Map<String, String> sessionIdToUsernameMapping;

    public UserRegistryImpl() {
        this.onlinePlayers = new ConcurrentHashMap<>();
        this.sessionIdToUsernameMapping = new ConcurrentHashMap<>();
    }

    @Override
    public Player getOnlinePlayer(String username) {
        return onlinePlayers.get(username);
    }

    @Override
    public void markPlayerOnline(Player player) {
        onlinePlayers.put(player.getUsername(), player);
        sessionIdToUsernameMapping.put(player.getSessionId(), player.getUsername());
    }

    @Override
    public void markPlayerOffline(String sessionId) {
        String username = sessionIdToUsernameMapping.get(sessionId);
        if (username != null) {
            onlinePlayers.remove(username);
            sessionIdToUsernameMapping.remove(sessionId);
        }
    }

}
