package com.vjserver.vjserver.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

import com.vjserver.vjserver.entity.User;

@Repository
public class UserRepository implements IRestRepository<User> {
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\" " +
            "FROM \"user_account\" " +
            "ORDER BY \"id\" ";

    private static String selectByIdQuery = "SELECT \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\"" +
            "FROM \"user_account\" " +
            "WHERE \"id\" = ?";

    private static String selectByUsernameQuery = "SELECT \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\"" +
        "FROM \"user_account\" " +
        "WHERE \"user_login\" = ?";

    private static String insertQuery = "INSERT INTO \"user_account\"(\"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\") " +
            "VALUES (?, ?, ?, ?, ?, ?) " +
            "RETURNING \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\"";

    private static String updateQuery = "UPDATE \"user_account\" " +
            "SET \"user_login\" = ?, \"user_password\" = ?, \"first_name\" = ?, \"last_name\" = ?, \"patronymic\" = ?, \"role\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\"";

    private static String deleteQuery = "DELETE FROM \"user_account\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"user_login\", \"user_password\", \"first_name\", \"last_name\", \"patronymic\", \"role\" ";

    public UserRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public User[] select() {
        ArrayList<User> values = new ArrayList<>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new User(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5),
                rowSet.getString(6),
                rowSet.getString(7)
            ));
        }
        User[] result = new User[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public User select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new User(
            rowSet.getInt(1),
            rowSet.getString(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5),
            rowSet.getString(6),
            rowSet.getString(7)
        );
    }

    public User selectByUsername(String username) {
        Object[] params = new Object[] { username };
        int[] types = new int[] { Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByUsernameQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new User(
            rowSet.getInt(1),
            rowSet.getString(2),
            rowSet.getString(3),
            rowSet.getString(4),
            rowSet.getString(5),
            rowSet.getString(6),
            rowSet.getString(7)
        );
    }

    @Override
    public User insert(User entity) {
        Object[] params = new Object[] { entity.getUserLogin(), entity.getUserPassword(), entity.getFirstName(), entity.getLastName(), entity.getPatronymic(), entity.getRole() };
        int[] types = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new User(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5),
                rowSet.getString(6),
                rowSet.getString(7)
        );
    }

    @Override
    public User update(Integer id, User entity) {
        Object[] params = new Object[] { entity.getUserLogin(), entity.getUserPassword(), entity.getFirstName(), entity.getLastName(), entity.getPatronymic(), entity.getRole(), id };
        int[] types = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new User(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5),
                rowSet.getString(6),
                rowSet.getString(7)
        );
    }

    @Override
    public User delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new User(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4),
                rowSet.getString(5),
                rowSet.getString(6),
                rowSet.getString(7)
        );
    }
}
