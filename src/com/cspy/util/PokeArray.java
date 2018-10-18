package com.cspy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokeArray {
    private ArrayList<Poke> containList;

    public PokeArray() {
        containList = new ArrayList<>();
    }

    public PokeArray(ArrayList<Poke> pokeList) {
        this.containList = pokeList;
    }

    public ArrayList<Poke> getContainList() {
        return containList;
    }



    public int getSize() {
        return containList.size();
    }

    public Poke getOne() {
        if(containList.size() != 0) {
            return containList.get(0);
        } else {
            return new Poke();
        }
    }

    public boolean add(Poke poke) {
        return containList.add(poke);
    }

    public boolean addAll(List<Poke> pokes) {
        return containList.addAll(pokes);
    }

    //PokeArray和List<Poke>比较
    public boolean compareArray(List<Poke> pokes) {

        boolean same = true;
        if (containList.size() != pokes.size()) {
            return false;
        } else {
            containList.sort(Poke::compareTo);
            Collections.reverse(containList);
            pokes.sort(Poke::compareTo);
            Collections.reverse(pokes);
            for (int i = 0; i < containList.size(); i++) {
                if (!containList.get(i).equal(pokes.get(i))) {
                    same = false;
                }
            }
            return same;
        }
    }

    public Poke get(int index) {
        return containList.get(index);
    }

    @Override
    public String toString() {
        return "<"+getOne().getNumber()+">" + containList.toString();
    }
}
