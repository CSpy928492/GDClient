package com.cspy;

import com.cspy.util.Poke;
import com.cspy.util.PokeGroup;
import com.cspy.util.Solution;
import com.cspy.util.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class HandPokePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    List<PokePanel> pokePanels;
    boolean[] clicked;
    int pokeGapWidth;
    List<PokePanel> selectedPanel;
    boolean selectValid;

    Dimension pokeSize;
    //鼠标点的相对位置
    int mx,my;
    boolean dragging = false;

    int specialNumber;

    JLayeredPane jLayeredPane;
    JButton ok,cancel;
    JButton tempRefresh,addOne,removeOne,addOneSpecial;
    BorderLayout borderLayout;

    List<Poke> allPokes;

    Solution last;

    public HandPokePanel(List<Poke> pokes, int specialNumber,Dimension dimension , List<Poke> allPokes) {
        if (pokes == null) {
            pokes = new ArrayList<>();
        }

//        setOpaque(false);
        this.setPreferredSize(dimension);
        int width = dimension.width;
        this.specialNumber = specialNumber;
        this.allPokes = allPokes;
        pokePanels = new ArrayList<>();
        selectedPanel = new ArrayList<>();

        pokeSize = PokePanel.normalPokeSize;
        pokeGapWidth = pokeSize.width / 3;
        System.out.println("after change pokeGapWidth=" + pokeGapWidth);

        borderLayout = new BorderLayout();

        ok = new JButton("确定");
        ok.addActionListener(this);
        cancel = new JButton("不要");
        cancel.addActionListener(this);

        tempRefresh = new JButton("刷新界面");
        tempRefresh.addActionListener(this);
        addOne = new JButton("增加一个");
        addOne.addActionListener(this);
        removeOne = new JButton("删除一个");
        removeOne.addActionListener(this);
        addOneSpecial = new JButton("增加一个特殊");
        addOneSpecial.addActionListener(this);

        last = new Solution(Type.INVALID,null);

//        System.out.println("卡牌间隙为：" + pokeGapWidth);

        if (pokes.size() != 0) {
            int[] pokeX = new int[pokes.size()];
            for (int i = 0; i < pokeX.length; i++) {
                pokeX[i] = i * pokeGapWidth;
            }

            for (int i = 0; i < pokes.size(); i++) {
                PokePanel pp = new PokePanel(pokes.get(i), pokeSize);
                pokePanels.add(pp);
                pp.setBounds(pokeX[i], pokeGapWidth * 2, pokeSize.width, pokeSize.height);
                pp.addMouseListener(this);
                pp.addMouseMotionListener(this);
            }
        }
//        this.setPreferredSize(new Dimension(width, pokeGapWidth * 8));
        System.out.println("width=" + width + "  gapWidth=" + pokeGapWidth + "  共" + pokePanels.size() + "张牌");
        jLayeredPane = new JLayeredPane();



        JPanel north = new JPanel();
        north.setLayout(new FlowLayout());
        north.setOpaque(false);
        north.add(ok);
        north.add(cancel);

        north.add(tempRefresh);
        north.add(addOne);
        north.add(removeOne);
        north.add(addOneSpecial);

        north.setPreferredSize(new Dimension(width,pokeGapWidth));
        setLayout(borderLayout);
        this.add(north,BorderLayout.NORTH);

        initPanel(pokePanels);
        jLayeredPane.setPreferredSize(new Dimension(width,pokeGapWidth * 7));

        this.add(jLayeredPane,BorderLayout.CENTER);

    }

    //根据pokePanels初始化面板
    private void initPanel(List<PokePanel> pokePanels) {
        if (pokeGapWidth != 0) {
            int width = pokeGapWidth * (pokePanels.size() + 2);
            if (width < pokeSize.width * 3) {
                width = pokeSize.width * 3;
            }
            this.setPreferredSize(new Dimension(width,pokeSize.height / 5 * 8));
        }
        jLayeredPane.removeAll();
        repaint();
        revalidate();
        if (pokePanels.size() != 0) {
            int[] pokeX = new int[pokePanels.size()];
            for (int i = 0; i < pokeX.length; i++) {
                pokeX[i] = i * pokeGapWidth;
            }
            clicked = new boolean[pokePanels.size()];
            for (int i = 0; i < clicked.length; i++) {
                clicked[i] = false;
            }
            selectedPanel.clear();
            for (int i = 0; i < pokePanels.size(); i++) {
                PokePanel pp = pokePanels.get(i);
                pp.setBounds(pokeX[i], pokeGapWidth * 2, pokeSize.width, pokeSize.height);
                jLayeredPane.add(pp);
                jLayeredPane.moveToFront(pp);
            }
        }

    }

