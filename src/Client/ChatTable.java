package Client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ChatTable extends AbstractTableModel {

    List<String>chatList=new ArrayList<>();



    @Override
    public String getColumnName(int column) {
        return "Your Commands";
    }

    @Override
    public int getRowCount() {
        return chatList.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return chatList.get(rowIndex);
            default:
                return null;
        }
    }

    public void addCommand(String command){
        this.chatList.add(command);
        fireTableRowsInserted(chatList.size() -1, chatList.size() -1);
    }
    public void clear(){
        this.chatList=new ArrayList<>();

    }
}
