package com.cspy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
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

    int locationX,locationY;

    public PokePacks(List<PokePanel> pokes, PokePanel specialPanel) {
        setPreferredSize(new Dimension(400,400));
        setLayout(new FlowLayout(FlowLayout.CENTER));


        locationX = 100;
        locationY = -100;

        if (specialPanel != null) {
            this.specialPanel = specialPanel;
            pokes.add(1, specialPanel);
            pokes.remove(0);
            Collections.shuffle(pokes);
        }

        this.pokes = pokes;
        if (pokes.size() < 10) {
            toIndex = pokes.size();
        }
        showPokes = new LinkedList<>();
        showPokes.addAll(pokes.subList(fromIndex,toIndex));


        pokeSize = PokePanel.normalBackSize;
        System.out.println("0:" + pokeSize);

        jLayeredPane = new JLayeredPane();

        pokePaneSize = new Dimension(350,350);
        jLayeredPane.setPreferredSize(pokePaneSize);

        for (int i = showPokes.size() - 1, j = 0; i >= 0; i--, j++) {
            PokePanel pp = showPokes.get(i);
            pp.setBounds(locationX + j * gap, locationY + pokePaneSize.width - (j * gap) - pokeSize.height,
                    pokeSize.width, pokeSize.height);
            jLayeredPane.add(pp);
            jLayeredPane.moveToFront(pp);
        }

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

    boolean contains = false;
    private void showPokes() {
        if (showPokes.size() != 10 || showPokes.contains(specialPanel) || contains) {


            int offsetX = 0;
            int offsetY = 0;

            PokePanel lastPokePanel = showPokes.get(showPokes.size() - 1);
            lastPokePanel.setLocation(locationX,locationY);
            jLayeredPane.add(lastPokePanel);
            jLayeredPane.moveToBack(lastPokePanel);


            for (int i = showPokes.size() - 2, j = 1; i >= 0; i--, j++) {
                PokePanel pp = showPokes.get(i);
                pp.setLocation(locationX + j * gap, locationY + pokePaneSize.width - (j * gap) - pokeSize.height);
//            jLayeredPane.add(pp);
                jLayeredPane.moveToFront(pp);
            }


//            jLayeredPane.removeAll();
//            repaint();
//            revalidate();
//            for (int i = showPokes.size() - 1, j = 0; i >= 0; i--, j++) {
//                PokePanel pp = showPokes.get(i);
//                pp.setBounds(locationX + j * gap, locationY + pokePaneSize.width - (j * gap) - pokeSize.height,
//                        pokeSize.width, pokeSize.height);
//                jLayeredPane.add(pp);
//                jLayeredPane.moveToFront(pp);
//            }
//            if (showPokes.contains(specialPanel)) {
//                contains = true;
//            } else {
//                contains = false;
//            }
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

    public PokePanel removeTop() {
        PokePanel pp = null;
        if (++toIndex < pokes.size()) {
            showPokes.add(pokes.get(toIndex));
        } else {
            toIndex = pokes.size() - 1;
        }
        if (fromIndex < toIndex) {
            ++fromIndex;
            pp =  showPokes.remove(0);
        }
        showPokes();
        if (pp!= null) {
            jLayeredPane.remove(pp);
        }
        System.out.println("当前剩余" + showPokes.size() + "张");
        return pp;
    }

    private void refresh() {
        fromIndex = 0;
        toIndex = 10;
        if (pokes.size() < 10) {
            toIndex = pokes.size();
        }
        showPokes.clear();
        showPokes.addAll(pokes.subList(fromIndex,toIndex));
        showPokes();
    }
}
