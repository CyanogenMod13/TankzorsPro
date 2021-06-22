package com.tanchiki.libgdx.server;

import java.rmi.registry.Registry;

public class GameServer {
    private static GameServer gameServer = null;

    public static GameServer getInstance() {
        if (gameServer == null)
            gameServer = new GameServer();
        return gameServer;
    }

    private Registry registry;

    private GameServer() {
    }

    public void createRoom(String name, int port) {

    }
}
