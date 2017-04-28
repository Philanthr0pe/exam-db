package com.iis.soft.services;

import com.iis.soft.dao.DbDao;
import com.iis.soft.models.DepWorker;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class FileService {

    public void getAllFromDB() {
        Set<DepWorker> all = new DbDao().getAll();
        for (DepWorker depWorker : all) {
            System.out.println(depWorker);
        }
    }

    public void insertAll(int count) {
        HashSet<DepWorker> depWorkers = new HashSet<>();
        for (int i = 0; i < count; i++) {
            depWorkers.add(new DepWorker("depCode:" + i, "depJob:"+i, "descr:" + i));
        }
        new DbDao().insertAll(depWorkers);
    }

}
