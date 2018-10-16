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

    @Override
    public String toString() {
        return "<"+getOne().getNumber()+">" + containList.toString();
    }
}
