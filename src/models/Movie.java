/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author RnaBo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie {
    
    public static final DateTimeFormatter DB_DATE_FORMAT = DateTimeFormatter.ofPattern("uuuuMMdd");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    public static final DateTimeFormatter RS_DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    
     @XmlAttribute
    private int idmovie;

    
    
    private String title;
    
    @XmlJavaTypeAdapter(ScreeningDateAdapter.class)
    @XmlElement(name = "ScreeningDate")
    private LocalDate screeningdate;
    private String description;
    private int duration;
    private String picturepath;
    
    @XmlElementWrapper
    @XmlElement(name = "genre")
    private List<Genre> genres;
    
    @XmlElementWrapper
    @XmlElement(name = "person")
    private List<Person> actors;
    
    @XmlElementWrapper
    @XmlElement(name = "person")
    private List<Person> directors;
    
    public Movie() {
    }

    public Movie(int idmovie, String title, LocalDate screeningdate, String description, int duration, String picturepath) {
        this.idmovie = idmovie;
        this.title = title;
        this.screeningdate = screeningdate;
        this.description = description;
        this.duration = duration;
        this.picturepath = picturepath;
    }

    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getScreeningdate() {
        return screeningdate;
    }

    public void setScreeningdate(LocalDate screeningdate) {
        this.screeningdate = screeningdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> director) {
        this.directors = director;
    }

}
