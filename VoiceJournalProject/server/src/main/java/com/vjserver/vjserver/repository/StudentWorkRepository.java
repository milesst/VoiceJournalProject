package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.StudentGroup;
import com.vjserver.vjserver.entity.StudentWork;

@Repository
public class StudentWorkRepository implements IRestRepository<StudentWork> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" " +
            "FROM \"student_work\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" " +
            "FROM \"student_work\" " +
            "WHERE \"id\" = ?";

    private static String selectByClassRecordIdQuery = "SELECT \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" " +
        "FROM \"student_work\" " +
        "WHERE \"class_record_id\" = ?";  
    
    private static String selectByStudentIdQuery = "SELECT \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" " +
    "FROM \"student_work\" " +
    "WHERE \"student_id\" = ?";      
    // private static String selectByGroupNumberQuery = "SELECT \"id\", \"group_number\" " +
    //         "FROM \"student_work\" " +
    //         "WHERE \"group_number\" = ?";

    private static String insertQuery = "INSERT INTO \"student_work\"(\"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\") " +
            "VALUES (?, ?, ?, ?, ?) " +
            "RETURNING \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\"";

    private static String updateQuery = "UPDATE \"student_work\" " +
            "SET \"student_id\" = ?, \"class_record_id\" = ?, \"attendance\" = ?, \"note\" = ?, \"grade\" = ? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" ";

    private static String deleteQuery = "DELETE FROM \"student_work\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"student_id\", \"class_record_id\", \"attendance\", \"note\", \"grade\" ";

    public StudentWorkRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public StudentWork[] select() {
        ArrayList<StudentWork> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new StudentWork(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getBoolean(4),
                rowSet.getString(5),
                rowSet.getInt(6)
            ));
        }
        StudentWork[] result = new StudentWork[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public StudentWork select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentWork(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getBoolean(4),
            rowSet.getString(5),
            rowSet.getInt(6)
        );
    }

    public StudentWork[] selectByClassRecordId(Integer id) {
        ArrayList<StudentWork> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByClassRecordIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new StudentWork(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getBoolean(4),
                rowSet.getString(5),
                rowSet.getInt(6)
            ));
        }
        StudentWork[] result = new StudentWork[values.size()];
        result = values.toArray(result);
        return result;
    }

    public StudentWork[] selectByStudentId(Integer id) {
        ArrayList<StudentWork> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByStudentIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new StudentWork(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getBoolean(4),
                rowSet.getString(5),
                rowSet.getInt(6)
            ));
        }
        StudentWork[] result = new StudentWork[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public StudentWork insert(StudentWork entity) {
        Object[] params = new Object[] { entity.getStudentId(), entity.getClassRecordId(), entity.getAttendance(), entity.getNote(), entity.getGrade() };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.BOOLEAN, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentWork(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getBoolean(4),
            rowSet.getString(5),
            rowSet.getInt(6)
        );
    }

    @Override
    public StudentWork update(Integer id, StudentWork entity) {
        Object[] params = new Object[] { entity.getStudentId(), entity.getClassRecordId(), entity.getAttendance(), entity.getNote(), entity.getGrade(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.BOOLEAN, Types.VARCHAR, Types.SMALLINT, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentWork(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getBoolean(4),
            rowSet.getString(5),
            rowSet.getInt(6)
        );
    }

    @Override
    public StudentWork delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new StudentWork(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getBoolean(4),
            rowSet.getString(5),
            rowSet.getInt(6)
        );
    }
}
