package com.iis.soft.dao;

import com.iis.soft.models.DepWorker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ASUS-PC on 02.05.2017.
 */
public class DbDaoTest {
    DbDao dbDao;
    Set<DepWorker> depWorkers;

    @Before
    public void setUp() throws Exception {
        dbDao = new DbDao();

    }

    @After
    public void tearDown() throws Exception {
        dbDao.deleteAllById(depWorkers.stream()
                .mapToInt(d -> d.getId())
                .toArray());
        dbDao.close();
    }

    @Test
    public void insertAll() throws Exception {

        depWorkers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            depWorkers.add(new DepWorker("depCode " + i,
                                        "depJob " + i,
                                        "depDescription " + i));
        }
        dbDao.insertAll(depWorkers);
        Set<DepWorker> all = dbDao.getAll();
        Assert.assertEquals(depWorkers, all);
        depWorkers = all;
    }

}