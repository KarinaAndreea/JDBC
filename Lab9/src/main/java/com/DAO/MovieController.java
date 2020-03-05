package com.DAO;
import Model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieController {
    public void create(String name, Integer directorID) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into movies (name, director_id) values (?, ?)"))  {
            pstmt.setString(1, name);
            pstmt.setString(2, String.valueOf(directorID));
            pstmt.executeUpdate();
        }
    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from movies where name like '" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement statement = con.prepareStatement("select name from movies"+" where id = ?" )){
            statement.setInt(1, id);
            ResultSet rs =statement.executeQuery();
            while (rs.next()) {
                return rs.getString("name");
            }
        }
        return null;
    }

    public List<Movie> findAll() throws SQLException{
        List<Movie> movies = new ArrayList<>();
        Connection con = Database.getConnection();
        try (PreparedStatement statement = con.prepareStatement("select * from movies" )){
            ResultSet rs =statement.executeQuery();
            while (rs.next()) {
                Movie m = new Movie(rs.getInt("id"),rs.getString("name"),rs.getInt("director_id"));
                movies.add(m);
            }
        }
        return movies;
    }

    public void addActor(int movieid, int actorid) throws SQLException{
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into movie_actors (movie_id, actor_id) values (?, ?)"))  {
            pstmt.setInt(1, movieid);
            pstmt.setInt(2, actorid);
            pstmt.executeUpdate();
        }
    }
    public void retrieveActorsFromAMovie(int movieid) throws SQLException{
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("delete from movie_actors where movie_id = ?")){
            pstmt.setInt(1, movieid);
            pstmt.execute();
        }
    }

    public void deleteMoviesFromAnActor(int actorid) throws SQLException{
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("delete from movie_actors where actor_id = ?")){
            pstmt.setInt(1, actorid);
            pstmt.execute();
        }
    }

    //nu am testat-o
    public void deleteMoviesFromADirector(int directorid) throws SQLException{
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("delete movies, movie_actors from movies inner join movie_actors on id = movie_id where director_id = ?")){
            pstmt.setInt(1, directorid);
            pstmt.execute();
        }
    }


    public void deleteMovies() {
        Connection con = Database.getConnection();
        try {
            Statement stmt = con.createStatement();
            // Use TRUNCATE
            String sql = "TRUNCATE TABLE movies";
            // Execute deletion
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteMovieActors() {
        Connection con = Database.getConnection();
        try {
            Statement stmt = con.createStatement();
            // Use TRUNCATE
            String sql = "TRUNCATE  TABLE movie_actors";
            // Execute deletion
            stmt.executeUpdate(sql);
            // Use DELETE
            sql = "DELETE FROM movie_actors";
            // Execute deletion
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


}