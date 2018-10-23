package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends JFrame {

    OtherPanel[] others;
    Dimension mainSize;
    HandPokePanel handPokePanel;

    Dimension pokeSize;
    JLabel centerLabel;


    public Main() {
        mainSize = new Dimension(1200, 1000);

        pokeSize = new Dimension(90,160);
        this.setPreferredSize(mainSize);
        this.setLayout(new BorderLayout());

        Dimension p02 = new Dimension(80, 450);
        Dimension p1 = new Dimension(600, 100);
        Dimension p4 = new Dimension(700,400);
        others = new OtherPanel[3];

        System.out.println("初始化面板");
        others[0] = new OtherPanel(2, p02, 10);
        System.out.println("others0 完成");
        others[2] = new OtherPanel(2, p02, 10);
        System.out.println("others2 完成");


        this.add(others[0], BorderLayout.WEST);
        others[1] = new OtherPanel(1, p1, 10);
        System.out.println("others1 完成");


        JPanel jp1 = new JPanel();
        jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp1.add(others[1]);
        this.add(jp1, BorderLayout.NORTH);

        this.add(others[2], BorderLayout.EAST);


        centerLabel = new JLabel("center");
        centerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("点击");
                start();
            }
        });
        this.add(centerLabel, BorderLayout.CENTER);

        List<Poke> pokes = Poke.getRandomPokes(1, 7);
        List<Poke> smallPokes = pokes.subList(0, 18);
        smallPokes.sort(Poke::compareTo);
        Collections.reverse(smallPokes);

        System.out.println("初始化手牌面板");
        handPokePanel = new HandPokePanel(null, 3, p4, pokes);
        JPanel jp2 = new JPanel();
        jp2.setBackground(Color.WHITE);
        jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp2.add(handPokePanel);
        this.add(jp2, BorderLayout.SOUTH);


        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void start() {
        List<Poke> pokes = Poke.getRandomPokes(2,2);
        List<PokePanel> allPanels = new ArrayList<>();
        for (int i = 0;i <pokes.size();i++) {
            PokePanel pp = new PokePanel(pokes.get(i),pokeSize.width);
            allPanels.add(pp);
        }

        PokePacks pokePacks = new PokePacks(allPanels,null);
//        this.getLayout().removeLayoutComponent();
        this.remove(centerLabel);
        this.add(pokePacks,BorderLayout.CENTER);
        revalidate();
        System.out.println("start完成");
    }

    public static void main(String[] args) {
        new Main();
    }
}
