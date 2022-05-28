package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.Schedule;
import com.vjserver.vjserver.entity.Student;
import com.vjserver.vjserver.entity.User;

@Repository
public class ScheduleRepository implements IRestRepository<Schedule> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\" " +
            "FROM \"schedule\" " +
            "ORDER BY \"id\" ";

    private static String selectByIdQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
            "FROM \"schedule\" " +
            "WHERE \"id\" = ?";

    private static String selectByTeacherQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"teacher_id\" = ?";

    private static String selectByDisciplineTeacherIdQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"discipline_id\" = ? AND \"teacher_id\" = ?";

    private static String selectByGroupIdAndDisciplineIdQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"group_id\" = ? AND \"discipline_id\" = ?";

    private static String selectByGroupDisciplineTeacherQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"group_id\" = ? AND \"discipline_id\" = ? AND \"teacher_id\" = ?";

    private static String selectByDisciplineIdQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"discipline_id\" = ?";

    private static String selectByGroupIdQuery = "SELECT \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\"" +
    "FROM \"schedule\" " +
    "WHERE \"group_id\" = ?";

    private static String insertQuery = "INSERT INTO \"schedule\"(\"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
            "RETURNING \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\" ";

    private static String updateQuery = "UPDATE \"schedule\" " +
            "SET \"group_id\" = ?, \"discipline_id\" = ?, \"teacher_id\" = ?, \"time\" = ?, \"day_name\" = ?, \"frequency\" = ?, \"classroom\" = ? " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\" ";

    private static String deleteQuery = "DELETE FROM \"schedule\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"group_id\", \"discipline_id\", \"teacher_id\", \"time\", \"day_name\", \"frequency\", \"classroom\" ";

    public ScheduleRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Schedule[] select() {
        ArrayList<Schedule> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Schedule select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Schedule(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getInt(4),
            rowSet.getTime(5),
            rowSet.getString(6),
            rowSet.getShort(7),
            rowSet.getShort(8)
        );
    }

    public Schedule[] selectByTeacherId(Integer id) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByTeacherQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByGroupId(Integer id) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByGroupIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByDisciplineId(Integer id) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByDisciplineIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByDisciplineTeacherId(Integer disId, Integer teachId) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { disId, teachId };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByDisciplineTeacherIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByGroupIdAndDisciplineId(Integer groupId, Integer disciplineId) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { groupId, disciplineId };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByGroupIdAndDisciplineIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    public Schedule[] selectByGroupDisciplineTeacher(Integer groupId, Integer disciplineId, Integer teacherId) {
        ArrayList<Schedule> values = new ArrayList<>();
        Object[] params = new Object[] { groupId, disciplineId, teacherId };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByGroupDisciplineTeacherQuery, params, types);
        while (rowSet.next()) {
            values.add(new Schedule(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3),
                rowSet.getInt(4),
                rowSet.getTime(5),
                rowSet.getString(6),
                rowSet.getShort(7),
                rowSet.getShort(8)
            ));
        }
        Schedule[] result = new Schedule[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Schedule insert(Schedule entity) {
        Object[] params = new Object[] { entity.getGroupId(), entity.getDisciplineId(), entity.getTeacherId(), entity.getTime(), entity.getDayName(), entity.getFrequency(), entity.getClassroom() };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIME, Types.VARCHAR, Types.SMALLINT, Types.SMALLINT};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Schedule(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getInt(4),
            rowSet.getTime(5),
            rowSet.getString(6),
            rowSet.getShort(7),
            rowSet.getShort(8)
        );
    }

    @Override
    public Schedule update(Integer id, Schedule entity) {
        Object[] params = new Object[] { entity.getGroupId(), entity.getDisciplineId(), entity.getTeacherId(), entity.getTime(), entity.getDayName(), entity.getFrequency(), entity.getClassroom(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIME, Types.VARCHAR, Types.SMALLINT, Types.SMALLINT, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Schedule(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getInt(4),
            rowSet.getTime(5),
            rowSet.getString(6),
            rowSet.getShort(7),
            rowSet.getShort(8)
        );
    }

    @Override
    public Schedule delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Schedule(
            rowSet.getInt(1),
            rowSet.getInt(2),
            rowSet.getInt(3),
            rowSet.getInt(4),
            rowSet.getTime(5),
            rowSet.getString(6),
            rowSet.getShort(7),
            rowSet.getShort(8)
        );
    }
}
