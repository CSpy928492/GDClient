package com.cspy.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cspy.ClientManager;
import com.cspy.tools.HttpConnect;
import com.cspy.util.RoomTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class RoomPanel extends JPanel {
    JLabel roomMessage;
    RoomTableModel model;
    JTable table;


    JButton refresh,witness,auto,create,join;


    public RoomPanel() {
        this.setLayout(new BorderLayout());

        MyMouseAdapter mouseAdapter = new MyMouseAdapter();

        roomMessage = new JLabel();
        setRoomMessage(0,50);
        this.add(roomMessage, BorderLayout.NORTH);


//        Object[][] rooms = new Object[][]{
//                {"1号房间","3/4","缺人"},
//                {"2号房间","3/4","缺人"},
//                {"3号房间","3/4","缺人"},
//                {"4号房间","2/4","缺人"},
//                {"5号房间","3/4","缺人"},
//                {"6号房间","4/4","开始"},
//                {"7号房间","3/4","缺人"}
//        };

        Object[][] rooms = getRoomList();


        model = new RoomTableModel(rooms);
        table = new JTable(model);
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
            if (e.getSource() == refresh) {

                model = new RoomTableModel(getRoomList());
                table.setModel(model);
                table.validate();
                table.updateUI();
//                model.fireTableDataChanged();
                System.out.println("表格刷新成功");
                return;
            }

            if (e.getSource() == create) {
                createRoom();
                return;
            }

            if (e.getSource() == join) {
                Object roomNumber = table.getValueAt(table.getSelectedRow(), 0);
                System.out.println("准备加入" + roomNumber +"号房间");
                joinRoom((Integer) roomNumber);
                return;
            }

            if (e.getSource() == auto) {
                autoJoinRoom();
                return;

            }
        }
    }

    public Object[][] getRoomList() {
        Object[][] rooms = null;
        Map<String, String> params = new HashMap<>();
        params.put("token", ClientManager.clientToken);
        params.put("type", "getRoom");

        String resultString = HttpConnect.postConnect("http://localhost:8080/rooms",params);
        System.out.println("result is" + resultString);
        JSONObject json = JSONObject.parseObject(resultString);
        if (json.get("state").equals(0)) {
            System.out.println("通信成功");
            JSONArray array = json.getJSONArray("rooms");

            int roomCount = array.size();
            rooms = new Object[roomCount][];

            for (int i = 0; i < roomCount; i++) {
                JSONObject room = array.getJSONObject(i);
                rooms[i] = new Object[3];
                rooms[i][0] = room.get("roomNumber");
                JSONArray playList = room.getJSONArray("players");
                rooms[i][1] = playList.size();
                rooms[i][2] = room.get("roomState");
            }

        }
        return rooms;
    }

    private void createRoom() {
        Map<String, String> params = new HashMap<>();
        params.put("token", ClientManager.clientToken);
        params.put("type", "createRoom");

        String resultString = HttpConnect.postConnect("http://localhost:8080/rooms",params);
        System.out.println("result is" + resultString);
    }

    private void joinRoom(int roomNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("token", ClientManager.clientToken);
        params.put("type", "joinRoom");
        params.put("roomNumber", String.valueOf(roomNumber));

        String resultString = HttpConnect.postConnect("http://localhost:8080/rooms",params);
        System.out.println("result is" + resultString);
    }

    private void autoJoinRoom() {
        Map<String, String> params = new HashMap<>();
        params.put("token", ClientManager.clientToken);
        params.put("type", "autoJoinRoom");

        String resultString = HttpConnect.postConnect("http://localhost:8080/rooms",params);
        System.out.println("result is" + resultString);
    }



}
