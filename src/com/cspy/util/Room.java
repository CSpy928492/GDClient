package com.cspy.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class Room implements Comparable<Room> {

    private int roomNumber;
    private List<Player> players;
    private RoomState roomState;


    public Room(int roomNumber, List<Player> players, RoomState roomState) {
        this.roomNumber = roomNumber;
        this.players = players;
        this.roomState = roomState;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public RoomState getRoomState() {
        return roomState;
    }

    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
    }

    public boolean isFull() {
        return this.players.size() >= 4;
    }

    @Override
    public String toString() {
        JSONObject toString = new JSONObject();
        toString.put("roomNumber", roomNumber);
        toString.put("roomState", roomState);

        JSONArray playerArray = new JSONArray();
        playerArray.addAll(players);
        toString.put("players", playerArray);
        return toString.toString();
    }


    @Override
    public int compareTo(Room o) {
        return this.roomNumber - o.roomNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            Room room = (Room) obj;
            return this.roomNumber == room.roomNumber;
        } else {
            return false;
        }
    }

    public static Room buildFromJSON(JSONObject object) {
        Room room = new Room(object.get("roomNumber"))
    }
}
