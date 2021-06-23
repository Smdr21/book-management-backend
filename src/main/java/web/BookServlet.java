package web;

import com.google.gson.Gson;
import model.Book;
import model.DataAccess;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(value = "/", name = "bookServlet")
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = DataAccess.getAllBooks(req.getServletContext());
        Gson gson = new Gson();
        String json = gson.toJson(books);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        writer.print(json);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Book book = gson.fromJson(req.getReader(),Book.class);
        DataAccess.addNewBook(req.getServletContext(), book);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Book book = gson.fromJson(req.getReader(),Book.class);
        int id = Integer.parseInt(req.getParameter("id"));
        DataAccess.editBook(req.getServletContext(),id,book);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DataAccess.deleteBook(req.getServletContext(),id);
    }
}
