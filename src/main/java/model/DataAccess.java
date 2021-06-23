package model;

import org.postgresql.ds.PGConnectionPoolDataSource;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    public static List<Book> getAllBooks(ServletContext sc){
        var datasource = (PGConnectionPoolDataSource)sc.getAttribute("datasource");
        List<Book> listOfBooks = new ArrayList<>();
        try(Connection myconn = datasource.getConnection()){
            PreparedStatement ps = myconn.prepareStatement("SELECT * FROM book_management;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Book book = new Book(rs.getInt("id"),rs.getString("title"),rs.getString("author"),rs.getFloat("price"));
                listOfBooks.add(book);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            System.out.println("GET ALL BOOKS FAILED: "+e.getStackTrace());
        }
        return listOfBooks;
    }
    public static boolean addNewBook(ServletContext sc, Book book){
        var datasource = (PGConnectionPoolDataSource)sc.getAttribute("datasource");

        try(Connection myconn = datasource.getConnection()){
            PreparedStatement ps = myconn.prepareStatement("INSERT INTO book_management (title,author,price) VALUES (?,?,?);");

            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setFloat(3,book.getPrice());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println("ADD NEW BOOK FAILED: "+e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean editBook(ServletContext sc,int id, Book book){
        var datasource = (PGConnectionPoolDataSource)sc.getAttribute("datasource");

        try(Connection myconn = datasource.getConnection()){
            PreparedStatement ps = myconn.prepareStatement("UPDATE book_management SET title = ?, author = ?, price = ? WHERE id = ?");

            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setFloat(3,book.getPrice());
            ps.setInt(4,id);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println("EDIT BOOK FAILED: "+e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean deleteBook(ServletContext sc, int id){
        var datasource = (PGConnectionPoolDataSource)sc.getAttribute("datasource");

        try(Connection myconn = datasource.getConnection()){
            PreparedStatement ps = myconn.prepareStatement("DELETE FROM book_management WHERE id = ?");

            ps.setInt(1,id);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            System.out.println("DELETE BOOK FAILED: "+e.getMessage());
            return false;
        }
        return true;
    }

}
