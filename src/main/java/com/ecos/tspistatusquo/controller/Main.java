package com.ecos.tspistatusquo.controller;

import com.ecos.tspistatusquo.view.MainView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

/**
 * modela el controlador de peticiones de la aplicacion web
 * 
 * @author Dev
 * @version 1.0
 * @since 1.0
 */
public class Main extends HttpServlet {

    /**
     * doGet
     * 
     * controla las peticiones realizadas por el metodo get
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MainView.showHome(req, resp);
    }
    
    /**
     * doPost
     * 
     * controla las peticiones por el metodo Post
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        //Server server = new Server(80);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Main()), "/*");
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
