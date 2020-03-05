package com.DAO;
import Model.Actor;
import Model.Person;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class PersonController {
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into persons (name) values (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from persons where name like '" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        String name="";
        try (PreparedStatement statement = con.prepareStatement("select name from persons"+" where id = ?" )){
            statement.setInt(1, id);
            ResultSet rs =statement.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
        }
        return name;
    }

    public List<Person> findAll() throws SQLException{
        List<Person> persons = new ArrayList<>();
        Connection con = Database.getConnection();
        try (PreparedStatement statement = con.prepareStatement("select * from persons" )){
            ResultSet rs =statement.executeQuery();
            while (rs.next()) {
                Person p = new Actor(rs.getInt("id"),rs.getString("name"));
                persons.add(p);
            }
        }
        return persons;
    }

    public void delete() {
        Connection con = Database.getConnection();
        try {
            Statement stmt = con.createStatement();
            // Use TRUNCATE
            String sql = "TRUNCATE table persons";
            // Execute deletion
            stmt.executeUpdate(sql);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

}
