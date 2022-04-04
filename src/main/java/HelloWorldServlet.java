import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "HelloWorldServlet", urlPatterns = "/hello-world")
public class HelloWorldServlet extends HttpServlet {
private int aNumber = 0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("aNumber = " + aNumber );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
       int tempNumber = Integer.parseInt(req.getParameter("newVal"));
       aNumber = tempNumber;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Value changed");
    }
}