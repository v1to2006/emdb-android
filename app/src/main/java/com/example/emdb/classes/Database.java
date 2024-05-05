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
    private static final Database instance = new Database();

    private Database() {}

    public static Database getInstance() {
        return instance;
    }

    public Connection createConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectionUrl = "jdbc:mysql://mc.koudata.fi:3306/moviedb?characterEncoding=latin1&useConfigs=maxPerformance";
        String username = "app";
        String password = "databaseApp!";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(connectionUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Movie> getBestMovies() {
        Connection connection = createConnection();
        if (connection == null) return null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviedb.movies WHERE idMovies >= 103 ORDER BY Rating DESC;");

            ArrayList<Movie> movies = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Movie> getNewMovies() {
        Connection connection = createConnection();
        if (connection == null) return null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviedb.movies WHERE idMovies >= 103 ORDER BY ReleaseYear DESC;");

            ArrayList<Movie> movies = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Movie> getMoviesByGenre(String genre) {
        Connection connection = createConnection();
        if (connection == null) return null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM moviedb.movies WHERE idMovies >= 103 AND Genres LIKE '%" + genre + "%';");

            ArrayList<Movie> movies = new ArrayList<>();
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getCategories() {
        return new ArrayList<String>() {{
            add("Action"); add("Adventure"); add("Animation"); add("Biography"); add("Comedy");
            add("Crime"); add("Documentary"); add("Drama"); add("Family"); add("Fantasy");
            add("Film-Noir"); add("Game-Show"); add("History"); add("Horror"); add("Music");
            add("Musical"); add("Mystery"); add("News"); add("Reality-TV"); add("Romance");
            add("Sci-Fi"); add("Short"); add("Sport"); add("Talk-Show"); add("Thriller");
            add("War"); add("Western");
        }};
    }
}
