package com.cspy.util;

import com.alibaba.fastjson.JSONObject;

public class Player {

    private String username;
    private String token;
    private PlayerState playerState;
    private String ip;

    public Player(String username, String token, PlayerState playerState, String ip) {
        this.username = username;
        this.token = token;
        this.playerState = playerState;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        JSONObject toString = new JSONObject();
        toString.put("token",token);
        toString.put("playerState",playerState);
        toString.put("ip", ip);
        return toString.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return this.token.equals(player.token);
        } else {
            return false;
        }
    }
}
