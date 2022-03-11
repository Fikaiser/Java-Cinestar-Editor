/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import models.Genre;
import models.Login;
import models.Movie;
import models.Person;

/**
 *
 * @author RnaBo
 */
public interface Repository {
    
    public Optional<Login> getLogin(String name, String pass) throws Exception;
    
    public void registerLogin(String name, String pass) throws Exception;

    public void addMovie(Movie movie) throws Exception;

    public int addGenre(Genre genre) throws Exception;

    public void addMovieGenreRelation(int movieId, int genreId)  throws Exception;
 
    public int addPerson(Person person) throws Exception; 
 
    public void addMoviePersonRelation(int movieId, int personId, boolean b) throws Exception;
     
    public List<Movie> getMovies() throws Exception; 
 
    public List<Genre> getMovieGenres(int idmovie) throws Exception; 
     
    public List<Genre> getAllGenres() throws Exception; 
         
    public void setMoviePeople(Movie movie) throws Exception; 
 
    public List<Person> getAllPeople() throws Exception; 
    
     public void purgeDatabase() throws Exception;
     
    public Optional<Movie> selectMovie(int selectedMovieId) throws Exception; 
 
    public void deleteMovie(int idmovie) throws Exception; 
 
    public void updateMovie(Movie movie) throws Exception; 
 
    public Optional<Genre> getGenre(int genreId) throws Exception; 
 
    public void deleteGenre(int idgenre) throws Exception; 
 
    public void updateGenre(Genre genre) throws Exception; 
 
    public Optional<Person> getPerson(int personId) throws Exception; 
 
    public void deletePerson(int idperson) throws Exception; 
 
    public void updatePerson(Person person) throws Exception; 
        
        
    
    
    
}
