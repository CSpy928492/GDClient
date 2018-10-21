package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class HandPokePanel extends JPanel implements MouseListener, MouseMotionListener {

    List<PokePanel> pokePanels;
    boolean[] clicked;
    int pokeGapWidth;
    List<PokePanel> selectedPanel;
    boolean selectValid;
    //鼠标点的相对位置
    int mx,my;
    boolean dragging = false;

    JLayeredPane jLayeredPane;

    public HandPokePanel(List<Poke> pokes, int specialNumber, int width) {
        if (pokes == null) {
            return;
        }
        pokePanels = new ArrayList<>();
        pokeGapWidth = width / (pokes.size() + 2);
        selectedPanel = new ArrayList<>();

        System.out.println("卡牌间隙为：" + pokeGapWidth);

        int[] pokeX = new int[pokes.size()];
        for (int i = 0; i < pokeX.length; i++) {
            pokeX[i] = i * pokeGapWidth;
        }

        for (int i = 0; i <pokes.size(); i++) {
            PokePanel pp = new PokePanel(pokes.get(i), pokeGapWidth * 3);
            pokePanels.add(pp);
            pp.setBounds(pokeX[i], pokeGapWidth * 2, pp.getPreferredSize().width, pp.getPreferredSize().height);
            pp.addMouseListener(this);
            pp.addMouseMotionListener(this);
        }
        this.setPreferredSize(new Dimension(width, pokeGapWidth * 7));
        setLayout(new BorderLayout());
        jLayeredPane = new JLayeredPane();

        initPanel(pokePanels);
    }

    //根据pokePanels初始化面板
    private void initPanel(List<PokePanel> pokePanels) {
        jLayeredPane.removeAll();
        repaint();
        revalidate();
        int[] pokeX = new int[pokePanels.size()];
        for (int i = 0; i < pokeX.length; i++) {
            pokeX[i] = i * pokeGapWidth;
        }
        clicked = new boolean[pokePanels.size()];
        for (int i = 0; i < clicked.length; i++) {
            clicked[i] = false;
        }
        for ( int i = 0; i < pokePanels.size(); i++) {
            PokePanel pp = pokePanels.get(i);
            pp.setBounds(pokeX[i], pokeGapWidth * 2, pp.getPreferredSize().width, pp.getPreferredSize().height);

            jLayeredPane.add(pp);
            jLayeredPane.moveToFront(pp);
        }
        this.add(jLayeredPane);
    }

    //改变选中状态
    private void changePokeByX(int x) {

        System.out.println(" 第" + x + "张");
        PokePanel pp = pokePanels.get(x);
        Dimension pSize = pp.getPreferredSize();
        //为true，选择List包含
        if (clicked[x]) {
            pp.setBounds(x * pokeGapWidth, pokeGapWidth * 2, pSize.width, pSize.height);
            selectedPanel.remove(pp);
            clicked[x] = false;
        } else {
            pp.setBounds(x * pokeGapWidth, 0, pSize.width, pSize.height);
            selectedPanel.add(pp);
            clicked[x] = true;
        }

    }


    //获取范围移动后的真实下标
    public int getRealIndex(int x) {
        int count = 0;
        for(int i = 0; i < x; i++) {
            if (!clicked[i]) {
                count++;
            }
        }
        System.out.println("realIndex: x=" + x + " count=" + count);
        return count;
    }

    public void insertIntoIndex(int x) {
        if (selectedPanel.size() != 0) {
//            System.out.println("before remove:" + pokePanels.toString());
            pokePanels.removeAll(selectedPanel);
//            System.out.println("before selected:" + selectedPanel.toString());
//            System.out.println("insertIntoIndex: selectedPanel.size=" +selectedPanel.size()
//                    + "pokePanels.size=" + pokePanels.size() );
            pokePanels.addAll(getRealIndex(x),selectedPanel);
//            System.out.println("after remove:" + pokePanels.toString());
            initPanel(pokePanels);
            clearSelected();
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {

//        System.out.println("click");
//        if (e.getY() > pokeGapWidth * 2) {
//            int x = pokePanels.indexOf(e.getSource());
//            changePokeByX(x);
//        }

    }


    @Override
    public void mousePressed(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        System.out.println("press");
        //点击牌的下半部
        if (e.getButton() != 3 && e.getY() > pokeGapWidth * 2) {
            selectValid = true;
            PokePanel pp = (PokePanel) e.getSource();
            addOrRemoveToSelect(pp);
        } else {
            selectValid = false;
        }

    }





    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("release");
        PokePanel draggedPanel = (PokePanel) e.getSource();


        if (e.getButton() == 3) {
            clearSelected();
            return;
        }
        if (e.getY() < pokeGapWidth * 2 && dragging ) {
            if (!selectedPanel.contains(draggedPanel)) {
                selectedPanel.add(draggedPanel);
                clicked[pokePanels.indexOf(draggedPanel)] = true;
            }
            int bx = draggedPanel.getLocation().x;
            int x = bx / pokeGapWidth + 1;
            if(bx < 0) {
                x = 0;
            }
            if (x > pokePanels.size()) {
                x = pokePanels.size();
            }
            insertIntoIndex(x);
            dragging = false;
        }
//        if (e.getY() > pokeGapWidth * 2) {
//            PokePanel pp = (PokePanel) e.getSource();
//            if (!selectedPanel.contains(pp)) {
//                selectedPanel.add(pp);
//            }


            refreshPanel();
            selectValid = false;

//        }


    }

    //刷新所有牌的位置
    private void refreshPanel() {
        for (int  x = 0; x <clicked.length; x++) {
            PokePanel pp = pokePanels.get(x);
            Dimension pSize = pp.getPreferredSize();

            if (!clicked[x]) {
                pp.setBounds(x * pokeGapWidth, pokeGapWidth * 2, pSize.width, pSize.height);
            } else {
                pp.setBounds(x * pokeGapWidth, 0, pSize.width, pSize.height);
            }
        }
        repaint();
        revalidate();
    }

    //清空选择
    private void clearSelected(){
        for (int i = 0; i <clicked.length;i++) {
            clicked[i] = false;
        }
        selectedPanel.clear();
        refreshPanel();
    }

    //改变状态的那个牌
    private void addOrRemoveToSelect(PokePanel pp) {
        if (!selectedPanel.contains(pp)) {
            selectedPanel.add(pp);
            clicked[pokePanels.indexOf(pp)] = true;
        } else {
            selectedPanel.remove(pp);
            clicked[pokePanels.indexOf(pp)]= false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (selectValid) {
            PokePanel pp = (PokePanel) e.getSource();
            addOrRemoveToSelect(pp);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        PokePanel draggedPanel = (PokePanel) e.getSource();
        if (e.getY() < pokeGapWidth * 2 &&
                (clicked[pokePanels.indexOf(draggedPanel)] || selectedPanel.size() == 0)) {
            jLayeredPane.moveToFront(draggedPanel);
            Dimension pSize = draggedPanel.getPreferredSize();
            int bx =draggedPanel.getLocation().x;
            int by = draggedPanel.getLocation().y;
//            System.out.println("bx:" + bx + " by:" +by);
            draggedPanel.setBounds(bx + e.getX() - mx,by + e.getY() - my,pSize.width,pSize.height);
            dragging = true;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
