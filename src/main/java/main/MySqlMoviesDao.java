package main;
import data.Movie;
import data.MoviesDao;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.jdbc.Driver;


public class MySqlMoviesDao implements MoviesDao {

    private Connection connection = null;

    public MySqlMoviesDao(ObjectInputFilter.Config config) {
        try {
            DriverManager.registerDriver(new Driver());

            this.connection = DriverManager.getConnection(
                    TestConfig.getHost(), // <-- WHERE IS THE DB?
                    TestConfig.getUser(), // <-- WHO IS ACCESSING?
                    TestConfig.getPassword() // <-- WHAT IS THEIR PASSWORD?
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }
    }

    @Override
    public List<Movie> all() throws SQLException {
        // TODO: Get ALL the movies
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM movies");

        List<Movie> movies = new ArrayList<>();

        while (rs.next()) {
            movies.add(new Movie(
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getString("actors"),
                    rs.getInt("rating"),
                    rs.getString("poster"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("plot"),
                    rs.getInt("id")
            ));
        }

        return movies;
    }

    @Override
    public Movie findOne(int id) {
        // TODO: Get one movie by id
        return null;
    }

    @Override
    public void insert(Movie movie) {
        // TODO: Insert one movie
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // TODO: Insert all the movies!
        // Build sql template
        StringBuilder sql = new StringBuilder("INSERT INTO movies (" +
                "title, year, director, actors, imdbId, poster, genre, plot) " +
                "VALUES ");


        // Add a interpolation template for each element in movies list
        sql.append("(?, ?, ?, ?, ?, ?, ?, ?), ".repeat(movies.length));

        // Create a new String and take off the last comma and whitespace
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));

        // Use the sql string to create a prepared statement
        PreparedStatement statement = connection.prepareStatement(sql.toString());

        // Add each movie to the prepared statement using the index of each sql param: '?'
        // This is done by using a counter
        // You could use a for loop to do this as well and use its incrementor
        int counter = 0;
        for (Movie movie : movies) {
            statement.setString((counter * 8) + 1, movie.getTitle());
            statement.setInt((counter * 8) + 2, movie.getYear());
            statement.setString((counter * 8) + 3, movie.getDirector());
            statement.setString((counter * 8) + 4, movie.getActors());
            statement.setInt((counter * 8) + 5, movie.getId());
            statement.setString((counter * 8) + 6, movie.getPoster());
            statement.setString((counter * 8) + 7, movie.getGenre());
            statement.setString((counter * 8) + 8, movie.getPlot());
            counter++;
        }
        statement.executeUpdate();
    }

    @Override
    public void update(Movie movie) throws SQLException {
        //TODO: Update a movie here!
    }

    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie

        String sql =
                "DELETE FROM movies " +
                        "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        statement.execute();
    }
}

