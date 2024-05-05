package com.example.emdb.classes;

import com.example.emdb.models.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.os.StrictMode;

public class Database {
    public Connection createConnection() {
        // Allow network operations on the main thread (not recommended for production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Database connection parameters
        String connectionUrl = "jdbc:mysql://mc.koudata.fi:3306/moviedb?characterEncoding=latin1&useConfigs=maxPerformance";
        String username = "app";
        String password = "databaseApp!";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);

            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }
    }

    public ArrayList<Movie> getMovies() {
        Connection connection = createConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviedb.movies WHERE idMovies >= 103;");

            ArrayList<Movie> movies = new ArrayList<Movie>();

            while (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9)
                ));
            }

            return movies;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
