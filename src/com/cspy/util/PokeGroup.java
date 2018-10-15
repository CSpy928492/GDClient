package com.cspy.util;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PokeGroup {
    Type type;

    private List<Poke> group;

    public PokeGroup(List<Poke> group) {
        this.group = group;
    }

    public List<Poke> getGroup() {
        return group;
    }

    public JSONObject analysisGroup(int specialNumber) {
        JSONObject result = new JSONObject();
        List<Poke> list = null;

        switch (group.size()) {
            case 1:
                type = Type.ONE;
                result.put("结果", type.getName());
                break;
            case 2:
                result = isTWO(specialNumber);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                type = Type.INVALID;
                result.put("结果", type.getName());
        }
        return result;
    }

    //判断是否成对
    public JSONObject isTWO(int specialNumber) {
        JSONObject result = new JSONObject();
        List<Poke> list = getSpecialPoke(specialNumber);
        //判断有无特殊爱心牌
        if (list == null) {
            List<PokeArray> arrays = getPokeClear(specialNumber);
            PokeArray array = arrays.get(0);
            if (array.getSize() == 2) {
                type = Type.TWO;
                result.put("结果", type.getName());
                result.put("1", array.getContainList().get(0));
                result.put("2", array.getContainList().get(1));
            } else {
                type = Type.INVALID;
                result.put("结果", type.getName());
            }
            //只有一个爱心牌
        } else if (list.size() == 1) {
            List<PokeArray> arrays = getPokeClear(specialNumber);
            int leftPokeNumber = arrays.get(0).getNumber();
            Poke leftPoke = arrays.get(0).getOne();
            //特殊牌不能转换成大王小王
            if (leftPokeNumber == Poke.pokeNumber.length ||
                    leftPokeNumber == Poke.pokeNumber.length + 1) {
                type = Type.INVALID;
                result.put("结果", type.getName());
            } else {
                type = Type.TWO;
                list.get(0).setChangedNumber(leftPoke.getNumber());
                list.get(0).setChangedPattern(leftPoke.getPattern());
                result.put("结果", type.getName());
                result.put("1", leftPoke);
                result.put("2", list.get(0));
            }
        } else if (list.size() == 2) {
            type = Type.TWO;
            result.put("结果", type.getName());
            result.put("1", list.get(0));
            result.put("2", list.get(1));
        }
        return result;
    }

    public JSONObject isTHREE(int specialNumber) {
        JSONObject result = new JSONObject();
        List<Poke> list = getSpecialPoke(specialNumber);
        List<PokeArray> arrays = getPokeClear(specialNumber);
        PokeArray leftPokeArray = arrays.get(0);
        //判断有无特殊爱心牌
        if (list == null) {
            if (leftPokeArray.getSize() == 3) {
                type = Type.TWO;
                result.put("结果", type.getName());
                result.put("1", leftPokeArray.getContainList().get(0));
                result.put("2", leftPokeArray.getContainList().get(1));
                result.put("3", leftPokeArray.getContainList().get(2));
            } else {
                type = Type.INVALID;
                result.put("结果", type.getName());
            }
        } else if (list.size() == 1) {
            //特殊牌不能转换成大王小王
            if (leftPokeArray.getNumber() == Poke.pokeNumber.length
                    || leftPokeArray.getNumber() == Poke.pokeNumber.length + 1
                    || leftPokeArray.getSize() != 2) {
                type = Type.INVALID;
                result.put("结果", type.getName());
            } else {
                type = Type.THREE;
                List<Poke> l = leftPokeArray.getContainList();
                list.get(0).setChangedNumber(l.get(0).getNumber());
                list.get(0).setChangedPattern(l.get(0).getPattern());
                result.put("结果", type.getName());
                result.put("1", list.get(0));
                result.put("2", l.get(0));
                result.put("3", l.get(1));
            }
        } else if (list.size() == 2) {
            Poke leftNumber = leftPokeArray.getOne();
            if (leftNumber.getNumber() != Poke.pokeNumber.length
                    || leftNumber.getNumber() != Poke.pokeNumber.length + 1) {
                type = Type.INVALID;
                result.put("结果", type.getName());
                return result;
            } else {
                type = Type.THREE;
                result.put("结果", type.getName());
                list.get(0).setChangedNumber(leftNumber.getNumber());
                list.get(0).setChangedPattern(leftNumber.getPattern());
                list.get(1).setChangedNumber(leftNumber.getNumber());
                list.get(1).setChangedPattern(leftNumber.getPattern());
                result.put("1", list.get(0));
                result.put("2", list.get(1));
            }
        }
        return result;
    }




    //返回特殊数字（红心）的集合，当specialNum不是正常数字的时候（而是大小王），返回错误
    private ArrayList<Poke> getSpecialPoke(int specialNum) {
        ArrayList<Poke> list = null;
        if (specialNum > -1 && specialNum < Poke.pokeNumber.length) {
            list = new ArrayList();
            for (Poke poke : group) {
                if (poke.getNumber() == specialNum
                        && poke.getPatternShow().equals("!")) {
                    //待删
                    poke.setSpecial(true);
                    list.add(poke);
                }
            }
            if (list.size() > 2) {
                return null;
            }
        }
        return list;
    }

    //返回整理之后的牌
    public ArrayList<PokeArray> getPokeClear(int specialNum) {

        //先把特殊的红桃提取出来
        ArrayList<Poke> specialArray = getSpecialPoke(specialNum);

        ArrayList<PokeArray> arrays = new ArrayList<>();
        for (Poke poke : group) {
            //跳过该红桃
            if (specialArray != null && specialArray.contains(poke)) {
                continue;
            }
            if (arrays.size() == 0) {
                PokeArray a = new PokeArray();
                a.getContainList().add(poke);
                arrays.add(a);
            } else {
                boolean inserted = false;
                //分类，相同的直接添加到PokeArray，不同的则创建PokeArray添加
                for (PokeArray arr : arrays) {
                    if (arr.getNumber() == poke.getNumber()) {
                        arr.getContainList().add(poke);
                        inserted = true;
                    }
                }
                if (!inserted) {
                    PokeArray a = new PokeArray();
                    a.getContainList().add(poke);
                    arrays.add(a);
                }
            }
        }
        if (specialArray != null && specialArray.size() != 0) {
            PokeArray a = new PokeArray();
            a.getContainList().addAll(specialArray);
            arrays.add(a);
        }
        return arrays;
    }


}

enum Type {
    INVALID("无效牌组", 0),
    ONE("单牌", 1), TWO("对子", 1), THREE("三不带", 1), THREE_WITH_TWO("三带二", 1),
    THREE_COUPLE("连对", 1), TWO_TRIPLE("飞机", 1), FIVE_COMBO("顺子", 1),
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
