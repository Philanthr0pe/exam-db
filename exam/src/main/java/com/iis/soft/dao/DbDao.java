package com.iis.soft.dao;

import com.iis.soft.dao.dataSources.DbDataSource;
import com.iis.soft.dao.utils.ResultSetExtractor;
import com.iis.soft.models.DepWorker;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vladislav on 28-Apr-17.
 */
public class DbDao {

    private DbDataSource dbDataSource;

    final static Logger logger = Logger.getLogger(DbDao.class);

    public DbDao() {
        dbDataSource = new DbDataSource();
    }

    /**
     * @return - all objects from database
     */
    public Set<DepWorker> getAll() {
        Set<DepWorker> depWorkers = new HashSet<>();
        Connection connection = dbDataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllStatement())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            depWorkers = new ResultSetExtractor().extractData(resultSet);
        } catch (SQLException e) {
            logger.error("DbDao getAll error: ", e);
        }

        logger.info("Select all");
        return depWorkers;
    }

    /**
     * @param depWorkers - objects for insert into database
     */
    public void insertAll(Set<DepWorker> depWorkers) {

        final int batchSize = 1000;
        int count = 0;

        Connection connection = dbDataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement())) {

            for (DepWorker depWorker : depWorkers) {
                preparedStatement.setString(1, depWorker.getDepCode());
                preparedStatement.setString(2, depWorker.getDepJob());
                preparedStatement.setString(3, depWorker.getDepDescription());
                preparedStatement.addBatch();

                if (++count % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();

            logger.info("Insert data");
        } catch (SQLException e) {
            logger.error("DbDao insertAll error: ", e);
        }
    }

    /**
     * @param depIds - array with identifiers to delete objects in the database
     */
    public void deleteAllById(int[] depIds) {

        final int batchSize = 1000;
        int count = 0;

        Connection connection = dbDataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement())) {

            for (Integer id : depIds) {
                preparedStatement.setInt(1, id.intValue());
                preparedStatement.addBatch();

                if (++count % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();

            logger.info("Delete data");
        } catch (SQLException e) {
            logger.error("DbDao deleteAll error: ", e);
        }
    }

    /**
     * @param depWorkers - set of objects for update data in database
     */
    public void updateAll(Set<DepWorker> depWorkers) {
        final int batchSize = 1000;
        int count = 0;

        Connection connection = dbDataSource.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement())) {

            for (DepWorker depWorker : depWorkers) {
                preparedStatement.setString(1, depWorker.getDepDescription());
                preparedStatement.setInt(2, depWorker.getId());
                preparedStatement.addBatch();

                if (++count % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
            preparedStatement.executeBatch();
            logger.info("Update data");
        } catch (SQLException e) {
            logger.error("DbDao updateAll error: ", e);
        }
    }

    public void close() {
        try {
            dbDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String deleteStatement() {
        return "DELETE from depworker WHERE id = ?";
    }

    private String updateStatement() {
        return "update depworker set description = ? where id = ?";

    }

    private String getAllStatement() {
        return "SELECT * FROM depworker";
    }

    private String insertStatement() {
        return "INSERT INTO depworker(depcode, depjob, description) " +
                "values (?, ? , ?)";
    }

}
