package com.cspy.component;

import com.cspy.util.RoomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoomPanel extends JPanel {
    JLabel roomMessage;
    JTable allRooms;

    JButton refresh,witness,auto,create,join;


    public RoomPanel() {
        this.setLayout(new BorderLayout());

        MouseAdapter mouseAdapter = new MyMouseAdapter();

        roomMessage = new JLabel();
        setRoomMessage(0,50);
        this.add(roomMessage, BorderLayout.NORTH);


        Object[][] rooms = new Object[][]{
                {"1号房间","3/4","缺人"},
                {"2号房间","3/4","缺人"},
                {"3号房间","3/4","缺人"},
                {"4号房间","2/4","缺人"},
                {"5号房间","3/4","缺人"},
                {"6号房间","4/4","开始"},
                {"7号房间","3/4","缺人"}
        };
        JTable table = new JTable(new RoomTableModel(rooms));
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(400);
        table.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(500,500));
        this.add(jScrollPane);



        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        refresh = new JButton("刷新房间");
        refresh.addMouseListener(mouseAdapter);
        witness = new JButton("观战");
        witness.addMouseListener(mouseAdapter);
        join = new JButton("加入房间");
        join.addMouseListener(mouseAdapter);
        create = new JButton("创建房间");
        create.addMouseListener(mouseAdapter);
        auto = new JButton("自动加入");
        auto.addMouseListener(mouseAdapter);
        bottomPanel.add(refresh);
        bottomPanel.add(witness);
        bottomPanel.add(join);
        bottomPanel.add(create);
        bottomPanel.add(auto);

        this.add(bottomPanel, BorderLayout.SOUTH);

    }


    public void setRoomMessage(int now, int max) {
        this.roomMessage.setText("当前房间数：" + now + "\t最大房间数：" + max);
    }

    class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }



}
