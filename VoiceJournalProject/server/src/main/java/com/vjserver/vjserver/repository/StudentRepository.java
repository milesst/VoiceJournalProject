package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.User;

@Repository
public class StudentRepository implements IRestRepository<Student> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\" " +
            "FROM \"student\" " +
            "ORDER BY \"id\" ";

    private static String selectByIdQuery = "SELECT \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\"" +
            "FROM \"student\" " +
            "WHERE \"id\" = ?";

    private static String selectByGroupIdQuery = "SELECT \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\"" +
        "FROM \"student\" " +
        "WHERE \"group_id\" = ?";        

    // private static String selectByNameQuery = "SELECT \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\" " +
    //         "FROM \"user\" " +
    //         "WHERE \"name\" = ?";

    private static String insertQuery = "INSERT INTO \"student\"(\"group_id\", \"first_name\", \"last_name\", \"patronymic\") " +
            "VALUES (?, ?, ?, ?) " +
            "RETURNING \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\" ";

    private static String updateQuery = "UPDATE \"student\" " +
            "SET \"group_id\" = ?, \"first_name\" = ?, \"last_name\" = ?, \"patronymic\" = ? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\" ";

    private static String deleteQuery = "DELETE FROM \"student\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_id\", \"first_name\", \"last_name\", \"patronymic\" ";

    public StudentRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Student[] select() {
        ArrayList<Student> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Student(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
            ));
        }
        Student[] result = new Student[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Student select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Student(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5)
        );
    }

    public Student[] selectByGroupId(Integer id) {
        ArrayList<Student> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByGroupIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Student(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5)
            ));
        }
        Student[] result = new Student[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Student insert(Student entity) {
        Object[] params = new Object[] { entity.getGroupId(), entity.getFirstName(), entity.getLastName(), entity.getPatronymic() };
        int[] types = new int[] { Types.INTEGER, Types.CHAR, Types.CHAR, Types.CHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Student(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5)
        );
    }

    @Override
    public Student update(Integer id, Student entity) {
        Object[] params = new Object[] { entity.getGroupId(), entity.getFirstName(), entity.getLastName(), entity.getPatronymic(), id };
        int[] types = new int[] { Types.INTEGER, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Student(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5)
        );
    }

    @Override
    public Student delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Student(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5)
        );
    }
}
