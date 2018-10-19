package com.cspy.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Poke implements Comparable<Poke> {
    private int number;
    private int pattern;
    private boolean special;
    private int changedNumber;
    private int changedPattern;

    public static final int NUM_OF_PACK = 54;

    public static String[] pokeNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    //-小王   +大王
    //number（下标）接上面的数组
    public static String[] pokeNumberAppend = {"-", "+"};
    //分别表示：红桃   黑桃  黑花  方块
    public static String[] pokePattern = {"!", "@", "#", "$"};

    public Poke() {
        this(-1, -1);
    }

    public Poke(int number, int pattern) {
        if (number > -1 && number < pokeNumber.length + 2) {
            this.number = number;
        } else {
            this.number = -1;
        }
        if (pattern > -1 && pattern < pokePattern.length + 2) {
            this.pattern = pattern;
        } else {
            this.pattern = -1;
        }
        this.changedNumber = -1;
        this.changedPattern = -1;
    }

    public int getNumber() {
        return number;
    }

    //返回显示中间值
    public String getNumberShow() {
        if (number > -1) {
            if (number < pokeNumber.length) {
                return pokeNumber[number];
            } else if (number < pokeNumber.length + 2) {
                return pokeNumberAppend[number - pokeNumber.length];
            }
        }
        return "无效数字";
    }

    public int getNumberWithSpecial() {
        if (special) {
            return changedNumber;
        } else {
            return number;
        }
    }

    public int getPattern() {
        return pattern;
    }

    //返回显示中间值
    public String getPatternShow() {
        if (pattern > -1 && pattern < pokePattern.length) {
            return pokePattern[pattern];
        } else {
            return "无效图案";
        }
    }


    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }


    public int getChangedNumber() {
        return changedNumber;
    }

    //只有当这张牌是特殊牌，才提供第二属性
    public void setChangedNumber(int changedNumber) {
        if (special) {
            this.changedNumber = changedNumber;
        }
    }


    public int getChangedPattern() {
        return changedPattern;
    }

    public void setChangedPattern(int changedPattern) {
        if (special) {
            this.changedPattern = changedPattern;
        }
    }

    public static List<Poke> getPokePacks(int packs) {
        if (packs > 0) {
            Poke[] pokes = new Poke[packs * NUM_OF_PACK];
            for (int k = 0; k < packs; k++) {
                for (int n = 0; n < pokeNumber.length; n++) {
                    for (int p = 0; p < pokePattern.length; p++) {
                        pokes[n * pokePattern.length + p + k * NUM_OF_PACK] = new Poke(n, p);
                    }
                }
                pokes[(k + 1) * NUM_OF_PACK - 2] = new Poke(pokeNumber.length, -1);
                pokes[(k + 1) * NUM_OF_PACK - 1] = new Poke(pokeNumber.length + 1, -1);

            }
            return new ArrayList<>(Arrays.asList(pokes));
        } else {
            return new ArrayList<>(0);
        }
    }

    public static List<Poke> getRandomPokes(int packs) {
        List<Poke> pokeList = getPokePacks(packs);
        Collections.shuffle(pokeList);
        return pokeList;
    }

    public static Poke getSmallKing() {
        return new Poke(pokeNumber.length,-1);
    }

    public static Poke getBigKing() {
        return new Poke(pokeNumber.length + 1,-1);
    }

    @Override
    public String toString() {
        JSONObject show = new JSONObject();
        if(special) {
            return "(*" + changedNumber +"," + changedPattern +"*)";
        } else {
            return "(" + number +"," + pattern +")";
        }
    }

    public String toDetailedString() {
        JSONObject show = new JSONObject();
        show.put("数字", getNumberShow());
        show.put("数字index", getNumber());
        show.put("花色", getPatternShow());
        show.put("花色index", getPattern());
        show.put("特殊", special);
        if (special) {
            show.put("更改后数字index", getChangedNumber());
            show.put("更改后图案index", getChangedPattern());
        }
        return "该扑克属性为：" + show.toString();

    }

    public Poke clone(int specialNumber) {
        Poke clonePoke = new Poke(number,pattern);
        if (specialNumber == number && pattern == 0) {
            clonePoke.special = true;
        }
        return clonePoke;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Poke) {
            Poke anotherPoke = (Poke)obj;
            return this.getNumber() == anotherPoke.getNumber() && this.getPattern() == anotherPoke.getPattern();
        } else {
            return false;
        }
    }

    public boolean equal (Poke anotherPoke) {
        if (special && anotherPoke.special) {
            return true;
        }
//        if (special && anotherPoke.getNumber() < 13) {
//            setChangedNumber(anotherPoke.getNumber());
//            setChangedPattern(anotherPoke.getPattern());
//            return true;
//        }
        if (anotherPoke.special && getNumber() < 13) {
            anotherPoke.setChangedNumber(getNumber());
            anotherPoke.setChangedPattern(getPattern());
            return true;
        }
        return this.getNumber() == anotherPoke.getNumber() && this.getPattern() == anotherPoke.getPattern();
    }


    @Override
    public int compareTo(Poke o) {
        if(o.isSpecial()) {
            return number - o.getChangedNumber();
        } else {
            return number - o.getNumber();
        }
    }
}
