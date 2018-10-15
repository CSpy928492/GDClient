package com.cspy;

import com.cspy.util.Poke;
import com.cspy.util.PokeArray;
import com.cspy.util.PokeGroup;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        List<Poke> pokes = new ArrayList<>();
        pokes.add(new Poke(3,2));
        pokes.add(new Poke(2,1));
        pokes.add(new Poke(4,2));
        pokes.add(new Poke(5,2));
        pokes.add(new Poke(3,1));
        pokes.add(new Poke(3,0));
        pokes.add(new Poke(3,0));




        PokeGroup group = new PokeGroup(pokes);
        for (PokeArray array:group.getPokeClear(3)) {
            System.out.println(array);
        }
//        for(Poke poke: pokes) {
//            System.out.println(poke);
//        }


    }
}
