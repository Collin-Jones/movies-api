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

    public MySqlMoviesDao() {
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
        Movie findMovie = null;
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM movies WHERE id = " + id);
            rs.next();
            findMovie = new Movie(
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getString("actors"),
                    rs.getInt("rating"),
                    rs.getString("poster"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("plot"),
                    rs.getInt("id")
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findMovie;
    }

    @Override
    public void insert(Movie movie) {
        // TODO: Insert one movie
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO movies" +
                    "(title, year, director, actors, rating, poster, genre, plot) ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // TODO: Insert all the movies!
        // Build sql template
        StringBuilder sql = new StringBuilder("INSERT INTO movies (" +
                "title, year, director, actors, rating, poster, genre, plot) " +
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
            statement.setInt((counter * 8) + 5, movie.getRating());
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
        Movie movieToChange = findOne(movie.getId());

        if (movie.getTitle() != null) {
            movieToChange.setTitle(movie.getTitle());
        }
        if (movie.getRating() != null) {
            movieToChange.setRating(movie.getRating());
        }
        if (movie.getPoster() != null) {
            movieToChange.setPoster(movie.getPoster());
        }
        if (movie.getYear() != null) {
            movieToChange.setYear(movie.getYear());
        }
        if (movie.getGenre() != null) {
            movieToChange.setGenre(movie.getGenre());
        }
        if (movie.getDirector() != null) {
            movieToChange.setDirector(movie.getDirector());
        }
        if (movie.getActors() != null) {
            movieToChange.setActors(movie.getActors());
        }
        if (movie.getPlot() != null) {
            movieToChange.setPlot(movie.getPlot());
        }
        StringBuilder sql = new StringBuilder("UPDATE movies SET title = ?, rating = ?, poster = ?, year = ?, genre = ?, director = ?, actors = ?, plot = ? WHERE id = ?");

        PreparedStatement statement = connection.prepareStatement(sql.toString());
        statement.setString(1, movieToChange.getTitle());
        statement.setInt(2, movieToChange.getRating());
        statement.setString(3, movieToChange.getPoster());
        statement.setInt(4, movieToChange.getYear());
        statement.setString(5, movieToChange.getGenre());
        statement.setString(6, movieToChange.getDirector());
        statement.setString(7, movieToChange.getActors());
        statement.setString(8, movieToChange.getPlot());
        statement.setInt(9, movieToChange.getId());

        statement.executeUpdate();

    }


    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie
        PreparedStatement statement = connection.prepareStatement("DELETE FROM movies WHERE id = ?");
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    @Override
    public void cleanUp() {
        System.out.println("Closing connection");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

