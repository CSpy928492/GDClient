package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class Main extends JFrame {

    OtherPanel[] others;
    Dimension mainSize;
    HandPokePanel handPokePanel;


    public Main() {
        mainSize = new Dimension(1200, 900);
        this.setPreferredSize(mainSize);
        this.setLayout(new BorderLayout());

        Dimension p02 = new Dimension(80, 500);
        Dimension p1 = new Dimension(600, 100);
        Dimension p4 = new Dimension(700,220);
        others = new OtherPanel[3];

        others[0] = new OtherPanel(2, p02, 10);
        this.add(others[0], BorderLayout.WEST);
        others[1] = new OtherPanel(1, p1, 10);

        JPanel jp1 = new JPanel();
        jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp1.add(others[1]);
        this.add(jp1, BorderLayout.NORTH);

        others[2] = new OtherPanel(2, p02, 10);
        this.add(others[2], BorderLayout.EAST);


        JLabel centerLabel = new JLabel("center");
        this.add(centerLabel, BorderLayout.CENTER);

        List<Poke> pokes = Poke.getRandomPokes(1, 7);
        List<Poke> smallPokes = pokes.subList(0, 18);
        smallPokes.sort(Poke::compareTo);
        Collections.reverse(smallPokes);

        handPokePanel = new HandPokePanel(smallPokes, 3, p4, pokes);
        JPanel jp2 = new JPanel();
        jp2.setBackground(Color.WHITE);
        jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp2.add(handPokePanel);
        this.add(jp2, BorderLayout.SOUTH);


        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
