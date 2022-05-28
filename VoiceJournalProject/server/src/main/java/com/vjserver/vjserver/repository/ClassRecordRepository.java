package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.ClassRecord;
import com.vjserver.vjserver.entity.Discipline;

@Repository
public class ClassRecordRepository implements IRestRepository<ClassRecord> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"schedule_id\", \"start_date\", \"end_date\" " +
            "FROM \"class_record\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"schedule_id\", \"start_date\", \"end_date\" " +
            "FROM \"class_record\" " +
            "WHERE \"id\" = ?";

    private static String selectByScheduleIdQuery = "SELECT \"id\", \"schedule_id\", \"start_date\", \"end_date\" " +
        "FROM \"class_record\" " +
        "WHERE \"schedule_id\" = ?";

    private static String insertQuery = "INSERT INTO \"class_record\"(\"schedule_id\", \"start_date\", \"end_date\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"schedule_id\", \"start_date\", \"end_date\" ";

    private static String updateQuery = "UPDATE \"class_record\" " +
            "SET \"schedule_id\" = ?, \"start_date\" = ?, \"end_date\" = ? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"schedule_id\", \"start_date\", \"end_date\" ";

    private static String deleteQuery = "DELETE FROM \"discipline\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"schedule_id\", \"start_date\", \"end_date\" ";

    public ClassRecordRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public ClassRecord[] select() {
        ArrayList<ClassRecord> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new ClassRecord(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getTimestamp(3),
                rowSet.getTimestamp(4)
            ));
        }
        ClassRecord[] result = new ClassRecord[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public ClassRecord select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new ClassRecord(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getTimestamp(3),
            rowSet.getTimestamp(4)
        );
    }

    public ClassRecord[] selectByScheduleId(Integer id) {
        ArrayList<ClassRecord> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByScheduleIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new ClassRecord(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getTimestamp(3),
                rowSet.getTimestamp(4)
            ));
        }
        ClassRecord[] result = new ClassRecord[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public ClassRecord insert(ClassRecord entity) {
        Object[] params = new Object[] { entity.getScheduleId(), entity.getStartDate(), entity.getEndDate() };
        int[] types = new int[] { Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new ClassRecord(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getTimestamp(3),
                rowSet.getTimestamp(4)
        );
    }

    @Override
    public ClassRecord update(Integer id, ClassRecord entity) {
        Object[] params = new Object[] { entity.getScheduleId(), entity.getStartDate(), entity.getEndDate(), id };
        int[] types = new int[] { Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new ClassRecord(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getTimestamp(3),
                rowSet.getTimestamp(4)
        );
    }

    @Override
    public ClassRecord delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new ClassRecord(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getTimestamp(3),
                rowSet.getTimestamp(4)
        );
    }
}
