package com.phantasie.demo.controller;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Room implements Serializable {
    private int roomId;

    public GameStatus[] player = new GameStatus[2];
//    public boolean isready;
//    public int playerNow;
    public int roomsize;
    public String ownerName;

    public Room() {
        roomsize = 1;
        player = new GameStatus[2];
    }


    public void setOwner(int rid, GameStatus gameStatus) {
        roomId = rid;
        gameStatus.setGameId(rid);
        gameStatus.setSeat(0);
        gameStatus.setInRoom(true);
        player[0] = gameStatus;
        ownerName = gameStatus.getPlayerName();
    }

    public GameStatus[] getPlayer() {
        return player;
    }
}
