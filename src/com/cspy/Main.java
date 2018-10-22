package com.cspy;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        this.setPreferredSize(new Dimension(1600,800));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
