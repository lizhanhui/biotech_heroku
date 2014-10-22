package com.biotech;

import com.biotech.servlet.MainServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Entry of this application.
 */
public class Main {

    /**
     * Entry of the application.
     * @param args arguments.
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(Integer.valueOf(System.getenv("PORT")));

            ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setDirectoriesListed(true);
            resourceHandler.setWelcomeFiles(new String[] {"index.html"});
            resourceHandler.setResourceBase("webapp");


            ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            servletContextHandler.setContextPath("/");
            servletContextHandler.addServlet(new ServletHolder(new MainServlet()), "/");


            contextHandlerCollection.setHandlers(new Handler[] {servletContextHandler, resourceHandler});
            server.setHandler(contextHandlerCollection);
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
