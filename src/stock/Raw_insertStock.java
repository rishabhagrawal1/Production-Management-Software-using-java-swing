package stock;
import java.awt.Color;
import java.awt.event.WindowAdapter;
 import java.awt.event.WindowEvent;
 import java.awt.BorderLayout;
 import java.awt.Component;
 import java.awt.FlowLayout;
 import javax.swing.event.TableModelEvent;
 import javax.swing.event.TableModelListener;
 import javax.swing.table.DefaultTableCellRenderer;
 import javax.swing.table.TableColumn;
 import javax.swing.JButton;
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JScrollPane;
 import javax.swing.JTable;
 import javax.swing.JTextField;
 import javax.swing.UIManager;
 import javax.swing.*;
 import java.util.Collection;
 import java.util.Vector;
 import javax.swing.table.DefaultTableModel; 
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.text.*;

 public class Raw_insertStock extends JPanel {
 public static final String[] columnNames ={"product_id","company_name","quantity","date_of_purchaching", ""};

     protected JTable table;
    protected JButton butt;
     protected JScrollPane scroller;
     protected InteractiveTableModel tableModel;

     public Raw_insertStock() {
         
         initComponent();
     }

     public void initComponent() {
         tableModel = new InteractiveTableModel(columnNames);
         tableModel.addTableModelListener(new Raw_insertStock.InteractiveTableModelListener());
         table = new JTable();
         //table.getParent().setBackground(Color.black);
         table.setModel(tableModel);
         table.setSurrendersFocusOnKeystroke(true);
         if (!tableModel.hasEmptyRow()) {
             tableModel.addEmptyRow();
         }

         scroller = new javax.swing.JScrollPane(table);
         table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
         TableColumn hidden = table.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
         hidden.setMinWidth(2);
         hidden.setPreferredWidth(2);
         hidden.setMaxWidth(2);
         hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

    //     setLayout(new BorderLayout());
      //   add(scroller, BorderLayout.CENTER);
    //     setLayout(new GridLayout());
           scroller.setSize(500,500);
         add(scroller);
           butt= new JButton("update");
          butt.setSize(20,10);
         add(butt);
           butt.addActionListener(new java.awt.event.ActionListener() 
           {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
     }
             private void updateActionPerformed(java.awt.event.ActionEvent evt) {                                        
// TODO add your handling code here:
insertQuery(tableModel.getVector());
clearTable();

//initComponent();
    }
void insertQuery(Vector dataVector)
{
    
    
    int numrows = tableModel.getRowCount(); 
// javax.swing.JOptionPane.showMessageDialog(null," numof total field field    "+numrows);
    for(int i = numrows - 2; i >=0; i--)   {	
A_rawIns audioRecord = (A_rawIns)dataVector.get(i);
if(audioRecord.getPro()==""||audioRecord.getCom()==""||audioRecord.getQun()==""||audioRecord.getDate()=="")
{
   // javax.swing.JOptionPane.showMessageDialog(null," right   ");
 /*String query="insert into raw_stock values('"+audioRecord.getPro()+"','"+audioRecord.getCom()+"','"+audioRecord.getQun()+"',"+audioRecord.getDate()+")";
        int rs;
        
        
        try{
         rs=ConnectionService.update(query);
        if(rs==0)
            javax.swing.JOptionPane.showMessageDialog(null,"query of row :"+i+" is not inserted into databases due to wrong formate of any field");
          }
        catch(Exception e){};*/
javax.swing.JOptionPane.showMessageDialog(null,"query of row :"+(i+1)+" is not inserted into databases due to missing of some field");

}
else
{
   // javax.swing.JOptionPane.showMessageDialog(null,"query of row :"+i+" is not inserted into databases due to missing of some field");

String query="insert into raw_stock values('"+audioRecord.getPro()+"','"+audioRecord.getCom()+"','"+audioRecord.getQun()+"','"+audioRecord.getDate()+"')";
        int rs;
        
        
        try{
         rs=ConnectionService.update(query);
        if(rs==0)
            javax.swing.JOptionPane.showMessageDialog(null,"query of row :"+(i+1)+" is not inserted into databases due to wrong formate of any field");
          
        else
            javax.swing.JOptionPane.showMessageDialog(null,"query of row  :"+(i+1)+" is  inserted into databases successfully");
        }catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());};





}

}
 A_rawIns audioRecord = (A_rawIns)dataVector.get(dataVector.size() - 1);
}
public  void clearTable(){   
//TableModel model = this.getModel();    
int numrows = tableModel.getRowCount();   
 for(int i = numrows - 1; i >=0; i--)   {	
tableModel.removeRow(i);
}
 tableModel.addEmptyRow();

    }
     public void highlightLastRow(int row) {
         int lastrow = tableModel.getRowCount();
         if (row == lastrow - 1) {
             table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
         } else {
             table.setRowSelectionInterval(row + 1, row + 1);
         }

         table.setColumnSelectionInterval(0, 0);
     }
 

     class InteractiveRenderer extends DefaultTableCellRenderer {
         protected int interactiveColumn;

         public InteractiveRenderer(int interactiveColumn) {
             this.interactiveColumn = interactiveColumn;
         }

         public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
         {
             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             if (column == interactiveColumn && hasFocus) {
                 if ((Raw_insertStock.this.tableModel.getRowCount() - 1) == row &&
                    !Raw_insertStock.this.tableModel.hasEmptyRow())
                 {
                     Raw_insertStock.this.tableModel.addEmptyRow();
                 }

                 highlightLastRow(row);
             }

             return c;
         }
     }

    class InteractiveTableModelListener implements TableModelListener {
         public void tableChanged(TableModelEvent evt) {
             if (evt.getType() == TableModelEvent.UPDATE) {
                 int column = evt.getColumn();
                 int row = evt.getFirstRow();
                Object  name=table.getValueAt(row,column);
                 System.out.println("row: " + row + " column: " + column+"value:"+name.toString());
                 table.setColumnSelectionInterval(column + 1, column + 1);
                 table.setRowSelectionInterval(row, row);
             }
         }
     }

   /*  public static void main(String[] args) {
         try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
             
             JFrame frame = new JFrame("Interactive Form");
             frame.addWindowListener(new WindowAdapter() {
                 public void windowClosing(WindowEvent evt) {
                     System.exit(0);
                 }
             });
             frame.getContentPane().add(new Raw_insertStock());
             frame.pack();
             frame.setVisible(true);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }*/
 }
 class A_rawIns {
     protected String product_id;
     protected String company_name;
     protected String quantity;
     protected String date_of_purchaching;

     public A_rawIns() {
         product_id = "";
         company_name = "";
         quantity = "";
          date_of_purchaching= "";
     }

     public String getPro() {
         return product_id;
     }

     public void setCom(String title) {
         this.company_name = title;
     }

     public String getQun() {
         return quantity;
     }
   
     public String getDate() {
         return date_of_purchaching;
     }

     public void setPro(String artist) {
         this.product_id = artist;
     }

     public String getCom() {
         return company_name;
     }
      public void setQun(String artist) {
         this.quantity = artist;
     }

     public void setDate(String album) {
         this.date_of_purchaching = album;
     }
 }
 class InteractiveTableModel extends AbstractTableModel {
     public static final int PRODUCT_INDEX = 0;
     public static final int COMPANY_INDEX = 1;
     public static final int QUANTITY_INDEX = 2;
         public static final int DATE_INDEX = 3; 
    public static final int HIDDEN_INDEX = 4;

     protected String[] columnNames;
     protected Vector dataVector;

     public InteractiveTableModel(String[] columnNames) {
         this.columnNames = columnNames;
         dataVector = new Vector();
     }

     public String getColumnName(int column) {
         return columnNames[column];
     }
      public Vector getVector() {
         return dataVector;
     }

     public boolean isCellEditable(int row, int column) {
         if (column == HIDDEN_INDEX) return false;
         else return true;
     }

     public Class getColumnClass(int column) {
         switch (column) {
             case PRODUCT_INDEX:
             case COMPANY_INDEX:
             case QUANTITY_INDEX:
              case DATE_INDEX:
                return String.class;
             default:
                return Object.class;
         }
     }

     public Object getValueAt(int row, int column) {
         A_rawIns record = (A_rawIns)dataVector.get(row);
         switch (column) {
             case PRODUCT_INDEX:
                return record.getPro();
             case COMPANY_INDEX:
                return record.getCom();
             case QUANTITY_INDEX:
return record.getQun();
    case DATE_INDEX:
                return record.getDate();
             default:
                return new Object();
         }
     }

     public void setValueAt(Object value, int row, int column) {
         A_rawIns record = (A_rawIns)dataVector.get(row);
         switch (column) {
               case PRODUCT_INDEX:
                record.setPro((String)value);
                break;
             case COMPANY_INDEX:
                record.setCom((String)value);
                break;
             case QUANTITY_INDEX:
                record.setQun((String)value);
                break;
              case DATE_INDEX:

                record.setDate((String)value);
                break;
             default:
                System.out.println("invalid index");
         }
         fireTableCellUpdated(row, column);
     }

     public int getRowCount() {
         return dataVector.size();
     }

     public int getColumnCount() {
         return columnNames.length;
     }

     public boolean hasEmptyRow() {
         if (dataVector.size() == 0) return false;
         A_rawIns audioRecord = (A_rawIns)dataVector.get(dataVector.size() - 1);
         if (audioRecord.getPro().trim().equals("") &&
            audioRecord.getCom().trim().equals("") &&
              audioRecord.getDate().trim().equals("") &&
            audioRecord.getQun().trim().equals(""))
         {
            return true;
         }
         else return false;
     }

     public void addEmptyRow() {
         dataVector.add(new A_rawIns());
         fireTableRowsInserted(
            dataVector.size() - 1,
            dataVector.size() - 1);
     }
      public void removeRow(int i) {
        dataVector.removeElementAt(i);
        
     }
 }



