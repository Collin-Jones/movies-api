package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "main.MovieServlet", urlPatterns = "/movies/*")

public class MovieServlet extends HttpServlet {

    ArrayList<Movie> movies = new ArrayList<>();
    int nextId = 1;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try{
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            System.out.println(movieString);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie[] newMovies = new Gson().fromJson(br, Movie[].class);
        for (Movie movie : newMovies){
            movie.setId(nextId++);
            movies.add(movie);
        }
        try{
            PrintWriter out = response.getWriter();
            System.out.println("Movie(s) added");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}