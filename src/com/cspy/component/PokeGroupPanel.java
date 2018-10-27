package com.cspy.component;

import com.cspy.component.PokePanel;
import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;

import java.util.Collections;
import java.util.List;

public class PokeGroupPanel extends JPanel {
    public List<Poke> pokes;
    private boolean valid;
    int gap = 20;
    Dimension pokeSize = PokePanel.normalBackSize;


    public PokeGroupPanel(List<Poke> pokes, boolean valid) {
        this.pokes = pokes;

//        Dimension dimension = new Dimension(PokePanel.normalBackSize.width * 6 +100,PokePanel.normalBackSize.height + 20);

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
            PokePanel pp = new PokePanel(poke, pokeSize);
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

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return pokes.toString();
    }
}
