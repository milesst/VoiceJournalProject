package com.vjserver.vjserver.repository;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.StudentGroup;

@Repository
public class StudentGroupRepository implements IRestRepository<StudentGroup> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"group_number\" " +
            "FROM \"student_group\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"group_number\"" +
            "FROM \"student_group\" " +
            "WHERE \"id\" = ?";

    private static String selectByGroupNumberQuery = "SELECT \"id\", \"group_number\" " +
            "FROM \"student_group\" " +
            "WHERE \"group_number\" = ?";

    private static String insertQuery = "INSERT INTO \"student_group\"(\"group_number\") " +
            "VALUES (?) " +
            "RETURNING \"id\", \"group_number\"";

    private static String updateQuery = "UPDATE \"student_group\" " +
            "SET \"group_number\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_number\"";

    private static String deleteQuery = "DELETE FROM \"student_group\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_number\"";

    public StudentGroupRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public StudentGroup[] select() {
        ArrayList<StudentGroup> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new StudentGroup(
                rowSet.getInt(1),
                rowSet.getString(2)
            ));
        }
        StudentGroup[] result = new StudentGroup[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public StudentGroup select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentGroup(
            rowSet.getInt(1),
            rowSet.getString(2)
        );
    }

    public StudentGroup[] selectByGroupNumber(String name) {
        ArrayList<StudentGroup> values = new ArrayList<>();
        Object[] params = new Object[] { name };
        int[] types = new int[] { Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByGroupNumberQuery, params, types);
        while (rowSet.next()) {
            values.add(new StudentGroup(
                rowSet.getInt(1),
                rowSet.getString(2)
            ));
        }
        StudentGroup[] result = new StudentGroup[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public StudentGroup insert(StudentGroup entity) {
        Object[] params = new Object[] { entity.getGroupNumber() };
        int[] types = new int[] { Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentGroup(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public StudentGroup update(Integer id, StudentGroup entity) {
        Object[] params = new Object[] { entity.getGroupNumber(), id };
        int[] types = new int[] { Types.CHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentGroup(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public StudentGroup delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentGroup(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}
