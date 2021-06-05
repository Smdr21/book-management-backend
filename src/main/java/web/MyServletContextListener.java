package web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import controller.MyDataSouce;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.SQLException;
@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var myDataSource = MyDataSouce.getPGDataSource(sce.getServletContext());
        sce.getServletContext().setAttribute("datasource",myDataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Object datasource = sce.getServletContext().getAttribute("datasource");
        if(datasource instanceof PGConnectionPoolDataSource){
            PGConnectionPoolDataSource pgDataSource = (PGConnectionPoolDataSource)datasource;
            try{
                pgDataSource.getPooledConnection().close();
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