//    private void resizeGap() {
//        pokeGapWidth = getPreferredSize().width / (pokePanels.size() + 2);
//        if (pokeGapWidth > pokeSize.width / 3) {
//            pokeGapWidth = pokeSize.width / 3;
//        }
//        System.out.println("pokeGapWidth=" + pokeGapWidth);
//    }

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
//        System.out.println("press");
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
//        System.out.println("release");
        PokePanel draggedPanel = (PokePanel) e.getSource();


        if (e.getButton() == 3) {
            clearSelected();
            return;
        }

        System.out.println("当前选中");
        for (PokePanel pp : selectedPanel) {
            System.out.println(pp.getPoke());
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
            jLayeredPane.moveToFront(pp);

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

    //获取选择的牌
    public List<Solution> getValidSolutions() {
        List<Poke> pokes = new ArrayList<>();
        for (PokePanel pp:selectedPanel) {
            pokes.add(pp.getPoke());
        }
        PokeGroup pokeGroup = new PokeGroup(pokes,specialNumber);
        List<Solution> resultList = pokeGroup.analysisGroup();
        resultList.removeIf(Solution::isInvalid);
        return resultList;
    }

    //获取牌数组
    public List<PokePanel> getPokePanels() {
        return this.getPokePanels();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton jButton = (JButton) e.getSource();
            if (jButton == ok) {
                List<Poke> pokes = new ArrayList<>();
                for (PokePanel pp:selectedPanel) {
                    pokes.add(pp.getPoke());
                }
                PokeGroup pokeGroup = new PokeGroup(pokes,specialNumber);
                List<Solution> resultList = pokeGroup.analysisGroup();
                System.out.println("以下是结果");
                for (Solution sl: resultList) {
                    System.out.println(sl);
                }
                resultList = getValidSolutions();

                if (resultList.size() == 0) {
                    System.out.println("出牌无效");
                } else {
                    List<Poke> temp = new ArrayList<>();
//                    temp.add(new Poke(7,2,false));
//                    Solution sb = new Solution(Type.ONE,temp);
                    ChooseDialog dialog = new ChooseDialog(resultList,last,specialNumber);
                    dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    dialog.setVisible(true);
                    Solution s = dialog.getSolution();

                    if (s != null ) {
                        System.out.println("已选择的是:" + s);
                        last = s;
                        System.out.println("当前last:" + last);
                    }
                }




                return;


            }
            if (jButton == cancel) {
                System.out.println("本回合过");
                clearSelected();
                return;

            }
            if (jButton == tempRefresh) {
                pokePanels.clear();
                Collections.shuffle(allPokes);
                List<Poke> pokes = allPokes.subList(1,18);
                pokes.sort(Poke::compareTo);
                Collections.reverse(pokes);
                for (Poke poke:pokes) {
                    PokePanel pokePanel = new PokePanel(poke,pokeSize);
                    pokePanel.addMouseMotionListener(this);
                    pokePanel.addMouseListener(this);
                    pokePanels.add(pokePanel);
                }
                initPanel(pokePanels);
                return;
            }
            if (jButton == addOne) {
                PokePanel pokePanel = new PokePanel(allPokes.get((int) (Math.random() * allPokes.size())),pokeSize);
                pokePanel.addMouseMotionListener(this);
                pokePanel.addMouseListener(this);
                pokePanels.add(pokePanel);
                initPanel(pokePanels);
                return;
            }
            if (jButton == removeOne) {
                if (pokePanels.size() != 0) {
                    pokePanels.remove(pokePanels.get((int) (Math.random() * pokePanels.size())));
                    initPanel(pokePanels);
                }
                return;
            }
            if (jButton == addOneSpecial) {
                PokePanel pokePanel = new PokePanel(new Poke(specialNumber,0,true),pokeSize);
                pokePanel.addMouseMotionListener(this);
                pokePanel.addMouseListener(this);
                pokePanels.add(pokePanel);
                initPanel(pokePanels);
                return;
            }
        }

    }



}
