package com.cspy.util;

import java.util.ArrayList;
import java.util.Arrays;

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

    public int getNumber() {
        if(containList.size() != 0) {
            return containList.get(0).getNumber();
        } else {
            return -1;
        }
    }

    public int getSize() {
        return containList.size();
    }

    public Poke getOne() {
        return containList.get(0);
    }

    @Override
    public String toString() {
        return "<"+getNumber()+">" + containList.toString();
    }
}
