package com.cspy.util;

public enum Type {
    INVALID("无效牌组", 0),
    ONE("单牌", 1), TWO("对子", 1), THREE("三不带", 1), THREE_WITH_TWO("三带二", 1),
    THREE_COUPLE("连对", 1), TWO_TRIPLE("三顺", 1), FIVE_COMBO("顺子", 1),
    BOOM("炸弹", 2),
    FIVE_BOOM("五连炸", 3),
    FIVE_COMBO_BOOM("顺金", 4),
    SIX_BOOM("六连炸", 5),
    WANG_BOOM("王炸", 6);

    private String name;
    private int level;

    private Type(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }
}
