package com.DAO;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenerateFakeData {
    private ArrayList<String> nume = new ArrayList<>();
    private static final int NUMBER = 100;

    public  GenerateFakeData(){
        Faker faker = new Faker();
        for(int i=0; i<= NUMBER; i++)
        {
            String name = faker.name().fullName();
            nume.add(name);

        }
       // System.out.println(nume);
    }
    public void addToDataBase() throws SQLException {
        Connection con = Database.getConnection();
        for(int i=0; i<= NUMBER; i++) {
            try (PreparedStatement pstmt = con.prepareStatement("insert into persons (name) values (?)")) {
                pstmt.setString(1, nume.get(i));
                pstmt.executeUpdate();
            }
        }
    }
}
