/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.sql;

import dal.Repository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import models.Genre;
import models.Login;
import models.Movie;
import models.Person;

/**
 *
 * @author RnaBo
 */
public class SqlRepo implements Repository {
    
    
    private static final String USERNAME = "Username";
    private static final String ADMIN = "IsAdmin";
    
    private static final String MOVIEID = "IDMovie";
    private static final String TITLE = "Title";
    private static final String DESC = "MovieDescription";
    private static final String DATE = "ScreeningDate";
    private static final String DURATION = "Duration";
    private static final String PIC_PATH = "PicturePath";
    private static final String GENRE_NAME = "Name";
    private static final String FIRSTNAME = "Firstname";
    private static final String LASTNAME = "Lastname";
    
   
    
    
    private static final String GETLOGIN = "{ CALL getLogin (?,?) }";
    private static final String REGISTER = "{ CALL registerLogin (?,?) }";
    private static final String ADD_MOVIE = "{ CALL addMovie (?,?,?,?,?,?) }";
    private static final String ADD_GENRE = "{ CALL addGenre (?,?) }";
    private static final String ADD_MGRELATION = "{ CALL addMovieGenreRelation (?,?) }";
    private static final String ADD_PERSON = "{ CALL addPerson (?,?,?) }";
    private static final String ADD_MPRELATION = "{ CALL addMoviePersonRelation (?,?,?) }";
    private static final String PURGE = "{ CALL Purge }";
    private static final String SELECT_MOVIES = "{ CALL getMovies }";
    private static final String GET_MOVIE_GENRES = "{ CALL getMovieGenres (?) }";
    private static final String GET_MOVIE_PEOPLE = "{ CALL getMoviePeople (?) }";
    private static final String GET_ALL_GENRES = "{ CALL getAllGenres }";
    private static final String GET_ALL_PEOPLE = "{ CALL getAllPeople }";
    private static final String GET_MOVIE = "{ CALL getMovie (?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?) }";
    private static final String GET_GENRE = "{ CALL getGenre (?) }";
    private static final String DELETE_GENRE = "{ CALL deleteGenre (?) }";
    private static final String UPDATE_GENRE = "{ CALL updateGenre (?,?) }";
    private static final String GET_PERSON = "{ CALL getPerson (?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    
    
