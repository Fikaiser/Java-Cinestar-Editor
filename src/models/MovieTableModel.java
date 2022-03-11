/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author RnaBo
 */
public class MovieTableModel extends AbstractTableModel {
    
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Premiere", "Duration"};
    
    private List<Movie> movies;
    
     public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
     
     

    @Override
    public int getRowCount() {
        return movies.size();
    }


    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getIdmovie();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getScreeningdate().format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
            case 3:
                return movies.get(rowIndex).getDuration();

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
