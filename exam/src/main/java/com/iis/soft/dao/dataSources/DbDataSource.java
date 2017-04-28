package com.iis.soft.dao.dataSources;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class DbDataSource {

    public static DataSource getDataSource() {
        Properties properties = new Properties();
        BasicDataSource dataSource = new BasicDataSource();
        try {
            InputStream input = DbDataSource.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(input);
            dataSource.setDriverClassName(properties.getProperty("DB_DRIVER_CLASS"));
            dataSource.setUrl(properties.getProperty("DB_URL"));
            dataSource.setUsername(properties.getProperty("DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("DB_PASSWORD"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
