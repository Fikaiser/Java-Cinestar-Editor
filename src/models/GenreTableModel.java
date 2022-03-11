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
public class GenreTableModel extends AbstractTableModel {

    
     private static final String[] COLUMN_NAMES = {"Id", "Name"};
    
    private List<Genre> genres;
    
     public GenreTableModel(List<Genre> genres) {
        this.genres = genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    
    @Override
    public int getRowCount() {
        return genres.size();
    }

    @Override
    public int getColumnCount() {
        return Genre.class.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         switch (columnIndex) {
            case 0:
                return genres.get(rowIndex).getIdgenre();
            case 1:
                return genres.get(rowIndex).getName();
            
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
