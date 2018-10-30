package com.cspy.util;

public enum RoomState {
    LAKE_PLAYER("空缺",0),
    NOT_READY("预备",1),
    GAMING("游戏中",2);


    private String name;
    private int level;
    private RoomState(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public String toString() {
        return this.name;
    }


    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
