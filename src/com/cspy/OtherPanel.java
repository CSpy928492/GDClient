package com.cspy;

import com.cspy.util.AddOrRemove;
import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class OtherPanel extends JPanel implements MouseListener, AddOrRemove {

    int remainPokes;
    int pokeGapWidth;
    ClockPanel clockPanel;
    JLabel userPanel;
    JLabel remainLabel;
    List<PokePanel> pokePanelList;
    Dimension pokeSize;


    int type;
    JLayeredPane jLayeredPane;


    public OtherPanel(int type, Dimension preferredSize, int remainPokes) {
        setPreferredSize(preferredSize);
        this.remainPokes = remainPokes;
        clockPanel = new ClockPanel("倒计时");
        remainLabel = new JLabel("还剩：" + remainPokes);
        addMouseListener(this);
        pokePanelList = new ArrayList<>();
        this.type = type;


        Dimension d = getPreferredSize();
        System.out.println("d=" + d);
        setLayout(new BorderLayout());

        JPanel littlePanel = new JPanel();


        switch (type) {
            case 1://水平
                pokeSize = new Dimension(d.height / 5 * 3, d.height);
                littlePanel.setLayout(new GridLayout(2, 1));
                littlePanel.add(clockPanel);
                littlePanel.add(remainLabel);
                littlePanel.setPreferredSize(new Dimension(d.height, d.height));
                this.add(littlePanel, BorderLayout.WEST);
                break;
            case 2://竖直
                pokeSize = new Dimension(d.width, d.width / 3 * 5);
                littlePanel.setLayout(new GridLayout(1, 2));
                littlePanel.add(clockPanel);
                littlePanel.add(remainLabel);
                littlePanel.setPreferredSize(new Dimension(d.width, d.width));
                this.add(littlePanel, BorderLayout.NORTH);
                break;
        }
        for (int i = 0; i < remainPokes; i++) {
            PokePanel pp = new PokePanel(null, pokeSize);
            pokePanelList.add(pp);
        }





        jLayeredPane = new JLayeredPane();
        this.add(jLayeredPane, BorderLayout.CENTER);
        refreshPanel();
    }


    private void resizeGap() {
        Dimension d = getPreferredSize();
        int pokeWidth = 0, pokeHeight = 0;
        int length = 0;
        if (remainPokes <= 1) {
            pokeGapWidth = 0;
            return;
        }

        switch (type) {
            case 1:
                length = d.width - d.height;
                pokeHeight = d.height;
                pokeWidth = pokeHeight / 5 * 3;
                if (pokeWidth > length) {
                    pokeGapWidth = 0;
                } else {
                    pokeGapWidth = (length - pokeWidth) / (remainPokes - 1);
                }
                if (pokeGapWidth > pokeSize.width / 3) {
                    pokeGapWidth = pokeSize.width / 3;
                }
                break;
            case 2:
                length = d.height - d.width;
                pokeWidth = d.width;
                pokeHeight = pokeWidth / 3 * 5;
                if (pokeHeight > length) {
                    pokeGapWidth = 0;
                } else {
                    pokeGapWidth = (length - pokeHeight) / (remainPokes - 1);
                }
                if (pokeGapWidth > pokeSize.height / 5) {
                    pokeGapWidth = pokeSize.height / 5;
                }
                break;
        }


    }


    public void addByNumber(int number) {
        for (int i = 0; i < number; i++) {
            pokePanelList.add(new PokePanel(null,pokeSize));
        }
        remainPokes = pokePanelList.size();
        refreshPanel();

    }

    public void removeByNumber(int number) {

        if (number > pokePanelList.size()) {
            return;
        }
        for (int i = 0;i < number; i++) {
            pokePanelList.remove(0);
        }
        remainPokes = pokePanelList.size();
        refreshPanel();

    }

    private void refreshPanel() {
        jLayeredPane.removeAll();
        repaint();
        revalidate();
        resizeGap();
        for (int i = 0; i < remainPokes; i++) {
            PokePanel pp = pokePanelList.get(i);
            switch (type) {
                case 1:
                    pp.setBounds(i * pokeGapWidth, 0, pokeSize.width, pokeSize.height);
                    break;
                case 2:
                    pp.setBounds(0, i * pokeGapWidth, pokeSize.width, pokeSize.height);
                    break;

            }
            jLayeredPane.add(pp);
            jLayeredPane.moveToFront(pp);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            addByNumber((int) (Math.random() * 2 + 1));
            return;
        }
        if (e.getButton() == 3) {
            removeByNumber((int) (Math.random() * 2 + 1));
            return;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

