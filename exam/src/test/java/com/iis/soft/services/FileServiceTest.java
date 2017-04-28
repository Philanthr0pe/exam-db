package com.iis.soft.services;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class FileServiceTest {
    FileService fileService;

    @Before
    public void setUp() throws Exception {
        fileService = new FileService();
    }

    @Test
    public void getAllFromDB() throws Exception {
        fileService.getAllFromDB();
    }

    @Test
    public void insertAll() throws Exception {
        int count = 100;
        fileService.insertAll(count);
    }

    @Test
    public void myTest() throws Exception {
        this.insertAll();
        this.getAllFromDB();
    }

}