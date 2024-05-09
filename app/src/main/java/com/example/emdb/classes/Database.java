package com.example.emdb.classes;

import com.example.emdb.models.Movie;
import com.example.emdb.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public boolean checkConnection() {
        try (Connection connection = createConnection()) {
            return connection != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Movie> fetchMovies(String query) {
        Connection connection = createConnection();
        if (connection == null) return null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
                        resultSet.getFloat(8),
                        resultSet.getString(9)
                ));
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getMovieGenres(int movieId) {
        ArrayList<String> foundedGenres = new ArrayList<>();

        Movie movie = getMovieById(movieId);
        if (movie == null) return foundedGenres;

        Connection connection = createConnection();
        if (connection == null) return foundedGenres;

        try {
            String query = "SELECT Genres FROM moviedb.movies WHERE idMovies = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String movieGenresString = resultSet.getString("Genres");
                String[] movieGenresArray = movieGenresString.split(", ");

                for (String genre : movieGenresArray) {
                    if (movie.getGenres().contains(genre)) {
                        foundedGenres.add(genre);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return foundedGenres;
    }


    public ArrayList<Movie> getBestMovies() {
        String query = "SELECT * FROM moviedb.movies ORDER BY Rating DESC;";
        return fetchMovies(query);
    }

    public ArrayList<Movie> getNewMovies() {
        String query = "SELECT * FROM moviedb.movies ORDER BY ReleaseYear DESC;";
        return fetchMovies(query);
    }

    public ArrayList<Movie> getMoviesByGenre(String genre) {
        String query = "SELECT * FROM moviedb.movies WHERE Genres LIKE '%" + genre + "%';";
        return fetchMovies(query);
    }

    public ArrayList<Movie> getMovieBySearch(String search) {
        String query = "SELECT * FROM moviedb.movies WHERE Name LIKE '%" + search + "%';";
        return fetchMovies(query);
    }

    public Movie getMovieById(int id) {
        String query = "SELECT * FROM moviedb.movies WHERE idMovies = ?";
        try (Connection connection = createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Movie(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getFloat(8),
                            resultSet.getString(9)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void signUpUser(User user) {
        Connection connection = createConnection();
        if (connection == null) return;

        PreparedStatement statement = null;

        try {
            String query = "INSERT INTO moviedb.usertable (idUser, username, email, password) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, 0);
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public User logIn(String login, String password) {
        Connection connection = createConnection();
        if (connection == null) return null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            String query = "SELECT * FROM moviedb.usertable WHERE (username = ? OR email = ?) AND password = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, login);
            statement.setString(3, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public boolean userAlreadyExists(String username, String email) {
        Connection connection = createConnection();
        if (connection == null) return false;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM moviedb.usertable WHERE username = ? OR email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            resultSet = statement.executeQuery();

            boolean userExists = resultSet.next();

            System.out.println("Query: " + query);
            System.out.println("User exists: " + userExists);

            return userExists;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public void createMovie(Movie movie) {
        Connection connection = createConnection();
        if (connection == null) return;

        PreparedStatement statement = null;

        try {
            String query = "INSERT INTO moviedb.movies (idMovies, Name, Length, ReleaseYear, Genres, MainActors, Director, Rating, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, 0);
            statement.setString(2, movie.getTitle());
            statement.setInt(3, movie.getLength());
            statement.setString(4, movie.getReleaseYear());
            statement.setString(5, movie.getGenres());
            statement.setString(6, movie.getStars());
            statement.setString(7, movie.getDirector());
            statement.setFloat(8, movie.getRating());
            statement.setString(9, movie.getImage());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public boolean movieAlreadyExists(String title) {
        Connection connection = createConnection();
        if (connection == null) return false;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM moviedb.movies WHERE Name = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void deleteAccount(int userId) {
        Connection connection = createConnection();
        if (connection == null) return;

        PreparedStatement statement = null;

        try {
            String query = "DELETE FROM moviedb.usertable WHERE idUser = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
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
