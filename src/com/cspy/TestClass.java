package com.cspy;

import com.cspy.util.Poke;
import com.cspy.util.PokeArray;
import com.cspy.util.PokeGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        List<Poke> pokes = new ArrayList<>();
        pokes.add(new Poke(8, 1));
        pokes.add(new Poke(11, 1));
        pokes.add(new Poke(12, 1));
//        pokes.add(new Poke(6, 1));
        pokes.add(new Poke(2, 0));
        pokes.add(new Poke(2, 0));


        PokeGroup pg = new PokeGroup(pokes);
        pg.analysisGroup(2);

//        int repeat = 6;
//        List<PokeArray> pureArray = PokeGroup.getPurePoke(2,pokes);
//        List<Poke> repeatRequirement = PokeGroup.getRepeatRequirement(repeat, pureArray);
//        System.out.println("需要" + repeat + "个重复的，当前牌组：");
//        for(Poke poke:pokes) {
//            System.out.println(poke);
//        }
//
//        System.out.println("需要牌组：");
//        for(Poke poke: repeatRequirement) {
//            System.out.println(poke);
//        }


    }

    public static void changeArray(int[] ints) {
        ints[0] = 9999;
        System.out.println("改变：" + Arrays.toString(ints));
    }



//    public static void fixFive(Integer... ints) {
//        if(ints.length > 2 && ints.length < 6) {
//            Arrays.sort(ints);
//            System.out.println("数字数组是：" + Arrays.toString(ints) +
//                    "\n长度为：" + ints.length +
//                    "\n可以组成" + ints[0] + "~" + ints[ints.length - 1] + "的数组");
//            int difference = ints[ints.length - 1] - ints[0];
//            if (difference > 1 && difference < 5) {
//                //不考虑相同的情况
//                if(ints.length == 5) {
//                    System.out.println("长度为5，不需要修补\n" +
//                            "数组为：" + Arrays.toString(ints));
//                } else {
//                    List<Integer> list = new ArrayList<>();
//                    list.addAll(Arrays.asList(ints));
//
//                    fixArrays(list);
//                    List<List<Integer>> lists = appendArrays(list,0,12);
//                    if (lists != null) {
//                        if (lists.size() == 0) {
//                            System.out.println("无需追加");
//                        } else {
//                            System.out.println("修补方案如下：");
//                            for (List l : lists) {
//                                System.out.println(l.toString());
//                            }
//                        }
//                    } else {
//                        System.out.println("修补完成，但是添加解决方案的时候发生错误");
//                    }
//
//                }
//
//            } else {
//                System.out.println("参数差距过大不能组成顺子");
//            }
//        } else {
//            System.out.println("参数数量不正确，必须为3~5个数字");
//        }
//
//    }
//
//    //只补空缺
//    public static List<Integer> fixArrays(List<Integer> list) {
//        list.sort(Integer::compareTo);
//        int difference = list.get(list.size() - 1) - list.get(0);
//        if(difference + 1 == list.size()) {
//            System.out.println("数组很健康，不需要修补。\n" + list.toString());
//        } else {
//            List<Integer> fixed = new ArrayList<>(difference + 1 - list.size());
//            for (int i = list.get(0); i <= list.get(list.size() - 1); i++) {
//                if (!list.contains(i)) {
//                    fixed.add(i);
//                }
//            }
//            list.addAll(fixed);
//            list.sort(Integer::compareTo);
//            System.out.println("修补成功，修补的数组为：" + fixed.toString());
//            System.out.println("原数组现在为：" + list.toString());
//            return fixed;
//        }
//        return null;
//    }

//    public static List<List<Integer>> appendArrays(List<Integer> missEndArray, int min, int max) {
//        int size = missEndArray.size();
//        if(size < 6 && size > 2) {
//            missEndArray.sort(Integer::compareTo);
//            if(size == 5) {
//                return new ArrayList<>();
//            } else if (size == 4){
//                List<List<Integer>> solutions = new ArrayList<>();
//                if (missEndArray.get(0) > min) {
//                    List<Integer> s1 = new ArrayList<>();
//                    s1.add(missEndArray.get(0) - 1);
//                    solutions.add(s1);
//                }
//                if (missEndArray.get(missEndArray.size() - 1) < max) {
//                    List<Integer> s2 = new ArrayList<>();
//                    s2.add(missEndArray.get(missEndArray.size() - 1) + 1);
//                    solutions.add(s2);
//                }
//                return solutions;
//            } else {
//                List<List<Integer>> solutions = new ArrayList<>();
//                if(missEndArray.get(0) > min + 1) {
//                    List<Integer> s1 = new ArrayList<>();
//                    s1.add(missEndArray.get(0) - 2);
//                    s1.add(missEndArray.get(0) - 1);
//                    solutions.add(s1);
//                }
//                if (missEndArray.get(missEndArray.size() - 1) < max - 1) {
//                    List<Integer> s2 = new ArrayList<>();
//                    s2.add(missEndArray.get(missEndArray.size() - 1) + 2);
//                    s2.add(missEndArray.get(missEndArray.size() - 1) + 1);
//                    solutions.add(s2);
//                }
//                if(missEndArray.get(0) > min && missEndArray.get(missEndArray.size() - 1) < max) {
//                    List<Integer> s3 = new ArrayList<>();
//                    s3.add(missEndArray.get(0) - 1);
//                    s3.add(missEndArray.get(missEndArray.size() - 1) + 1);
//                    solutions.add(s3);
//                }
//                return solutions;
//            }
//
//        } else {
//            return null;
//        }
//
//    }


//    public static boolean contains(int a,List<Integer> array) {
//        return array.contains(a);
//    }
}
