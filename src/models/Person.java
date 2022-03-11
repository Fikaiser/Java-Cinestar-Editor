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
public class Person implements Comparable<Person> {
    
     @XmlAttribute
    private int idperson;
    
    private String firstname;
    
     private String lastname;

    public Person(int idperson, String firstname, String lastname) {
        this.idperson = idperson;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person() {
    }
    
   
    

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getIdperson() {
        return idperson;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return  firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object obj) {
        
        if((obj == null) || (getClass() != obj.getClass())){
        return false;
    } // end if
    else{
        Person p = (Person)obj;
        return idperson == p.idperson;
    } // end else
        
    }

    @Override
    public int compareTo(Person o) {
        return lastname.compareTo(o.lastname);
    }

    
    
    
}
