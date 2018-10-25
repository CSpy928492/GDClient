package com.cspy;

import com.cspy.util.Poke;
import com.cspy.util.PokeGroup;
import com.cspy.util.Solution;
import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ChooseDialog extends JDialog {
    PokeGroupPanel[] panels;

    Solution solution;

    public ChooseDialog(List<Solution> solutions, Solution sb, int specialNumber) {
        if (solutions.size() < 1) {
            solution = null;
            this.setVisible(false);
            return;
        }
        panels = new PokeGroupPanel[solutions.size()];

        int validCount = 0, index = -1;

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(solutions.size(),1));
        for(int i = 0; i < panels.length; i++) {
            Solution sa = solutions.get(i);
            boolean bigger = PokeGroup.compareSolution(sa,sb,specialNumber);
            System.out.println(sa+">" + sb + ":" + bigger);
            System.out.println("specialNumber " + specialNumber);
            panels[i] = new PokeGroupPanel(sa.getPokes(),bigger);
            if (bigger) {
                ++validCount;
                index = i;
            }
            tempPanel.add(panels[i]);
//            int finalI = i;
//            panels[i].addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    setClicked(finalI);
//                }
//            });
        }
        if (validCount == 0) {
            solution = null;
            this.setVisible(false);
        }
        if (validCount == 1) {
            solution = solutions.get(index);
            this.setVisible(false);
        }
        tempPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int y = e.getY() / (tempPanel.getHeight() / solutions.size());
                System.out.println("你选中了：" + y);
                System.out.println(panels[y].pokes);

                if (panels[y].isValid()) {
                    solution = solutions.get(y);
                    System.out.println("有效");
                    ChooseDialog.this.setVisible(false);
                } else {
                    System.out.println("无效");
                }



            }
        });

        Dimension size = panels[0].getPreferredSize();
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(tempPanel));

        this.setBounds(new Rectangle(size.width + 30,size.height * 3));
        System.out.println("dialog.size()=" + getPreferredSize());
        revalidate();



    }

    public Solution getSolution() {
        return solution;
    }




}
