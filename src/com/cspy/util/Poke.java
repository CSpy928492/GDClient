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
    private boolean changed;

    public static final int NUM_OF_PACK = 54;

    public static String[] pokeNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    //-小王   +大王
    //number（下标）接上面的数组
    public static String[] pokeNumberAppend = {"-", "+"};
    //分别表示：红桃  方块 黑桃  黑花
    public static String[] pokePattern = {"!", "@", "#", "$"};

    public Poke() {
        this(-1, -1, false);
    }

    public Poke(int number, int pattern, boolean special) {
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
        this.special = special;
        this.changedNumber = -1;
        this.changedPattern = -1;
    }

    public int getNumber() {
        return number;
    }

    //返回显示中间值
    public String getNumberShow() {
        int number = getNumberWithSpecial();
        if (number > -1) {
            if (number < pokeNumber.length) {
                return pokeNumber[number];
            } else if (number < pokeNumber.length + 2) {
                return pokeNumberAppend[number - pokeNumber.length];
            }
        }
        return "*";
    }



    public int getNumberWithSpecial() {
        if (special && changed) {
            return changedNumber;
        } else {
            return number;
        }
    }

    public int getPatternWithSpecial() {
        if (special && changed) {
            return changedPattern;
        }else {
            return pattern;
        }
    }

    public int getPattern() {
        return pattern;
    }

    //返回显示中间值
    public String getPatternShow() {
        int pattern = getPatternWithSpecial();
        if (pattern > -1 && pattern < pokePattern.length) {
            return pokePattern[pattern];
        } else {
            return "*";
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
            changed = true;
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getChangedPattern() {
        return changedPattern;
    }

    public void setChangedPattern(int changedPattern) {
        if (special) {
            this.changedPattern = changedPattern;
            changed = true;
        }
    }

    public static List<Poke> getPokePacks(int packs, int specialNumber) {
        if (packs > 0) {
            Poke[] pokes = new Poke[packs * NUM_OF_PACK];
            for (int k = 0; k < packs; k++) {
                for (int n = 0; n < pokeNumber.length; n++) {
                    for (int p = 0; p < pokePattern.length; p++) {
                        pokes[n * pokePattern.length + p + k * NUM_OF_PACK] = new Poke(n, p,n == specialNumber && p ==0 );
                    }
                }
                pokes[(k + 1) * NUM_OF_PACK - 2] = new Poke(pokeNumber.length, -1,false);
                pokes[(k + 1) * NUM_OF_PACK - 1] = new Poke(pokeNumber.length + 1, -1,false);

            }
            return new ArrayList<>(Arrays.asList(pokes));
        } else {
            return new ArrayList<>(0);
        }
    }

    public static List<Poke> getRandomPokes(int packs, int specialNumber) {
        List<Poke> pokeList = getPokePacks(packs, specialNumber);
        Collections.shuffle(pokeList);
        return pokeList;
    }

    public static Poke getSmallKing() {
        return new Poke(pokeNumber.length,-1,false);
    }

    public static Poke getBigKing() {
        return new Poke(pokeNumber.length + 1,-1,false);
    }

    @Override
    public String toString() {
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
        Poke clonePoke = new Poke(number,pattern,special);
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
