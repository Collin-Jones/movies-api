package main;

import com.google.gson.Gson;
import data.InMemoryMoviesDao;
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
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "main.MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

    private InMemoryMoviesDao dao = new InMemoryMoviesDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(dao.all().toArray());
            out.println(movieString);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie[] newMovies = new Gson().fromJson(br, Movie[].class);
        try {
            dao.insertAll(newMovies);
            PrintWriter out = response.getWriter();
            out.println("Movie(s) added");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        Movie newMovie = new Gson().fromJson(req.getReader(), Movie.class);
        newMovie.setId(targetId);
        try {
            dao.update(newMovie);
            PrintWriter out = resp.getWriter();
            out.println("Target id is " + targetId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

        try {
            dao.delete(targetId);
            PrintWriter out = resp.getWriter();
            out.println("Movie Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy(){
//        dao.cleanup();
    }
}

