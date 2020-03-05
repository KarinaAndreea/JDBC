import com.DAO.Database;
import com.DAO.GenerateFakeData;
import com.DAO.MovieController;
import com.DAO.PersonController;

import java.sql.*;
public class Main {

    public static void main(String[] args) {
        PersonController persons = new PersonController();
        MovieController movies = new MovieController();
        GenerateFakeData d = new GenerateFakeData();
        try {
            //adaug persoane in tabel
            persons.create("Francis Ford Coppola");
            persons.create("Marlon Brando");
            persons.create("Al Pacino");
            persons.create("Leonardo DiCaprio");
            d.addToDataBase();
            System.out.println("Commiting data here....");
            Database.commit();

            //Teste
            System.out.println(persons.findById(2));

            //adaug filme
            movies.create("The Godfather", persons.findByName("Francis Ford Coppola"));
            movies.create("Titanic", persons.findByName("Leonardo DiCaprio"));
            Database.commit();

            //adaug actori
            movies.addActor(movies.findByName("The Godfather"), persons.findByName("Al Pacino"));
            movies.addActor(movies.findByName("The Godfather"), persons.findByName("Marlon Brando"));
            movies.addActor(movies.findByName("Titanic"), persons.findByName("Leonardo DiCaprio"));

            movies.retrieveActorsFromAMovie(movies.findByName("The Godfather"));
            movies.deleteMoviesFromAnActor(persons.findByName("Leonardo DiCaprio"));
            Database.commit();
            System.out.println("Lista tuturor persoanelor: " + persons.findAll());
            System.out.println("Lista tuturor filmelor: " + movies.findAll());


        } catch (SQLException e) {
            // If there is an error then rollback the changes.
            System.out.println("Rolling back data here....");
            System.out.println(e);
            Database.rollback();
        } finally {
            persons.delete();
            movies.deleteMovies();
            movies.deleteMovieActors();
            try {
                System.out.println(persons.findAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Database.closeConnection();
        }
    }
}