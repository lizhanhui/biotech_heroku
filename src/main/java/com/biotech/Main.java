package com.biotech;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

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

            WebAppContext context = new WebAppContext();
            context.setContextPath("/");
            context.setWar("target/web-1.0-SNAPSHOT.war");
            context.setExtractWAR(false);
            context.setClassLoader(Thread.currentThread().getContextClassLoader());
            server.setHandler(context);
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
