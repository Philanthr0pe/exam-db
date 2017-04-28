package com.iis.soft.dao.utils;

import com.iis.soft.models.DepWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class ResultSetExtractor {

    public Set<DepWorker> extractData(ResultSet rs) throws SQLException {
        HashSet<DepWorker> depWorkers = new HashSet<>();
        while (rs.next()) {
            String depcode = rs.getString("depcode");
            String depjob = rs.getString("depjob");
            String description = rs.getString("description");
            depWorkers.add(new DepWorker(depcode, depjob, description));
        }
        return depWorkers;
    }
}
