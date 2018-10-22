package com.cspy;

import com.cspy.util.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ChooseDialog extends JFrame{
    PokeGroupPanel[] panels;
    boolean[] clicked;

    public ChooseDialog(List<Solution> solutions) {
        if (solutions.size() < 1) {
            return;
        }
        panels = new PokeGroupPanel[solutions.size()];
        clicked = new boolean[solutions.size()];


        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(solutions.size(),1));
        for(int i = 0; i < panels.length; i++) {
            panels[i] = new PokeGroupPanel(solutions.get(i).getPokes(),true);
            tempPanel.add(panels[i]);
            clicked[i] = false;
            int finalI = i;
            panels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setClicked(finalI);
                }
            });
        }
        Dimension size = panels[0].getPreferredSize();
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tempPanel));

        this.setPreferredSize(new Dimension(size.width + 30,size.height * 3));



    }

    public void setClicked(int x) {
        if (x < panels.length && x > -1) {
            for (int i = 0; i < clicked.length; i++) {
                if (i == x) {
                    clicked[i] = true;
                } else {
                    clicked[i] = false;
                }
            }
        }
    }


}
