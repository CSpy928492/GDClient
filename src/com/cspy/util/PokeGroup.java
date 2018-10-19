package com.cspy.util;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class PokeGroup {
    Type type;
    int specialNumber;

    private List<Poke> group;

    public PokeGroup(List<Poke> group, int specialNumber) {
        this.group = group;
        this.specialNumber = specialNumber;
    }

    public List<Poke> getGroup() {
        return group;
    }

    public List<JSONObject> analysisGroup() {
        List<JSONObject> pokeList = new ArrayList<>();
        switch (group.size()) {
            case 1:
                JSONObject r1 = new JSONObject();
                r1.put("type", Type.ONE);
                r1.put("value", group.get(0).toString());
                pokeList.add(r1);
                return pokeList;
            case 2:
                return getRepeatRequirement(2);
            case 3:
                return getRepeatRequirement(3);
            case 4:
                List<JSONObject> r4 = new ArrayList<>();
                r4.add(WangBoom(getPokeClear()));
                r4.addAll(getRepeatRequirement(4));
                return r4;
            case 5:
                List<JSONObject> r5 = new ArrayList<>();
                r5.addAll(getThreeWithTwo(specialNumber));
                r5.addAll(getFlow());
                r5.addAll(getRepeatRequirement(5));
                return r5;
            case 6:
                List<JSONObject> r6 = new ArrayList<>();
                r6.addAll(getCoupleTripleResult());
                r6.addAll(getRepeatRequirement(6));
                return r6;
            default:

        }
        return null;
    }

    //融合pureArray到list中
    private List<Poke> mergePure(List<PokeArray> pureArray) {
        List<Poke> mergeArray = new ArrayList<>();
        for (PokeArray array : pureArray) {
            for (Poke poke : array.getContainList()) {
                mergeArray.add(poke.clone(specialNumber));
            }
        }
        return mergeArray;
    }

    //判断是否是王炸
    public JSONObject WangBoom(List<PokeArray> clearPoke) {
        JSONObject result = new JSONObject();
        if (clearPoke.size() == 2) {
            for (PokeArray array : clearPoke) {
                int arrayName = array.getOne().getNumber();
                if (!(arrayName == Poke.getSmallKing().getNumber()
                        || arrayName == Poke.getBigKing().getNumber())
                        || array.getSize() != 2) {
                    result.put("type", Type.INVALID);
                    return result;
                }
            }
            result.put("type", Type.WANG_BOOM);
            result.put("value", mergePure(clearPoke).toString());
            return result;
        }
        result.put("type", Type.INVALID);
        return result;
    }


    //判断三带二
    public List<JSONObject> getThreeWithTwo(int specialNum) {
        List<PokeArray> solutions = new ArrayList<>();
        List<Poke> specialList = getAllSpecial();
        List<PokeArray> pureList = getPurePoke();


        switch (pureList.size()) {
            case 1:
                //3 0   情况
                int fixNum = 3 - pureList.get(0).getSize();

                PokeArray a1 = new PokeArray();
                a1.getContainList().addAll(getByNumber(2, Poke.getSmallKing()));
                a1.getContainList().addAll(getByNumber(fixNum, pureList.get(0).getOne()));
                solutions.add(a1);
                PokeArray a2 = new PokeArray();
                a2.getContainList().addAll(getByNumber(2, Poke.getBigKing()));
                a2.getContainList().addAll(getByNumber(fixNum, pureList.get(0).getOne()));
                solutions.add(a2);
                if (fixNum == 0) {
                    PokeArray a3 = new PokeArray();
                    a3.getContainList().add(new Poke(specialNum, 0));
                    a3.getContainList().add(new Poke(specialNum, 0));
                    solutions.add(a3);
                }
                break;
            case 2:
                solutions = fixThreeWithTwo(pureList);
        }
        List<JSONObject> result = new ArrayList<>();
        if (solutions != null) {
            for (PokeArray array : solutions) {
                List<Poke> sl = cloneArray(specialList, specialNum);
                if (array.compareArray(sl)) {
                    JSONObject object = new JSONObject();
                    object.put("type", Type.THREE_WITH_TWO);
                    List<Poke> list = mergePure(pureList);
                    list.addAll(sl);
                    object.put("value", list.toString());
                    result.add(object);
                } else {
                    JSONObject invalidResult = new JSONObject();
                    invalidResult.put("type", Type.INVALID);
                    result.add(invalidResult);
                }
            }
        } else {
            JSONObject invalidResult = new JSONObject();
            invalidResult.put("type", Type.INVALID);
            result.add(invalidResult);
        }
        return result;
    }

    //复制List
    private List<Poke> cloneArray(List<Poke> pokeList, int specialNumber) {
        List<Poke> cloneArray = new ArrayList<>();
        for (Poke poke : pokeList) {
            cloneArray.add(poke.clone(specialNumber));
        }
        return cloneArray;
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
            int index = -1;
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
                    //1 2   情况
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
                    //2 2   情况
                    if (index == -1) {
                        PokeArray s3 = new PokeArray();
                        s3.getContainList().add(unfixedList.get(0).getOne());
                        fixed.add(s3);
                        PokeArray s4 = new PokeArray();
                        s4.getContainList().add(unfixedList.get(1).getOne());
                        fixed.add(s4);
                    } else {
                        //1 3   情况
                        PokeArray s5 = new PokeArray();
                        s5.addAll(getByNumber(1, unfixedList.get(index).getOne()));
                        fixed.add(s5);
                    }
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
        for (PokeArray array : cardList) {
            count += array.getSize();
        }
        return count;
    }

    //返回AAABBB AABBCC型
    public List<JSONObject> getCoupleTripleResult() {
        List<Poke> specialList = getAllSpecial();
        List<PokeArray> pureList = getPurePoke();
        List<JSONObject> result = new ArrayList<>();

        int cardNumber = getCardNumber(pureList);
        List<Integer> indexs = getIndexs(pureList);
        indexs.sort(Integer::compareTo);
        int difference = indexs.get(indexs.size() - 1) - indexs.get(0);
        if (cardNumber < 4 || cardNumber > 6 || (difference !=2 && difference!=1)) {
            JSONObject invalidResult = new JSONObject();
            invalidResult.put("type", Type.INVALID);
            result.add(invalidResult);
            return result;
        }
        if (pureList.size() < 4 && pureList.size() > 1) {
            int maxLength = 0;
            for (PokeArray array : pureList) {
                maxLength = array.getSize() > maxLength ? array.getSize() : maxLength;
            }
            List<PokeArray> solutions = new ArrayList<>();
            switch (maxLength) {
                case 2:
                    solutions = getMax2(pureList);
                    break;
                case 3:
                    solutions = getMax3(pureList);
                    break;
            }
            for (PokeArray pokeArray : solutions) {
                List<Poke> sl = cloneArray(specialList, specialNumber);
                if (pokeArray.compareArray(sl)) {
                    List<Poke> list = mergePure(pureList);
                    list.addAll(sl);
                    List<PokeArray> types = clearAllPoke(list);

                    JSONObject object = new JSONObject();
                    switch (types.size()) {
                        case 2:
                            object.put("type", Type.TWO_TRIPLE);
                            object.put("value", list.toString());
                            break;
                        case 3:
                            object.put("type", Type.THREE_COUPLE);
                            object.put("value", list.toString());
                            break;
                        default:
                            object.put("type", Type.INVALID);
                    }
                    result.add(object);
                } else {
                    JSONObject invalidResult = new JSONObject();
                    invalidResult.put("type", Type.INVALID);
                    result.add(invalidResult);
                }
            }
            return result;

        } else {
            JSONObject invalidResult = new JSONObject();
            invalidResult.put("type", Type.INVALID);
            result.add(invalidResult);
            return result;
        }
    }

    //整理分类所有牌
    public List<PokeArray> clearAllPoke(List<Poke> list) {
        List<PokeArray> arrays = new ArrayList<>();

        for (Poke poke : list) {
            if (arrays.size() == 0) {
                PokeArray a = new PokeArray();
                a.add(poke);
                arrays.add(a);
            } else {
                boolean inserted = false;
                //分类，相同的直接添加到PokeArray，不同的则创建PokeArray添加
                for (PokeArray arr : arrays) {
                    if (arr.getOne().getNumberWithSpecial() == poke.getNumberWithSpecial()) {
                        arr.add(poke);
                        inserted = true;
                    }
                }
                if (!inserted) {
                    PokeArray a = new PokeArray();
                    a.add(poke);
                    arrays.add(a);
                }
            }

        }
        return arrays;
    }

    //返回分类后牌的下标值
    private List<Integer> getIndexs(List<PokeArray> pureList) {
        List<Integer> indexs = new ArrayList<>();
        for(PokeArray pokeArray:pureList) {
            indexs.add(pokeArray.getOne().getNumber());
        }
        return indexs;
    }


    //数组最大长度为2  AAB+C+ AABBC+等
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
                    solutions.add(s1);
                }
                if (index.get(1) < Poke.pokeNumber.length - 1) {
                    PokeArray s2 = new PokeArray();
                    s2.getContainList().addAll(getByNumber(2, new Poke(index.get(1) + 1, 1)));
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
                return solutions;
        }


    }

    //数组最大长度为3  AAAB++等
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
    public List<Poke> getSpecialPoke() {
        ArrayList<Poke> list = null;
        if (specialNumber > -1 && specialNumber < Poke.pokeNumber.length) {
            list = new ArrayList();
            for (Poke poke : group) {
                if (poke.getNumber() == specialNumber
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

    //获取所有的特殊牌，包括红桃和大小王
    public List<Poke> getAllSpecial() {
        List<Poke> allSpecial = new ArrayList<>();
        for (Poke poke : group) {
            if (poke.equals(Poke.getSmallKing()) || poke.equals(Poke.getBigKing()
            ) || poke.equals(new Poke(specialNumber, 0))) {
                allSpecial.add(poke);
            }
        }
        return allSpecial;

    }

    //返回整理之后的牌，红桃放在最后
    public List<PokeArray> getPokeClear() {
        List<Poke> group = cloneArray(this.group,specialNumber);
        //先把特殊的红桃提取出来
        List<Poke> specialArray = getSpecialPoke();

        ArrayList<PokeArray> arrays = new ArrayList<>();
        for (Poke poke : group) {
            //跳过特殊红桃牌
            if (specialArray != null && specialArray.contains(poke)) {
                continue;
            }
            if (arrays.size() == 0) {
                PokeArray a = new PokeArray();
                a.add(poke);
                arrays.add(a);
            } else {
                boolean inserted = false;
                //分类，相同的直接添加到PokeArray，不同的则创建PokeArray添加
                for (PokeArray arr : arrays) {
                    if (arr.getOne().getNumber() == poke.getNumber()) {
                        arr.add(poke);
                        inserted = true;
                    }
                }
                if (!inserted) {
                    PokeArray a = new PokeArray();
                    a.add(poke);
                    arrays.add(a);
                }
            }
        }
        //特殊红桃牌添加到最后
        if (specialArray != null && specialArray.size() != 0) {
            PokeArray a = new PokeArray();
            a.addAll(specialArray);
            arrays.add(a);
        }
        return arrays;
    }

    //返回不带特殊红桃牌和大小王的列表
    public List<PokeArray> getPurePoke() {
        List<PokeArray> purePoke = getPokeClear();
        Poke lastPoke = purePoke.get(purePoke.size() - 1).getOne();
        //去掉特殊红桃牌
        if (lastPoke.getNumber() == specialNumber
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
     * @return 返回所需牌组列表，可能是多种可能
     */
    public List<JSONObject> getRepeatRequirement(int repeat) {
        List<JSONObject> result = new ArrayList<>();
        List<PokeArray> pureArray = getPurePoke();
        List<Poke> specialArray = getAllSpecial();
        if (pureArray == null || pureArray.size() > 1) {
            result.add(getInValidJSON());
            return result;
        }
        if (pureArray.size() == 0) {
            //纯特殊红桃，大小王，至少为2张，因为单张不在此函数中
            if (repeat == 2) {
                List<PokeArray> solutions = new ArrayList<>();
                PokeArray s1 = new PokeArray();
                s1.getContainList().addAll(getByNumber(2, new Poke(specialNumber, 1)));
                solutions.add(s1);

                PokeArray s2 = new PokeArray();
                s2.getContainList().addAll(getByNumber(2, Poke.getSmallKing()));
                solutions.add(s2);

                PokeArray s3 = new PokeArray();
                s3.getContainList().addAll(getByNumber(2, Poke.getBigKing()));
                solutions.add(s3);

                for (PokeArray array:solutions) {
                    List<Poke> sl = cloneArray(specialArray,specialNumber);
                    if (array.compareArray(sl)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type",Type.TWO);
                        jsonObject.put("value",sl.toString());
                        result.add(jsonObject);
                    }
                }
                return result;
            } else {
                result.add(getInValidJSON());
                return result;
            }
        } else {
            //包含普通数字的重复
            PokeArray onlyPokeArray = pureArray.get(0);
            int left = repeat - onlyPokeArray.getSize();
            if (left == 0) {
                JSONObject allNumber = getRepeatJSON(repeat,onlyPokeArray.getContainList());
                result.add(allNumber);
                return result;
            } else {
                Poke[] pokes = new Poke[left];
                if(left > 2) {
                    result.add(getInValidJSON());
                    return result;
                }
                Poke templatePoke = onlyPokeArray.getOne();
                for (int i = 0; i < pokes.length; i++) {
                    pokes[i] = new Poke(templatePoke.getNumber(), templatePoke.getPattern());
                }
                PokeArray array = new PokeArray();
                array.addAll(Arrays.asList(pokes));
                List<Poke> sl = cloneArray(specialArray,specialNumber);
                if (array.compareArray(sl)) {
                    sl.addAll(mergePure(pureArray));
                    JSONObject repeatResult = getRepeatJSON(repeat,sl);
                    result.add(repeatResult);
                } else {
                    result.add(getInValidJSON());
                }
                return result;
            }
        }
    }

    //返回重复牌的类型
    private JSONObject getRepeatJSON(int repeat, List<Poke> pokeList) {
        JSONObject result = new JSONObject();
        if (repeat == pokeList.size()) {
            switch (repeat) {
                case 2:
                    result.put("type",Type.TWO);
                    break;
                case 3:
                    result.put("type",Type.THREE);
                    break;
                case 4:
                    result.put("type",Type.BOOM);
                    break;
                case 5:
                    result.put("type",Type.FIVE_BOOM);
                    break;
                case 6:
                    result.put("type",Type.SIX_BOOM);
                    break;
                default:
                    return getInValidJSON();
            }
            result.put("value",pokeList.toString());
            return result;
        }
        return getInValidJSON();
    }

    /**
     * @return 返回顺子或者顺金缺的牌
     */
    public List<JSONObject> getFlow() {
        List<PokeArray> pureArray = getPurePoke();
        List<Poke> specialArray = getSpecialPoke();
        List<JSONObject> result = new ArrayList<>();

        if (pureArray == null) {
            result.add(getInValidJSON());
            return result;
        }


        //当提供的牌中有相同的数字的时候，返回错误（即顺子中出现多个相同）
        //samePattern为相同花色
        boolean samePattern = true;
        int pattern = pureArray.get(0).getOne().getPattern();
        for (PokeArray pokeArray : pureArray) {
            if (pokeArray.getSize() != 1) {
                result.add(getInValidJSON());
                return result;
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
                    //不需要追加，说明长度已经是5
                    List<PokeArray> solutions = new ArrayList<>();
                    PokeArray a = new PokeArray();
                    PokeArray b = new PokeArray();
                    //如果是同花，返回2种结果
                    if (samePattern) {
                        //第一种返回和原花色一样的
                        JSONObject ja = new JSONObject();
                        a.addAll(getPokeFromNum(fixedArray, true, pattern));
                        solutions.add(a);

                        List<Poke> specialList = cloneArray(getSpecialPoke(),specialNumber);
                        if(a.compareArray(specialList)) {
                            ja.put("type",Type.FIVE_COMBO_BOOM);
                            specialList.addAll(cloneArray(mergePure(pureArray),specialNumber));
                            ja.put("value",specialList.toString());
                            result.add(ja);
                        }

                    }
                    JSONObject jb = new JSONObject();
                    b.addAll(getPokeFromNum(fixedArray, false, pattern));
                    solutions.add(b);
                    List<Poke> specialList = cloneArray(getSpecialPoke(),specialNumber);
                    if(b.compareArray(specialList)) {
                        jb.put("type",Type.FIVE_COMBO);
                        specialList.addAll(cloneArray(mergePure(pureArray),specialNumber));
                        jb.put("value",specialList.toString());
                        result.add(jb);
                    }


                } else {
                    List<PokeArray> solutions = new ArrayList<>();
                    for (List<Integer> l : finalArray) {
                        l.addAll(fixedArray);
                        PokeArray a = new PokeArray();
                        PokeArray b = new PokeArray();
                        //如果是同花，返回2种结果
                        if (samePattern) {
                            //第一种返回和原花色一样的
                            a.getContainList().addAll(getPokeFromNum(l, true, pattern));
                            solutions.add(a);
                            result.add(getFlowResult(mergePure(pureArray),specialArray,a,true));
                        }
                        b.getContainList().addAll(getPokeFromNum(l, false, pattern));
                        solutions.add(b);
                        result.add(getFlowResult(mergePure(pureArray),specialArray,b,false));
                    }
                    return result;
                }
            }
        }
        result.add(getInValidJSON());
        return result;

    }

    //判断顺子，顺金
    private JSONObject getFlowResult(List<Poke> pureList, List<Poke> specialPoke, PokeArray pokeArray, boolean samePattern) {
        JSONObject jsonObject = null;
        List<Poke> sl = cloneArray(specialPoke,specialNumber);
        if(pokeArray.compareArray(sl)) {
            jsonObject = new JSONObject();
            sl.addAll(pureList);
            jsonObject.put("value",sl.toString());
            if(samePattern) {
                jsonObject.put("type",Type.FIVE_COMBO_BOOM);
            } else {
                jsonObject.put("type",Type.FIVE_COMBO);
            }
        }
        return jsonObject;
    }

    //获得无效JSON
    private JSONObject getInValidJSON() {
        JSONObject invalid = new JSONObject();
        invalid.put("type",Type.INVALID);
        return invalid;
    }

    //只补空缺
    public List<Integer> fixArrays(List<Integer> list) {
        list.sort(Integer::compareTo);
        int difference = list.get(list.size() - 1) - list.get(0);
        if (difference + 1 == list.size()) {
//            System.out.println("数组很健康，不需要修补。\n" + list.toString());
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
//            System.out.println("修补成功，修补的数组为：" + fixed.toString());
            return fixed;
        }
    }

    //为顺子补上头尾
    public List<List<Integer>> appendArrays(List<Integer> missEndArray, int min, int max) {
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

    //根据数字列表返回对应的牌
    public static List<Poke> getPokeFromNum(List<Integer> numList, boolean samePattern, int pattern) {
        List<Poke> pokeList = new ArrayList<>();
        int p = (pattern + 1) % Poke.pokePattern.length;
        for (Integer i : numList) {
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
