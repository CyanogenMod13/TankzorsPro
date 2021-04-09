package com.tanchiki.libgdx.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static GameServer gameServer = null;
    public static GameServer getInstance() {
        if (gameServer == null)
            gameServer = new GameServer();
        return gameServer;
    }

    private ServerSocket server;
    private final String address = "localhost";
    private final int port = 1488;
    private final List<Socket> sockets = new ArrayList<>(20);
    private boolean isWaiting = false;

    private GameServer() {
        try {
            server = new ServerSocket(port, 50, InetAddress.getByName(address));
            System.out.println("Server ip: " + server.getInetAddress() + ", port: " + server.getLocalPort() + " was created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getListClient() {
        synchronized (sockets) {
            String[] s = new String[sockets.size()];
            for (int i = 0; i < s.length; i++)
                s[i] = sockets.get(i).toString();
            return s;
        }
    }

    public void waitForClient() {
        if (isWaiting) throw new IllegalStateException();
        isWaiting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start waiting for client...");
                while (isWaiting) {
                    try {
                        Socket socket = server.accept();
                        if (!isWaiting) {
                            socket.close();
                            break;
                        }
                        synchronized (sockets) {
                            sockets.add(socket);
                            System.out.println("Socket " + socket.toString() + " was accepted by server");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Waiting stopped");
            }
        }).start();
    }

    public void sendData(byte[] data) {
        for (int i = 0; i < sockets.size(); i++) {
            Socket socket = sockets.get(i);
            try {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopWaiting() {
        isWaiting = false;
    }

    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
    }

    public void closeAllSockets() {
        for (int i = 0; i < sockets.size(); i++) {
            Socket socket = sockets.get(i);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sockets.clear();
    }

    public void shutdown() {

    }
}
