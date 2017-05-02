package com.iis.soft.dao.dataSources;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class DbDataSource {

    private Connection connection;
    private BasicDataSource dataSource;

    private Logger logger = Logger.getLogger(DbDataSource.class);

    /**
     * @return - return connection to database
     */
    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        Properties properties = new Properties();
        dataSource = new BasicDataSource();
        try {
            InputStream input = DbDataSource.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(input);
            dataSource.setDriverClassName(properties.getProperty("DB_DRIVER_CLASS"));
            dataSource.setUrl(properties.getProperty("DB_URL"));
            dataSource.setUsername(properties.getProperty("DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("DB_PASSWORD"));
            connection = dataSource.getConnection();
            logger.info("Connection: " + connection);
            connection.setAutoCommit(false);
            logger.info("AutoCommit = false");

        } catch (IOException | SQLException e) {
            logger.error("DbDataSource error:  ", e);
        }
        return connection;
    }

    public void close() throws SQLException {
        connection.commit();
        logger.info("Connection commit");
        connection.close();
        logger.info("Connection close");
        dataSource.close();
        logger.info("DataSource is closed!");
        System.err.println("DataSource is closed!");
    }
}