     public Optional<Login> getLogin(String name, String pass) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GETLOGIN)) {
           
            stmt.setString(1, name);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    return Optional.of(new Login(
                            rs.getString(USERNAME), rs.getBoolean(ADMIN)
                            ));
                }
            }
        }
        return Optional.empty();
    }
     
      public void registerLogin(String name, String pass) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(REGISTER)) {
           
            stmt.setString(1, name);
            stmt.setString(2, pass);
            stmt.executeUpdate();
        }
       
    }

    public void addMovie(Movie movie) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_MOVIE)) {
            
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDescription());
            stmt.setInt(3, movie.getDuration());
            stmt.setString(4, movie.getScreeningdate().format(Movie.DB_DATE_FORMAT));
            stmt.setString(5, movie.getPicturepath());
            stmt.registerOutParameter(6, Types.INTEGER);

            stmt.executeUpdate();
            
            int movieId = stmt.getInt(6);
            
            for (Genre genre : movie.getGenres()) {
                int genreId = addGenre(genre);
                addMovieGenreRelation(movieId, genreId);
            }
            
            if(movie.getActors() != null){
             for (Person actor : movie.getActors()) {
                int personId = addPerson(actor);
                addMoviePersonRelation(movieId, personId,false);
            }
            }
           
            
             for (Person director : movie.getDirectors()) {
                int personId = addPerson(director);
                addMoviePersonRelation(movieId, personId,true);
            }
        
    }
    }

    public int addGenre(Genre genre) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_GENRE)) {
            
            stmt.setString(1, genre.getName());
            stmt.registerOutParameter(2, Types.INTEGER);

            stmt.executeUpdate();
            
            return stmt.getInt(2);
        
    }
    }

    public void addMovieGenreRelation(int movieId, int genreId) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_MGRELATION)) {
            
            stmt.setInt(1, movieId);
            stmt.setInt(2, genreId);
            

            stmt.executeUpdate();
           
        
    }
    }

    public int addPerson(Person person) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_PERSON)) {
            
            stmt.setString(1, person.getFirstname());
            stmt.setString(2, person.getLastname());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(3);
        
    }
}
     public void purgeDatabase() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(PURGE)) {
 
            stmt.executeUpdate();
        }
    }

    public void addMoviePersonRelation(int movieId, int personId, boolean b) throws SQLException {

         DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_MPRELATION)) {
            
            stmt.setInt(1, movieId);
            stmt.setInt(2, personId);
            stmt.setBoolean(3, b );

            stmt.executeUpdate();
            

        }   
        
    }
    
     public List<Movie> getMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
          
            while (rs.next()) {
                Movie movie =
                new Movie(
                                rs.getInt(MOVIEID),
                                rs.getString(TITLE),
                                LocalDate.parse(rs.getString(DATE), Movie.RS_DATE_FORMAT),
                                rs.getString(DESC),
                                rs.getInt(DURATION),
                                rs.getString(PIC_PATH)
                                );
                
                movie.setGenres(getMovieGenres(movie.getIdmovie()));
                setMoviePeople(movie);
                
                movies.add(movie);
            }
        } 
        return movies;
    }

    public List<Genre> getMovieGenres(int idmovie) throws SQLException  {
        
        
        List<Genre> genres = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIE_GENRES)) {
           
            stmt.setInt(1, idmovie);
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                   genres.add(new Genre (rs.getInt("IDGenre"), rs.getString("GenreName")));
                }
            }
        }
        
        return genres;
        
        
    }
    
        public List<Genre> getAllGenres() throws SQLException  {
        
        
        List<Genre> genres = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ALL_GENRES)) {
           
           
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                   genres.add(new Genre (rs.getInt("IDGenre"), rs.getString("Name")));
                }
            }
        }
        
        return genres;
        
        
    }
        
         public void setMoviePeople(Movie movie) throws SQLException {
        
        List<Person> directors = new ArrayList<>();
        List<Person> actors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIE_PEOPLE)) {
           
            stmt.setInt(1, movie.getIdmovie());
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                   Person p = new Person(rs.getInt("IDPerson"), rs.getString("Firstname"), rs.getString("Lastname"));
                   if(rs.getBoolean("isDirector")){
                       directors.add(p);
                   }
                   else{
                   actors.add(p);
                   }
                }
            }
        }
        
        movie.setActors(actors);
        movie.setDirectors(directors);
 
    }

    public List<Person> getAllPeople() throws SQLException {
        
        List<Person> people = new ArrayList<>();
        
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ALL_PEOPLE)) {
           
           
            try (ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                   people.add(new Person(rs.getInt("IDPerson"), rs.getString("Firstname"), rs.getString("Lastname")));
                   
                }
            }
        }
        
        return people;
 
    }

    public Optional<Movie> selectMovie(int selectedMovieId) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
       
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIE)) {
           
            stmt.setInt(1, selectedMovieId);
          
             try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Movie movie =
                new Movie(
                                rs.getInt(MOVIEID),
                                rs.getString(TITLE),
                                LocalDate.parse(rs.getString(DATE), Movie.RS_DATE_FORMAT),
                                rs.getString(DESC),
                                rs.getInt(DURATION),
                                rs.getString(PIC_PATH)
                                );
                
                movie.setGenres(getMovieGenres(movie.getIdmovie()));
                setMoviePeople(movie);
                
                return Optional.of(movie);
            } 
            }
        } 
        
        return Optional.empty();
    }

    public void deleteMovie(int idmovie) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            
            stmt.setInt(1, idmovie);
            
            stmt.executeUpdate();
        } 
        
    }

    public void updateMovie(Movie movie) throws SQLException {
        
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            
             int movieId = movie.getIdmovie();
            
            stmt.setInt(1, movieId);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getDescription());
            stmt.setInt(4, movie.getDuration());
            stmt.setString(5, movie.getScreeningdate().format(Movie.DB_DATE_FORMAT));
            stmt.setString(6, movie.getPicturepath());
            

            stmt.executeUpdate();
            
           
            
            for (Genre genre : movie.getGenres()) {
                int genreId = addGenre(genre);
                addMovieGenreRelation(movieId, genreId);
            }
            
            if(movie.getActors() != null){
             for (Person actor : movie.getActors()) {
                int personId = addPerson(actor);
                addMoviePersonRelation(movieId, personId,false);
            }
            }
           
            
             for (Person director : movie.getDirectors()) {
                int personId = addPerson(director);
                addMoviePersonRelation(movieId, personId,true);
            }
        
    }
        
    }

    public Optional<Genre> getGenre(int genreId) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
       
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_GENRE)) {
           
            stmt.setInt(1, genreId);
          
             try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Genre(genreId,rs.getString(GENRE_NAME)));
            } 
            }
        } 
        
        return Optional.empty();
        
    }

    public void deleteGenre(int idgenre) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {
            
            stmt.setInt(1, idgenre);
            
            stmt.executeUpdate();
        } 
        
    }

    public void updateGenre(Genre genre) throws SQLException {
        
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {
            
             
            
            stmt.setInt(1,  genre.getIdgenre());
            stmt.setString(2, genre.getName());
            
            stmt.executeUpdate();
            
  
        
    }
        
    }

    public Optional<Person> getPerson(int personId) throws SQLException {
        
         DataSource dataSource = DataSourceSingleton.getInstance();
       
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_PERSON)) {
           
            stmt.setInt(1, personId);
          
             try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Person(personId,rs.getString(FIRSTNAME),rs.getString(LASTNAME)));
            } 
            }
        } 
        
        return Optional.empty();
        
    }

    public void deletePerson(int idperson) throws SQLException {
       
         DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {
            
            stmt.setInt(1, idperson);
            
            stmt.executeUpdate();
        } 
        
    }

    public void updatePerson(Person person) throws SQLException {
        
         DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {
            
             
            
            stmt.setInt(1,  person.getIdperson());
            stmt.setString(2, person.getFirstname());
             stmt.setString(3, person.getLastname());
            
            stmt.executeUpdate();
            
  
        
    }
        
    }
}
