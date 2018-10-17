package com.cspy.util;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
                List<PokeArray> requirement = getFlowRequirement(getPurePoke(specialNumber, group));
                System.out.println("requirement:");
                for (PokeArray array : requirement) {
                    System.out.println("这是一个方案：");
                    for (Poke p : array.getContainList()) {
                        System.out.println(p);
                    }
                }
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
        List<Poke> list = getSpecialPoke(specialNumber, group);
        //判断有无特殊爱心牌
        if (list == null) {
            List<PokeArray> arrays = getPokeClear(specialNumber, group);
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
            List<PokeArray> arrays = getPokeClear(specialNumber, group);
            int leftPokeNumber = arrays.get(0).getOne().getNumber();
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


    //判断是否是王炸
    public boolean isWangBoom(List<PokeArray> clearPoke) {
        if (clearPoke.size() == 2) {
            for (PokeArray array : clearPoke) {
                int arrayName = array.getOne().getNumber();
                if (!(arrayName == Poke.getSmallKing().getNumber()
                        || arrayName == Poke.getBigKing().getNumber())
                        || array.getSize() != 2) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    //判断三带二
    public List<PokeArray> getThreeWithTwoRequirements(List<PokeArray> pureList, int specialNum) {
        List<PokeArray> solutions = new ArrayList<>();

        switch (pureList.size()) {
            case 1:
                int fixNum = 3 - pureList.get(0).getSize();
                List<Poke> fix = getByNumber(fixNum, pureList.get(0).getOne());

                PokeArray a1 = new PokeArray();
                a1.getContainList().addAll(getByNumber(2, Poke.getSmallKing()));
                a1.getContainList().addAll(fix);
                solutions.add(a1);
                PokeArray a2 = new PokeArray();
                a2.getContainList().addAll(getByNumber(2, Poke.getBigKing()));
                a2.getContainList().addAll(fix);
                solutions.add(a2);
                if (fixNum == 0) {
                    PokeArray a3 = new PokeArray();
                    a3.getContainList().add(new Poke(specialNum, 0));
                    a3.getContainList().add(new Poke(specialNum, 0));
                    solutions.add(a3);
                }
                return solutions;
            case 2:
                solutions = fixThreeWithTwo(pureList);
                return solutions;
            default:
                return null;
        }

    }

    //根据数字返回相同的牌
    public List<Poke> getByNumber(int number, Poke poke) {
        List<Poke> pokes = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            pokes.add(new Poke(poke.getNumber(), poke.getPattern()));
        }
        return pokes;
    }

    //只修复三带二
    private List<PokeArray> fixThreeWithTwo(List<PokeArray> unfixedList) {
        if (unfixedList.size() == 2) {
            int count = 0;
            int index = 0;
            for (int i = 0; i < unfixedList.size(); i++) {
                PokeArray array = unfixedList.get(i);
                count += array.getSize();
                if (array.getSize() == 1) {
                    index = i;
                }
            }
            List<PokeArray> fixed = new ArrayList<>();
            //只能是3或4，否则返回错误
            switch (count) {
                case 3:
                    PokeArray s1 = new PokeArray();
                    s1.getContainList().add(unfixedList.get(0).getOne());
                    s1.getContainList().add(unfixedList.get(1).getOne());
                    fixed.add(s1);
                    PokeArray s2 = new PokeArray();
                    s2.getContainList().add(unfixedList.get(index).getOne());
                    s2.getContainList().add(unfixedList.get(index).getOne());
                    fixed.add(s2);
                    return fixed;
                case 4:
                    PokeArray s3 = new PokeArray();
                    s3.getContainList().add(unfixedList.get(0).getOne());
                    fixed.add(s3);
                    PokeArray s4 = new PokeArray();
                    s4.getContainList().add(unfixedList.get(1).getOne());
                    fixed.add(s4);
                    return fixed;
                default:
                    return null;
            }

        } else {
            return null;
        }

    }

    private int getCardNumber(List<PokeArray> cardList) {
        int count = 0;
        for (PokeArray array:cardList) {
            count += array.getSize();
        }
        return count;
    }

    public List<PokeArray> getCoupleTripleRequirements(List<PokeArray> pureList) {
        int cardNumber = getCardNumber(pureList);
        if (cardNumber < 4 || cardNumber > 6) {
            return null;
        }
        if (pureList.size() < 4 && pureList.size() > 1) {
            int maxLength = 0;
            for (PokeArray array : pureList) {
                maxLength = array.getSize() > maxLength ? array.getSize() : maxLength;
            }
            switch (maxLength) {
                case 2:
                    return getMax2(pureList);
                case 3:
                    return getMax3(pureList);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    //数组最大长度为2
    private List<PokeArray> getMax2(List<PokeArray> pureList) {
        List<PokeArray> solutions = new ArrayList<>();
        List<Integer> index = new ArrayList<>();
        for (PokeArray array : pureList) {
            index.add(array.getOne().getNumber());
        }
        index.sort(Integer::compareTo);
        switch (pureList.size()) {
            case 2:
                if (index.get(0) > 0) {
                    PokeArray s1 = new PokeArray();
                    s1.getContainList().addAll(getByNumber(2, new Poke(index.get(0) - 1, 1)));
//                    for(PokeArray a:pureList) {
//                        if (a.getSize() < 2) {
//                            s1.getContainList().add(new Poke(a.getOne().getNumber(),0));
//                        }
//                    }
                    solutions.add(s1);
                }
                if (index.get(1) < Poke.pokeNumber.length - 1) {
                    PokeArray s2 = new PokeArray();
                    s2.getContainList().addAll(getByNumber(2, new Poke(index.get(1) + 1, 1)));
//                    for(PokeArray a:pureList) {
//                        if (a.getSize() < 2) {
//                            s2.getContainList().add(new Poke(a.getOne().getNumber(),0));
//                        }
//                    }
                    solutions.add(s2);
                }
                PokeArray s3 = new PokeArray();
                s3.getContainList().add(new Poke(index.get(0), 1));
                s3.getContainList().add(new Poke(index.get(1), 1));
                solutions.add(s3);
                return solutions;
            case 3:
                PokeArray array = new PokeArray();
                for (PokeArray a : pureList) {
                    if (a.getSize() < 2) {
                        array.getContainList().add(new Poke(a.getOne().getNumber(), 1));
                    }
                }
                solutions.add(array);
                return solutions;
            default:
                return null;
        }


    }

    private List<PokeArray> getMax3(List<PokeArray> pureList) {
        List<PokeArray> solutions = new ArrayList<>();
        if (pureList.size() > 1) {
            for (PokeArray array : pureList) {
                PokeArray fixed = new PokeArray();
                //补少于3的牌
                if (array.getSize() < 3) {
                    fixed.getContainList().addAll(getByNumber(3 - array.getSize(), array.getOne()));
                }
                solutions.add(fixed);
            }
            return solutions;
        } else {
            int index = pureList.get(0).getOne().getNumber();
            if (index > 0) {
                PokeArray s1 = new PokeArray();
                s1.getContainList().addAll(getByNumber(3, new Poke(index - 1, 1)));
                solutions.add(s1);
            }
            if (index < Poke.pokeNumber.length - 1) {
                PokeArray s2 = new PokeArray();
                s2.getContainList().addAll(getByNumber(3, new Poke(index + 1, 1)));
                solutions.add(s2);
            }
            return solutions;
        }
    }


    //返回特殊数字（红心）的集合，当specialNum不是正常数字的时候（而是大小王），返回错误
    public static ArrayList<Poke> getSpecialPoke(int specialNum, List<Poke> group) {
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
    public static ArrayList<PokeArray> getPokeClear(int specialNum, List<Poke> group) {
        //先把特殊的红桃提取出来
        ArrayList<Poke> specialArray = getSpecialPoke(specialNum, group);

        ArrayList<PokeArray> arrays = new ArrayList<>();
        for (Poke poke : group) {
            //跳过特殊红桃牌
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
                    if (arr.getOne().getNumber() == poke.getNumber()) {
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
        //特殊红桃牌添加到最后
        if (specialArray != null && specialArray.size() != 0) {
            PokeArray a = new PokeArray();
            a.getContainList().addAll(specialArray);
            arrays.add(a);
        }
        return arrays;
    }

    //返回不带特殊红桃牌和大小王的列表
    public static List<PokeArray> getPurePoke(int specialNum, List<Poke> group) {
        List<PokeArray> purePoke = getPokeClear(specialNum, group);
        Poke lastPoke = purePoke.get(purePoke.size() - 1).getOne();
        //去掉特殊红桃牌
        if (lastPoke.getNumber() == specialNum
                && lastPoke.getPattern() == 0) {
            purePoke.remove(purePoke.size() - 1);
        }
        Iterator<PokeArray> iterable = purePoke.iterator();
        while (iterable.hasNext()) {
            PokeArray array = iterable.next();
            //判断大小王
            if (array.getOne().getNumber() == Poke.getSmallKing().getNumber()
                    || array.getOne().getNumber() == Poke.getBigKing().getNumber()) {
                iterable.remove();
            }
        }
        return purePoke;
    }

    //只判断 repeat个重复的牌，并返回所需的牌

    /**
     * @param repeat    重复数量
     * @param pureArray 必须传入不包含红桃和大小王的牌组
     * @return 返回所需牌组列表，可能是多种可能
     */
    public static List<PokeArray> getRepeatRequirement(int repeat, List<PokeArray> pureArray) {
        if (pureArray == null) {
            return null;
        }
        if (pureArray.size() != 1) {
            return null;
        } else {
            PokeArray onlyPokeArray = pureArray.get(0);
            int left = repeat - onlyPokeArray.getSize();
            if (left == 0) {
                return new ArrayList<>();
            } else {
                Poke[] pokes = new Poke[left];
                Poke templatePoke = onlyPokeArray.getOne();
                for (int i = 0; i < pokes.length; i++) {
                    pokes[i] = new Poke(templatePoke.getNumber(), templatePoke.getPattern());
                }
                PokeArray array = new PokeArray();
                array.getContainList().addAll(Arrays.asList(pokes));
                ArrayList<PokeArray> list = new ArrayList<>();
                list.add(array);
                return list;
            }
        }
    }

    /**
     * @param pureArray 不包括大小王和红桃
     * @return 返回顺子或者顺金缺的牌
     */
    public static List<PokeArray> getFlowRequirement(List<PokeArray> pureArray) {
        if (pureArray == null) {
            return null;
        }

        //当提供的牌中有相同的数字的时候，返回错误（即顺子中出现多个相同）
        //samePattern为相同花色
        boolean samePattern = true;
        int pattern = pureArray.get(0).getOne().getPattern();
        for (PokeArray pokeArray : pureArray) {
            if (pokeArray.getSize() != 1) {
                return null;
            }

            if (pattern != pokeArray.getOne().getPattern()) {
                samePattern = false;
            }
        }


        int size = pureArray.size();
        if (size > 2 && size < 6) {
            //获取牌的数字，并赋值到列表
            List<Integer> contains = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                contains.add(pureArray.get(i).getOne().getNumber());
            }
            List<Integer> fixedArray = fixArrays(contains);
            List<List<Integer>> finalArray = appendArrays(contains, 0, Poke.pokeNumber.length - 1);
            //null 加入错误     size=0为不需要追加的数组
            if (finalArray != null) {
                if (finalArray.size() == 0) {
                    List<PokeArray> resultArray = new ArrayList<>();
                    PokeArray a = new PokeArray();
                    PokeArray b = new PokeArray();
                    //如果是同花，返回2种结果
                    if (samePattern) {
                        //第一种返回和原花色一样的
                        a.getContainList().addAll(getPokeFromNum(fixedArray, true, pattern));
                        resultArray.add(a);
                    }
                    b.getContainList().addAll(getPokeFromNum(fixedArray, false, pattern));
                    resultArray.add(b);
                    return resultArray;

                } else {
                    List<PokeArray> resultArray = new ArrayList<>();
                    for (List<Integer> l : finalArray) {
                        l.addAll(fixedArray);
                        PokeArray a = new PokeArray();
                        PokeArray b = new PokeArray();
                        //如果是同花，返回2种结果
                        if (samePattern) {
                            //第一种返回和原花色一样的
                            a.getContainList().addAll(getPokeFromNum(l, true, pattern));
                            resultArray.add(a);
                        }
                        b.getContainList().addAll(getPokeFromNum(l, false, pattern));
                        resultArray.add(b);
                    }
                    return resultArray;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    //只补空缺
    public static List<Integer> fixArrays(List<Integer> list) {
        list.sort(Integer::compareTo);
        int difference = list.get(list.size() - 1) - list.get(0);
        if (difference + 1 == list.size()) {
            System.out.println("数组很健康，不需要修补。\n" + list.toString());
            return new ArrayList<>();
        } else {
            List<Integer> fixed = new ArrayList<>(difference + 1 - list.size());
            for (int i = list.get(0); i <= list.get(list.size() - 1); i++) {
                if (!list.contains(i)) {
                    fixed.add(i);
                }
            }
            list.addAll(fixed);
            list.sort(Integer::compareTo);
            System.out.println("修补成功，修补的数组为：" + fixed.toString());
            return fixed;
        }
    }

    public static List<List<Integer>> appendArrays(List<Integer> missEndArray, int min, int max) {
        int size = missEndArray.size();
        if (size < 6 && size > 2) {
            missEndArray.sort(Integer::compareTo);
            if (size == 5) {
                return new ArrayList<>();
            } else if (size == 4) {
                List<List<Integer>> solutions = new ArrayList<>();
                if (missEndArray.get(0) > min) {
                    List<Integer> s1 = new ArrayList<>();
                    s1.add(missEndArray.get(0) - 1);
                    solutions.add(s1);
                }
                if (missEndArray.get(missEndArray.size() - 1) < max) {
                    List<Integer> s2 = new ArrayList<>();
                    s2.add(missEndArray.get(missEndArray.size() - 1) + 1);
                    solutions.add(s2);
                }
                return solutions;
            } else {
                List<List<Integer>> solutions = new ArrayList<>();
                if (missEndArray.get(0) > min + 1) {
                    List<Integer> s1 = new ArrayList<>();
                    s1.add(missEndArray.get(0) - 2);
                    s1.add(missEndArray.get(0) - 1);
                    solutions.add(s1);
                }
                if (missEndArray.get(missEndArray.size() - 1) < max - 1) {
                    List<Integer> s2 = new ArrayList<>();
                    s2.add(missEndArray.get(missEndArray.size() - 1) + 2);
                    s2.add(missEndArray.get(missEndArray.size() - 1) + 1);
                    solutions.add(s2);
                }
                if (missEndArray.get(0) > min && missEndArray.get(missEndArray.size() - 1) < max) {
                    List<Integer> s3 = new ArrayList<>();
                    s3.add(missEndArray.get(0) - 1);
                    s3.add(missEndArray.get(missEndArray.size() - 1) + 1);
                    solutions.add(s3);
                }
                return solutions;
            }

        } else {
            return null;
        }

    }

    public static List<Poke> getPokeFromNum(List<Integer> numList, boolean samePattern, int pattern) {
        List<Poke> pokeList = new ArrayList<>();
        int p = (pattern + 1) % Poke.pokePattern.length;
        for (Integer i : numList) {
            //默认花色为1
            Poke poke = new Poke(i, samePattern ? pattern : p);
            pokeList.add(poke);
        }
        return pokeList;
    }


}

enum Type {
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
