package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

@WebServlet(name = "main.MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

    ArrayList<Movie> movies = new ArrayList<>();
    int nextId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie[] newMovies = new Gson().fromJson(br, Movie[].class);
        for (Movie movie : newMovies) {
            System.out.println(movie);
            movie.setId(nextId++);
            movies.add(movie);
        }
        try {
            PrintWriter out = response.getWriter();
            out.println("Movie(s) added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        Movie newMovie = new Gson().fromJson(req.getReader(), Movie.class);

        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                if (newMovie.getTitle() != null) {
                    movie.setTitle(newMovie.getTitle());
                }
                if (newMovie.getRating() != null) {
                    movie.setRating(newMovie.getRating());
                }
                if (newMovie.getPoster() != null) {
                    movie.setPoster(newMovie.getPoster());
                }
                if (newMovie.getYear()  != null) {
                    movie.setYear(newMovie.getYear());
                }
                if (newMovie.getGenre()  != null) {
                    movie.setGenre(newMovie.getGenre());
                }
                if (newMovie.getDirector()  != null) {
                    movie.setDirector(newMovie.getDirector());
                }
                if (newMovie.getPlot()  != null) {
                    movie.setPlot(newMovie.getPlot());
                }
                if (newMovie.getActors()  != null) {
                    movie.setActors(newMovie.getActors());
                }
                if (newMovie.getId()  != null) {
                    movie.setId(newMovie.getId());
                }
            }

        }

        PrintWriter out = resp.getWriter();
        out.println("Target id is " + targetId);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Movie movieToDelete = new Gson().fromJson(req.getReader(), Movie.class);
        resp.setContentType("application/json");
        String[] uriParts = req.getRequestURI().split("/");

        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                movies.remove(movieToDelete.getId());
                PrintWriter out = resp.getWriter();
                out.println("Movie Deleted");
            }
        }
    }
}

