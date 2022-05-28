package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.Discipline;

@Repository
public class DisciplineRepository implements IRestRepository<Discipline> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name\" " +
            "FROM \"discipline\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name\"" +
            "FROM \"discipline\" " +
            "WHERE \"id\" = ?";

    private static String selectByNameQuery = "SELECT \"id\", \"name\" " +
            "FROM \"discipline\" " +
            "WHERE \"name\" = ?";

    private static String insertQuery = "INSERT INTO \"discipline\"(\"name\") " +
            "VALUES (?) " +
            "RETURNING \"id\", \"name\"";

    private static String updateQuery = "UPDATE \"discipline\" " +
            "SET \"name\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"discipline\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"name\"";

    public DisciplineRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Discipline[] select() {
        ArrayList<Discipline> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Discipline(
                rowSet.getInt(1),
                rowSet.getString(2)
            ));
        }
        Discipline[] result = new Discipline[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Discipline select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Discipline(
            rowSet.getInt(1),
            rowSet.getString(2)
        );
    }

    public Discipline[] selectByName(String name) {
        ArrayList<Discipline> values = new ArrayList<>();
        Object[] params = new Object[] { name };
        int[] types = new int[] { Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByNameQuery, params, types);
        while (rowSet.next()) {
            values.add(new Discipline(
                rowSet.getInt(1),
                rowSet.getString(2)
            ));
        }
        Discipline[] result = new Discipline[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Discipline insert(Discipline entity) {
        Object[] params = new Object[] { entity.getName() };
        int[] types = new int[] { Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Discipline(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Discipline update(Integer id, Discipline entity) {
        Object[] params = new Object[] { entity.getName(), id };
        int[] types = new int[] { Types.CHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Discipline(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Discipline delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Discipline(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}
