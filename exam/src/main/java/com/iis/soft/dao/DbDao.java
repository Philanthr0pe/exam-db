package com.iis.soft.dao;

import com.iis.soft.dao.dataSources.DbDataSource;
import com.iis.soft.dao.utils.ResultSetExtractor;
import com.iis.soft.models.DepWorker;

import java.sql.*;
import java.util.Set;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class DbDao {

    public Set<DepWorker> getAll() {
        Set<DepWorker> depWorkers = null;

        try (Connection connection = DbDataSource.getDataSource().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllStatement());
            depWorkers = new ResultSetExtractor().extractData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return depWorkers;
    }

    public void insertAll(Set<DepWorker> depWorkers) {

        final int batchSize = 1000;
        int count = 0;

        try (Connection connection = DbDataSource.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertStatement())){

            for (DepWorker depWorker : depWorkers) {
                preparedStatement.setString(1, depWorker.getDepCode());
                preparedStatement.setString(2, depWorker.getDepJob());
                preparedStatement.setString(3, depWorker.getDepDescription());
                preparedStatement.addBatch();

                if(++count % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String getAllStatement() {
        return "SELECT * FROM depworker";
    }

    private String insertStatement() {
        return "INSERT INTO depworker(depcode, depjob, description) " +
                "values (?, ? , ?)";
    }

}
