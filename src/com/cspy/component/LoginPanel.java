package com.cspy.component;

import com.cspy.ClientManager;
import com.cspy.tools.HttpConnect;
import com.cspy.util.MyTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends JPanel implements MouseListener {

    JButton loginBtn;
    JTextField usernameInput;
    JTextField passwordInput;

    String token = "";

    ClientManager parentPanel;



    public LoginPanel() {


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

        this.setLayout(new GridLayout(4,1));
        this.add(titleLabel);
        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(loginBtn);
        this.setPreferredSize(new Dimension(600,400));

//        this.add(rootPanel);
//        pack();
//        this.setVisible(true);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setResizable(false);




    }




    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == loginBtn) {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

//            username = "12";
//            password = "1";
            if (MyTools.validString(username) && MyTools.validString(password)) {

                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                String result = HttpConnect.postConnect("http://localhost:8080/login",params);
                System.out.println("结果为：" + result);

                if (result.equals("invalid")) {
                    System.out.println("登陆错误，请重试");
                } else {
                    this.token = result;
                    this.setVisible(false);
                    parentPanel = (ClientManager) SwingUtilities.getRoot(loginBtn);
                    parentPanel.setToken(token);

                }



//                try (Socket socket = new Socket("localhost", 9990)) {
//                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
//                    JSONObject loginMsg = new JSONObject();
//                    loginMsg.put("username",username);
//                    loginMsg.put("password",password);
//                    pw.println(loginMsg.toJSONString());
//                    pw.flush();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String returnMsg = reader.readLine();
//                    System.out.println("return message:" + returnMsg);
//
//                    reader.close();
//                    pw.close();
//                    socket.close();
//
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
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

    public String getToken() {
        return token;
    }
}
