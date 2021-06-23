package controller;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.common.BaseDataSource;

import javax.servlet.ServletContext;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyDataSouce {
    public static PGConnectionPoolDataSource getPGDataSource(ServletContext sc){

        Properties props = new Properties();
        PGConnectionPoolDataSource ds = null;

        try(var inputStream = sc.getResourceAsStream("WEB-INF/dbconfig.properties")){
            props.load(inputStream);
            ds = new PGConnectionPoolDataSource();
            ds.setURL(props.getProperty("DATABASE-URL"));
            ds.setUser(props.getProperty("DATABASE-USERNAME"));
            ds.setPassword(props.getProperty("DATABASE-PASSWORD"));
        }catch (IOException e){
            System.out.println("IT HAS FAILED MY FRIEND\n"+ e.getMessage());
        }
        return ds;
    }
}
