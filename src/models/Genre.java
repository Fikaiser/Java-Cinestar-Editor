/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author RnaBo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Genre implements Comparable<Genre> {
    
    @XmlAttribute
    private int idgenre;
    
    private String name;

    public Genre() {
    }

    
    
    
    public Genre(int idgenre, String name) {
        this.idgenre = idgenre;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

    public int getIdgenre() {
        return idgenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    @Override
    public String toString() {
        return name;
    }
    
     @Override
    public boolean equals(Object obj) {
        
        if((obj == null) || (getClass() != obj.getClass())){
        return false;
    } // end if
    else{
        Genre g = (Genre)obj;
        return name.equalsIgnoreCase(g.name);
    } // end else
        
    }

    

    @Override
    public int compareTo(Genre o) {
        return name.compareTo(o.name);
    }
    
    
    
}
