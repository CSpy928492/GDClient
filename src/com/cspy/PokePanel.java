package com.cspy;

import com.cspy.util.Poke;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PokePanel extends JPanel {

    Poke poke;
    JLabel mainLabel;
    JLabel smallPatternPanel;
    JLabel bigPatternPanel;

    private Map<String, String> patternMap;

    {
        patternMap = new HashMap<>();
        patternMap.put("!", "红桃");
        patternMap.put("@", "方块");
        patternMap.put("#", "黑桃");
        patternMap.put("$", "黑花");
        patternMap.put("-", "小王");
        patternMap.put("+", "大王");
        patternMap.put("*","特殊");
        patternMap.put("=", "卡背");
    }


    public PokePanel(Poke poke, int width) {
        Dimension dimension = new Dimension(width, width / 9 * 16);
        this.setPreferredSize(dimension);
        this.poke = poke;
        initPanel();

    }

    private void initPanel() {
        JLayeredPane jLayeredPane = new JLayeredPane();


        System.out.println("P宽度:" + getPreferredSize().width + "\nP高度:" + getPreferredSize().height);
        System.out.println("宽度:" + getSize().width + "\n高度:" + getSize().height);


        if (poke != null) {
            if (poke.getNumber() < 13) {
                int preferredWidth = getPreferredSize().width;
                int preferredHeight = getPreferredSize().height;

                mainLabel = new JLabel(getIcon(getFileName(poke.getNumberShow(), null), getPreferredSize()));
                smallPatternPanel = new JLabel(getIcon(
                        getFileName(patternMap.get(poke.getPatternShow()), "小"),
                        new Dimension(preferredWidth, (int) (preferredHeight * 0.625))));
                if (poke.isSpecial() && poke.isChanged()) {
                    bigPatternPanel = new JLabel(getIcon(
                            getFileName(patternMap.get("*"),null),
                            new Dimension((preferredWidth / 3) * 2, preferredHeight / 3)));
                } else {
                    bigPatternPanel = new JLabel(getIcon(
                            getFileName(patternMap.get(poke.getPatternShow()), "大"),
                            new Dimension((preferredWidth / 3) * 2, preferredHeight / 3)));
                }

                setLayout(new BorderLayout());
                mainLabel.setBounds(0, 0, preferredWidth, preferredHeight);
                smallPatternPanel.setBounds(0, (int) (0.1875 * preferredHeight), preferredWidth, (int) (preferredHeight * 0.625));
                bigPatternPanel.setBounds(preferredWidth / 6, preferredHeight / 2 - (preferredWidth / 3), preferredWidth / 3 * 2, preferredWidth / 3 * 2);
                jLayeredPane.add(mainLabel);
                jLayeredPane.add(bigPatternPanel);
                jLayeredPane.add(smallPatternPanel);
                jLayeredPane.moveToBack(mainLabel);
                jLayeredPane.moveToFront(smallPatternPanel);
                jLayeredPane.moveToFront(bigPatternPanel);
                this.add(jLayeredPane);
            } else {
                mainLabel = new JLabel(getIcon(getFileName(patternMap.get(poke.getNumberShow()), null), getPreferredSize()));
                mainLabel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
                this.add(mainLabel);
            }
        } else {
            mainLabel = new JLabel(getIcon(getFileName("卡背", null), getPreferredSize()));
            this.add(mainLabel);
        }


    }

    public ImageIcon getIcon(String fileName, Dimension dimension) {
        ImageIcon originalIcon = new ImageIcon(fileName);
        System.out.println("原图片宽度:" + originalIcon.getIconWidth() + "\n原图片高度:" + originalIcon.getIconHeight());
        Image image = originalIcon.getImage();
        image = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_AREA_AVERAGING);
        ImageIcon icon = new ImageIcon(image);
        System.out.println("图片宽度:" + icon.getIconWidth() + "\n图片高度:" + icon.getIconHeight());
        return icon;
    }

    private String getFileName(String exactName, String extra) {
        if (extra == null) {
            extra = "";
        }
        return "image/" + exactName + extra + ".png";
    }


}
