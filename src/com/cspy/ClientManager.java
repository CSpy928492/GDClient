package com.cspy;



import com.cspy.component.GamePanel;
import com.cspy.component.LoginPanel;
import com.cspy.component.RoomPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientManager extends JFrame implements ActionListener {

    public static String clientToken;
    LoginPanel loginPanel;
    RoomPanel roomPanel;
    GamePanel gamePanel;


    public ClientManager() {

        loginPanel = new LoginPanel();


        this.add(loginPanel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new ClientManager();
    }

    public void setToken(String token) {
        this.clientToken = token;

        System.out.println("token is" + clientToken);
        switchToRoomPanel();
    }

    private void switchToRoomPanel() {
        if(roomPanel == null) {
            roomPanel = new RoomPanel();
        }
        this.add(roomPanel);
        this.pack();
    }

    private void switchToGame() {
        if (gamePanel == null) {
            gamePanel = new GamePanel();
        }
        this.add(gamePanel);
        this.pack();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginPanel) {
            System.out.println("login消失了");
        }
    }
}
