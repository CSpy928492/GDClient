package com.cspy.component;

import com.alibaba.fastjson.JSONObject;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginFrame extends JFrame implements MouseListener {

    JButton loginBtn;
    JTextField usernameInput;
    JTextField passwordInput;



    public LoginFrame () {
        JPanel rootPanel = new JPanel();

        JLabel titleLabel = new JLabel("用户登录");

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("用户名");
        usernameInput = new JTextField();
        usernameInput.setColumns(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameInput);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("密码");
        passwordInput = new JPasswordField();
        passwordInput.setColumns(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);

        loginBtn = new JButton("登陆");
        loginBtn.addMouseListener(this);

        rootPanel.setLayout(new GridLayout(4,1));
        rootPanel.add(titleLabel);
        rootPanel.add(usernamePanel);
        rootPanel.add(passwordPanel);
        rootPanel.add(loginBtn);
        rootPanel.setPreferredSize(new Dimension(600,400));

        this.add(rootPanel);
        pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);




    }


    public static void main(String[] args) {
        new LoginFrame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == loginBtn) {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (username != null && username.length() != 0 && password != null && password.length() != 0) {
                try (Socket socket = new Socket("localhost", 9990)) {
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    JSONObject loginMsg = new JSONObject();
                    loginMsg.put("username",username);
                    loginMsg.put("password",password);
                    pw.println(loginMsg.toJSONString());
                    pw.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String returnMsg = reader.readLine();
                    System.out.println("return message:" + returnMsg);

                    reader.close();
                    pw.close();
                    socket.close();

                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println("输入错误");
            }
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
