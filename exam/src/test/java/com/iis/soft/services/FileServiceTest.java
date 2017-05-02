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
    public void fromDbToXml() throws Exception {
        String filename = "test.xml";
        fileService.fromDbToXml(filename);
    }

    @Test
    public void syncDbWithXml() {
        String filename = "test.xml";
        fileService.syncDbWithXml(filename);

    }

}