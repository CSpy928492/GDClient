package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

public class PokePacks extends JPanel {
    JLayeredPane jLayeredPane;

    Dimension pokeSize;
    Dimension pokePaneSize;
    PokePanel specialPanel;

    List<PokePanel> pokes;

    List<PokePanel> showPokes;

    int gap = 4;

    int fromIndex = 0;
    int toIndex = 10;
    public PokePacks(List<PokePanel> pokes, PokePanel specialPanel) {
        setPreferredSize(new Dimension(400,400));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        this.specialPanel = specialPanel;
        pokes.add(1,specialPanel);
        pokes.remove(0);
        Collections.shuffle(pokes);

        this.pokes = pokes;
        if (pokes.size() < 10) {
            toIndex = pokes.size();
        }
        showPokes = pokes.subList(fromIndex,toIndex);


        pokeSize = PokePanel.normalBackSize;

        jLayeredPane = new JLayeredPane();

        pokePaneSize = new Dimension(350,350);
        jLayeredPane.setPreferredSize(pokePaneSize);

//        double gap = getGap();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    removeTop();
                } else {
                    refresh();
                }
            }
        });
        this.add(jLayeredPane);
        showPokes();

    }

    private void showPokes() {
        jLayeredPane.removeAll();
        repaint();
        revalidate();
        for (int i = showPokes.size() - 1,j = 0; i >= 0 ; i--,j ++) {
            PokePanel pp = showPokes.get(i);
            pp.setBounds(j * gap,pokePaneSize.width - (j * gap) - pokeSize.height,pokeSize.width,pokeSize.height);
            jLayeredPane.add(pp);
            jLayeredPane.moveToFront(pp);
        }
    }

//    public double getGap() {
//        Dimension pSize = jLayeredPane.getPreferredSize();
//        size = showPokes.size();
//
//        int gw = (pSize.width - pokeSize.width) / pokes.size();
//        if (pSize.width < pokeSize.width) {
//            gw = 0;
//        }
//        int gh = (pSize.width - pokeSize.height) / pokes.size();
//        if (pSize.height < pokeSize.height) {
//            gh = 0;
//        }
//        return gw<gh?gw:gh;
//    }

    public void removeTop() {
        fromIndex++;
        if (fromIndex > toIndex) {
            return;
        }
        if (toIndex < pokes.size()) {
            toIndex++;
        } else {
            toIndex = pokes.size();
        }
        showPokes = pokes.subList(fromIndex,toIndex);
        showPokes();
    }

    private void refresh() {
        fromIndex = 0;
        toIndex = 10;
        if (pokes.size() < 10) {
            toIndex = pokes.size();
        }
        showPokes = pokes.subList(fromIndex,toIndex);
        showPokes();
    }
}
