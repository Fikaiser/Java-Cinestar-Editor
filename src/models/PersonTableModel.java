/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author RnaBo
 */
public class PersonTableModel extends AbstractTableModel {

     private static final String[] COLUMN_NAMES = {"Id", "Firstname", "Lastname"};
    
    private List<Person> people;

    public PersonTableModel(List<Person> people) {
        this.people = people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return people.size();
    }

    @Override
    public int getColumnCount() {
        return Person.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return people.get(rowIndex).getIdperson();
            case 1:
                return people.get(rowIndex).getFirstname();
            case 2:
                return people.get(rowIndex).getLastname();
            default:
                throw new RuntimeException("No such column");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); 
    }
    
}
