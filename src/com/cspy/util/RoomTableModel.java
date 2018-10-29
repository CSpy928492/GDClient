package com.cspy.util;

import javax.swing.table.AbstractTableModel;

public class RoomTableModel extends AbstractTableModel {

    private String[] columnNames = {"房间号","人数","状态"};   //"房间名"
    private Object[][] data;

    public RoomTableModel(Object[][] data) {
        this.data = data;
    }



    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
