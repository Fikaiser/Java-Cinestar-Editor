/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import models.Genre;
import models.Movie;
import models.Person;


/**
 *
 * @author RnaBo
 */
public class MovieParser {
    
     private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

   

    private MovieParser() {
    }
    
    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL, 0, "GET");
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent()) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case ITEM:
                                    movie = new Movie();
                                    movies.add(movie);
                                    break;
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case DURATION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDuration(parseInt(data));
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {
                                        
                                        int imgEnd = data.indexOf(">");
                                        movie.setDescription(data.substring(imgEnd + 1,data.length() - 1));
                                    }
                                    break;
                                case GENRE:
                                    if (movie != null && !data.isEmpty()) {
                                        
                                String[] words = data.split(",");
                                List<Genre> genres = new ArrayList<Genre>();
                               
                                        for (String genre : words) {
                                            genres.add(new Genre(genre.trim()));
                                        }
                                        movie.setGenres(genres);
                                    }
                                    break;
                                 case ACTORS:
                                    if (movie != null && !data.isEmpty()) {
                                        
                                String[] words = data.split(",");
                                List<Person> actors = new ArrayList<Person>();
                               
                                        for (String actor : words) {
                                            actors.add(handlePerson(actor.trim()));
                                        }
                                        movie.setActors(actors);
                                    }
                                    break;
                                case DIRECTOR:
                                    if (movie != null && !data.isEmpty()) {

                                        String[] words = data.split(",");
                                        List<Person> directors = new ArrayList<Person>();
                               
                                        for (String director : words) {
                                            directors.add(handlePerson(director.trim()));
                                        }
                                        movie.setDirectors(directors);
                                        
                                    }
                                    break;
                                case PICTURE:
                                    // bugfix -> prevent to enter 2 times!!!
                                    if (movie != null && startElement != null && movie.getPicturepath() == null) {
                                        
                                            handlePicture(movie, data);
                                        
                                    }
                                    break;
                                case DATE:
                                    if (movie != null && !data.isEmpty()) {
                               
                                String[] dates = data.split("\\.");
                                String d = dates[0].length() == 2? dates[0] : "0" + dates[0];
                                String m = dates[1].length() == 2? dates[1] : "0" + dates[1];
                                String y = dates[2];
                                
                                
                                        LocalDate publishedDate = LocalDate.parse(y+m+d, Movie.DB_DATE_FORMAT);
                                        
                                        movie.setScreeningdate(publishedDate);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        return movies;

    }
    
     private static Person handlePerson(String person) {
        
       
         int space = person.indexOf(" ");
         if(space == -1){
             return new Person(person,"");
         }
         else{
             return new Person(person.substring(0,space),person.substring(space,person.length()));
         }
         
    }

    private static void handlePicture(Movie movie, String pictureUrl){

        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;
            
            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            movie.setPicturepath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {

        ITEM("item"),
        TITLE("title"),
        DURATION("trajanje"),
        DESCRIPTION("description"),
        PICTURE("plakat"),
        ACTORS("glumci"),
        GENRE("zanr"),
        DIRECTOR("redatelj"),
        DATE("pocetak");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
    
}
