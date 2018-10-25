package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokePanel extends JPanel {

    private Poke poke;
    private JLabel mainLabel;
    private JLabel smallPatternPanel;
    private JLabel bigPatternPanel;
    private JLabel borderPanel;
    private Dimension dimension;

    static ImageIcon[] normalNumber;
    static ImageIcon[] normalNumberB;
    static ImageIcon[] normalNumberS;

    static ImageIcon[] redNumber;
    static ImageIcon[] redNumberB;
    static ImageIcon[] redNumberS;

    static ImageIcon[] wangIcons;
    static ImageIcon[] wangIconsB;
    static ImageIcon[] wangIconsS;

    static ImageIcon[] smallPattern;
    static ImageIcon[] smallPatternB;
    static ImageIcon[] smallPatternS;

    static ImageIcon[] bigPattern;
    static ImageIcon[] bigPatternB;
    static ImageIcon[] bigPatternS;

    static ImageIcon border;
    static ImageIcon borderB;
    static ImageIcon borderS;


    static ImageIcon back;
    static ImageIcon backB;
    static ImageIcon backS;

    public static Dimension normalPokeSize;
    public static Dimension normalBackSize;
    private static Map<String, String> patternMap;

    private static List<PokePanel> pokePanels;
    private static List<PokePanel> backPanels;

    //加载图片
    static {
        System.out.println("开始加载图片");
        patternMap = new HashMap<>();
        patternMap.put("!", "红桃");
        patternMap.put("@", "方块");
        patternMap.put("#", "黑桃");
        patternMap.put("$", "黑花");
        patternMap.put("*", "特殊");
        patternMap.put("=", "卡背");

        normalPokeSize = new Dimension(135, 240);
        normalBackSize = new Dimension(90, 160);

        Dimension smallPatternSizeB,smallPatternSizeS;
        smallPatternSizeB = new Dimension(normalPokeSize.width,normalPokeSize.width / 9 * 10);
        smallPatternSizeS = new Dimension(normalBackSize.width,normalBackSize.width / 9 * 10);
        Dimension bigPatternSizeB,bigPatternSizeS;
        bigPatternSizeB = new Dimension(normalPokeSize.width /3 * 2,normalPokeSize.width /3 * 2);
        bigPatternSizeS = new Dimension(normalBackSize.width /3 * 2,normalBackSize.width /3 * 2);


        normalNumber = new ImageIcon[Poke.pokeNumber.length];
        normalNumberB = new ImageIcon[Poke.pokeNumber.length];
        normalNumberS = new ImageIcon[Poke.pokeNumber.length];
        for (int i = 0; i < normalNumber.length; i++) {
            normalNumber[i] = new ImageIcon(getFileName(Poke.pokeNumber[i], null));
            normalNumberB[i] = getIcon(normalNumber[i], normalPokeSize);
            normalNumberS[i] = getIcon(normalNumber[i], normalBackSize);
        }
        redNumber = new ImageIcon[Poke.pokeNumber.length];
        redNumberB = new ImageIcon[Poke.pokeNumber.length];
        redNumberS = new ImageIcon[Poke.pokeNumber.length];
        for (int i = 0; i < redNumber.length; i++) {
            redNumber[i] = new ImageIcon(getFileName(Poke.pokeNumber[i], "r"));
            redNumberB[i] = getIcon(redNumber[i], normalPokeSize);
            redNumberS[i] = getIcon(redNumber[i], normalBackSize);

        }
        wangIcons = new ImageIcon[2];
        wangIconsB = new ImageIcon[2];
        wangIconsS = new ImageIcon[2];
        wangIcons[0] = new ImageIcon(getFileName("小王", null));
        wangIconsB[0] = getIcon(wangIcons[0], normalPokeSize);
        wangIconsS[0] = getIcon(wangIcons[0], normalBackSize);
        wangIcons[1] = new ImageIcon(getFileName("大王", null));
        wangIconsB[1] = getIcon(wangIcons[1], normalPokeSize);
        wangIconsS[1] = getIcon(wangIcons[1], normalBackSize);


        smallPattern = new ImageIcon[Poke.pokePattern.length];
        smallPatternB = new ImageIcon[Poke.pokePattern.length];
        smallPatternS = new ImageIcon[Poke.pokePattern.length];
        for (int i = 0; i < smallPattern.length; i++) {
            smallPattern[i] = new ImageIcon(getFileName(patternMap.get(Poke.pokePattern[i]), "小"));
            smallPatternB[i] = getIcon(smallPattern[i], smallPatternSizeB);
            smallPatternS[i] = getIcon(smallPattern[i], smallPatternSizeS);
        }

        bigPattern = new ImageIcon[Poke.pokePattern.length + 1];
        bigPatternB = new ImageIcon[Poke.pokePattern.length + 1];
        bigPatternS = new ImageIcon[Poke.pokePattern.length + 1];
        for (int i = 0; i < Poke.pokePattern.length; i++) {
            bigPattern[i] = new ImageIcon(getFileName(patternMap.get(Poke.pokePattern[i]), "大"));
            bigPatternB[i] = getIcon(bigPattern[i], bigPatternSizeB);
            bigPatternS[i] = getIcon(bigPattern[i], bigPatternSizeS);
        }
        bigPattern[Poke.pokePattern.length] = new ImageIcon(getFileName("特殊", null));
        bigPatternB[Poke.pokePattern.length] = getIcon(bigPattern[Poke.pokePattern.length], bigPatternSizeB);
        bigPatternS[Poke.pokePattern.length] = getIcon(bigPattern[Poke.pokePattern.length], bigPatternSizeS);

        border = new ImageIcon(getFileName("纸牌边框", null));
        borderB = getIcon(border, normalPokeSize);
        borderS = getIcon(border, normalBackSize);

        back = new ImageIcon(getFileName("卡背", null));
        backB = getIcon(back, normalPokeSize);
        backS = getIcon(back, normalBackSize);

        System.out.println("加载图片完成");

        System.out.println("开始加载牌组");

        pokePanels = new ArrayList<>();
        //临时设置
        List<Poke> pokes = Poke.getRandomPokes(2, 5);
        for (Poke p : pokes) {
            PokePanel pp = new PokePanel(p, normalPokeSize);
            pokePanels.add(pp);
        }

        backPanels = new ArrayList<>();
        for (int i = 0; i < Poke.NUM_OF_PACK * 2; i++) {
            backPanels.add(new PokePanel(null, normalBackSize));
        }

        System.out.println("加载随机牌组完成");

    }

    public static List<PokePanel> getRandomPokePanel() {
        if (pokePanels != null) {
            return pokePanels;
        } else {
            pokePanels = new ArrayList<>();
            List<Poke> pokes = Poke.getRandomPokes(2, 5);
            for (Poke p : pokes) {
                PokePanel pp = new PokePanel(p, normalPokeSize);
                pokePanels.add(pp);
            }
            return pokePanels;
        }

    }

    public static List<PokePanel> getBackPanel() {
        if (backPanels != null) {
            return backPanels;
        } else {
            backPanels = new ArrayList<>();
            for (int i = 0; i < Poke.NUM_OF_PACK * 2; i++) {
                backPanels.add(new PokePanel(null, normalBackSize));
            }
            return pokePanels;
        }

    }

    public PokePanel(Poke poke, Dimension dimension) {
//        Dimension dimension = new Dimension(width, width / 9 * 16);
//        System.out.println("卡片大小为：" + dimension.toString());
        this.dimension = dimension;
        this.setPreferredSize(dimension);
        this.poke = poke;
        initPanel();

    }


    public Poke getPoke() {
        return poke;
    }

    private void initPanel() {
        JLayeredPane jLayeredPane = new JLayeredPane();


//        System.out.println("P宽度:" + getPreferredSize().width + "\nP高度:" + getPreferredSize().height);
//        System.out.println("宽度:" + getSize().width + "\n高度:" + getSize().height);


        if (poke != null) {
            if (poke.getNumberWithSpecial() < 13) {
                boolean red = true;
                //普通牌
                if (poke.getPatternWithSpecial() > 1) {
                    red = false;
                }

                ImageIcon[] redNumber,normalNumber,smallPattern,bigPattern;
                ImageIcon border;
                if (dimension.equals(normalPokeSize)) {
                    redNumber = redNumberB;
                    normalNumber = normalNumberB;
                    smallPattern = smallPatternB;
                    bigPattern = bigPatternB;
                    border = borderB;
                } else {
                    redNumber = redNumberS;
                    normalNumber = normalNumberS;
                    smallPattern = smallPatternS;
                    bigPattern = bigPatternS;
                    border = borderS;
                }


                mainLabel = new JLabel(red ? redNumber[poke.getNumberWithSpecial()] : normalNumber[poke.getNumberWithSpecial()]);

                smallPatternPanel = new JLabel(smallPattern[poke.getPatternWithSpecial()]);
                if (poke.isSpecial() && poke.isChanged()) {
                    bigPatternPanel = new JLabel(bigPattern[Poke.pokePattern.length]);
                } else {
                    bigPatternPanel = new JLabel(bigPattern[poke.getPatternWithSpecial()]);
                }
                borderPanel = new JLabel(border);


                setLayout(new BorderLayout());
                mainLabel.setBounds(0, 0, dimension.width, dimension.height);
                smallPatternPanel.setBounds(0, (int) (0.1875 * dimension.height), dimension.width, (int) (dimension.height * 0.625));
                bigPatternPanel.setBounds(dimension.width / 6, dimension.height / 2 - (dimension.height / 3), dimension.width / 3 * 2, dimension.height / 3 * 2);
                borderPanel.setBounds(0, 0, dimension.width, dimension.height);
                jLayeredPane.add(mainLabel);
                jLayeredPane.add(bigPatternPanel);
                jLayeredPane.add(smallPatternPanel);
                jLayeredPane.add(borderPanel);
                jLayeredPane.moveToBack(mainLabel);
                jLayeredPane.moveToFront(smallPatternPanel);
                jLayeredPane.moveToFront(bigPatternPanel);
                jLayeredPane.moveToFront(borderPanel);
                this.setOpaque(false);
                this.add(jLayeredPane);

            } else {

                ImageIcon[] wangIcons;
                ImageIcon border;

                if (dimension == normalPokeSize) {
                    wangIcons = wangIconsB;
                    border = borderB;
                } else {
                    wangIcons = wangIconsS;
                    border = borderS;
                }
                //大小王
                setLayout(new BorderLayout());
                if (poke.getNumber() == 13) {
                    mainLabel = new JLabel(wangIcons[0]);
                } else {
                    mainLabel = new JLabel(wangIcons[1]);

                }
                mainLabel.setBounds(0, 0, dimension.width, dimension.height);
                borderPanel = new JLabel(border);
                borderPanel.setBounds(0, 0, dimension.width, dimension.height);
                jLayeredPane.add(mainLabel);
                jLayeredPane.add(borderPanel);
                jLayeredPane.moveToBack(mainLabel);
                jLayeredPane.moveToFront(borderPanel);
                this.setOpaque(false);
                this.add(jLayeredPane);
            }
        } else {

            ImageIcon back,border;
            if (dimension.equals(normalPokeSize)) {
                back = backB;
                border = borderB;
            } else {
                back = backS;
                border = borderS;
            }
            setLayout(new BorderLayout());
            mainLabel = new JLabel(back);
            mainLabel.setBounds(0, 0, dimension.width, dimension.height);
            borderPanel = new JLabel(border);
            borderPanel.setBounds(0, 0, dimension.width, dimension.height);
            jLayeredPane.add(mainLabel);
            jLayeredPane.add(borderPanel);
            jLayeredPane.moveToBack(mainLabel);
            jLayeredPane.moveToFront(borderPanel);
            this.setOpaque(false);
            this.add(jLayeredPane);
        }


    }

    public static ImageIcon getIcon(ImageIcon originalIcon, Dimension dimension) {
//        System.out.println("原图片宽度:" + originalIcon.getIconWidth() + "\n原图片高度:" + originalIcon.getIconHeight());
        Image image = originalIcon.getImage();
        Image aImage = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_AREA_AVERAGING);
        ImageIcon icon = new ImageIcon(aImage);
//        System.out.println("图片宽度:" + icon.getIconWidth() + "\n图片高度:" + icon.getIconHeight());
        return icon;
    }

    private static String getFileName(String exactName, String extra) {
        if (extra == null) {
            extra = "";
        }
        return "image/" + exactName + extra + ".png";
    }


}
