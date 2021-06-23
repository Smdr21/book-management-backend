package launch;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import web.MyServletContextListener;

import java.io.File;

public class Main {

    public static void main(String... args) throws Exception {

        String webappDirLocation = new File("src/main/webapp").getAbsolutePath();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/",webappDirLocation);

        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources,"/WEB-INF/classes",additionWebInfClasses.getAbsolutePath(),"/"));
        ctx.setResources(resources);


        System.out.println("Application is running on port 8080");
        System.out.println("Application path is "+webappDirLocation);

        tomcat.start();
        tomcat.getServer().await();


    }
}
