package com.biotech;

import com.alibaba.druid.pool.DruidDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public final class Helper {

    private static DruidDataSource DATA_SOURCE;

    public static DruidDataSource getDataSource() throws SQLException {

        if (null != DATA_SOURCE) {
            return DATA_SOURCE;
        }

        synchronized (Helper.class) {
            try {
                DATA_SOURCE = new DruidDataSource();
                DATA_SOURCE.setUrl(getDatabaseURL());

                String[] confidential = getDatabaseAccount();
                DATA_SOURCE.setUsername(confidential[0]);
                DATA_SOURCE.setPassword(confidential[1]);

                DATA_SOURCE.setInitialSize(1);
                DATA_SOURCE.setMinIdle(1);
                DATA_SOURCE.setMaxActive(20);
                DATA_SOURCE.setMaxWait(60000);

                DATA_SOURCE.setTimeBetweenEvictionRunsMillis(60000);
                DATA_SOURCE.setMinEvictableIdleTimeMillis(300000);

                DATA_SOURCE.setValidationQuery("SELECT 1");
                DATA_SOURCE.setTestWhileIdle(true);
                DATA_SOURCE.setTestOnBorrow(false);
                DATA_SOURCE.setTestOnReturn(false);

                DATA_SOURCE.setPoolPreparedStatements(true);
                DATA_SOURCE.setMaxPoolPreparedStatementPerConnectionSize(20);

                DATA_SOURCE.setFilters("stat");
            } catch (URISyntaxException e) {
                throw new SQLException("Failed to get Database URL string", e);
            }
        }

        return DATA_SOURCE;
    }

    private static String getDatabaseURL() throws URISyntaxException{
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        return "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
    }

    private static String[] getDatabaseAccount() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        return dbUri.getUserInfo().split(":");
    }

    public static Connection getConnection() throws SQLException {
//        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//        String username = dbUri.getUserInfo().split(":")[0];
//        String password = dbUri.getUserInfo().split(":")[1];
//        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
//
//        return DriverManager.getConnection(dbUrl, username, password);

        return getDataSource().getConnection();
    }

    public static void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!");
    }

    public static void showDatabase(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Connection connection = Helper.getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

            String out = "Hello!\n";
            while (rs.next()) {
                out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
            }

            resp.getWriter().print(out);
        } catch (Exception e) {
            resp.getWriter().print("There was an error: " + e.getMessage());
        }
    }
}
