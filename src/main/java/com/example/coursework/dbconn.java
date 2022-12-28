package com.example.coursework;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class dbconn {
    public Connection getdbconnection () throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","root","");
        return connection;
    }
}
