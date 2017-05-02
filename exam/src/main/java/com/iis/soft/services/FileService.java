package com.iis.soft.services;

import com.iis.soft.CommandController;
import com.iis.soft.dao.DbDao;
import com.iis.soft.dao.XmlDao;
import com.iis.soft.models.DepWorker;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class FileService {

    private DbDao dbDao;
    private XmlDao xmlDao;

    /**
     * @param filename - name of file for export database
     */
    public void fromDbToXml(String filename) {
        filename = generatePath(filename);
        System.out.println("Filename :" + filename);
        dbDao = new DbDao();
        Set<DepWorker> all = dbDao.getAll();
        dbDao.close();
        xmlDao = new XmlDao();
        xmlDao.writeToXml(filename, all);

    }

    /**
     * @param filename - name of file for synchronization database
     */
    public void syncDbWithXml(String filename) {
        filename = generatePath(filename);
        System.out.println("Filename :" + filename);
        xmlDao = new XmlDao();
        Set<DepWorker> depWorkersFromXml = xmlDao.readFromXml(filename);
        dbDao = new DbDao();
        Set<DepWorker> depWorkersFromDb = dbDao.getAll();

        int[] ints = depWorkersFromDb.stream().filter(d -> !depWorkersFromXml.contains(d))
                .mapToInt(d -> d.getId()).toArray();
        dbDao.deleteAllById(ints);

        Set<DepWorker> insertCollect = depWorkersFromXml.stream()
                .filter(d -> !depWorkersFromDb.contains(d))
                .collect(Collectors.toSet());
        dbDao.insertAll(insertCollect);

        Set<DepWorker> updateCollect = depWorkersFromXml.stream()
                .filter(d -> depWorkersFromDb.contains(d))
                .collect(Collectors.toSet());

        Set finalUpdateCollect = new HashSet();

        for (DepWorker depWorker : updateCollect) {
            for (DepWorker worker : depWorkersFromDb) {
                if (depWorker.equals(worker) &&
                        !depWorker.getDepDescription().equals(worker.getDepDescription())) {
                    finalUpdateCollect.add(new DepWorker(worker.getId(),
                                                    worker.getDepCode(),
                                                    worker.getDepJob(),
                                                    depWorker.getDepDescription()));
                }
            }
        }
        dbDao.updateAll(finalUpdateCollect);
        dbDao.close();
    }

    private String generatePath(String filename) {
        String path = "";
        try {
            path = CommandController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path + "/" + filename;
    }
}
