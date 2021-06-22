package com.tanchiki.libgdx.stage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameStageImpl extends Remote {
    void startLevel(byte[] data) throws RemoteException;
}
