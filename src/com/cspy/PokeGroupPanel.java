package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokeGroupPanel extends JPanel {
    List<Poke> pokes;
    boolean valid;
    int gap = 20;
    Dimension pokeSize = new Dimension(90, 150);


    public PokeGroupPanel(List<Poke> pokes, boolean valid) {
        this.pokes = pokes;
        int width = pokes.size() * pokeSize.width + (pokes.size() - 1) * gap;
        int height = pokeSize.height + gap * 2;
        Dimension size = new Dimension(width, height);
        this.setPreferredSize(size);
        System.out.println(size);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        pokes.sort(Poke::compareTo);
        Collections.reverse(pokes);

        for (int i  = 0; i < pokes.size(); i++) {
            Poke poke = pokes.get(i);
            PokePanel pp = new PokePanel(poke, pokeSize.width);
            pp.setBounds(gap + i * (pokeSize.width + gap), gap,
                    pokeSize.width, pokeSize.height);
            this.add(pp);
        }
        this.valid = valid;
        if (valid) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.BLACK);
        }

    }

}
