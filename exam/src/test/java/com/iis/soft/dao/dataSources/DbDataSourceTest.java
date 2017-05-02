package com.iis.soft.dao.dataSources;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by ASUS-PC on 02.05.2017.
 */
public class DbDataSourceTest {
    DbDataSource dbDataSource;

    @Before
    public void setUp() {
        dbDataSource = new DbDataSource();
    }

    @Test
    public void getConnection() throws Exception {
        Connection connection = dbDataSource.getConnection();
        Assert.assertNotNull(connection);
    }

    @After
    public void tearDown() throws Exception {
        dbDataSource.close();
    }
}