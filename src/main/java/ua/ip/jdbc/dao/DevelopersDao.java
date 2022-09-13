package ua.ip.jdbc.dao;

import ua.ip.jdbc.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Developers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DevelopersDao implements ServiceCrud<Developers> {
    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_DEVELOPER;
    private final PreparedStatement SELECT_DEVELOPER_BY_ID;
    private final PreparedStatement SELECT_ALL_DEVELOPERS;
    private final PreparedStatement UPDATE_DEVELOPER;
    private final PreparedStatement DELETE_DEVELOPER;

    public DevelopersDao(DatabaseSqlManagerConnector sqlConnector) throws SQLException {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        INSERT_DEVELOPER = connection.prepareStatement("INSERT INTO developers(id,name,age,sex,salary) VALUES(?,?,?,?,?)");
        SELECT_DEVELOPER_BY_ID = connection.prepareStatement("SELECT id,name,age,sex,salary FROM developers WHERE id = ?");
        SELECT_ALL_DEVELOPERS = connection.prepareStatement("SELECT * FROM developers");
        UPDATE_DEVELOPER = connection.prepareStatement("UPDATE developers SET id = ?,name = ?," + "age = ?,sex = ?, salary = ?  WHERE id = ?");
        DELETE_DEVELOPER = connection.prepareStatement("DELETE FROM developers WHERE id = ?");
    }

    @Override
    public boolean create(Developers entity) {
        try {
            INSERT_DEVELOPER.setInt(1, entity.getId());
            INSERT_DEVELOPER.setString(2, entity.getName());
            INSERT_DEVELOPER.setInt(3, entity.getAge());
            INSERT_DEVELOPER.setString(4, entity.getSex());
            INSERT_DEVELOPER.setInt(5, entity.getSalary());
            return INSERT_DEVELOPER.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Developers findById(Integer id) throws SQLException {
        SELECT_DEVELOPER_BY_ID.setInt(1, id);
        try (ResultSet rs = SELECT_DEVELOPER_BY_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return convertDeveloper(rs);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Developers> findAll() {
        List<Developers> allDevelopers = new ArrayList<>();
        try (ResultSet rs = SELECT_ALL_DEVELOPERS.executeQuery()) {
            while (rs.next()) {
                allDevelopers.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allDevelopers;
    }

    @Override
    public boolean update(Developers entity) {
        try {
            UPDATE_DEVELOPER.setInt(1, entity.getId());
            UPDATE_DEVELOPER.setString(2, entity.getName());
            UPDATE_DEVELOPER.setInt(3, entity.getAge());
            UPDATE_DEVELOPER.setString(4, entity.getSex());
            UPDATE_DEVELOPER.setInt(5, entity.getSalary());
            UPDATE_DEVELOPER.setInt(6, entity.getId());

            return UPDATE_DEVELOPER.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DELETE_DEVELOPER.setInt(1, id);
            return DELETE_DEVELOPER.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Developers convertDeveloper(ResultSet resultSet) throws SQLException {
        Developers developer = new Developers();
        developer.setId(resultSet.getInt("id"));
        developer.setName(resultSet.getString("name"));
        developer.setAge(resultSet.getInt("age"));
        developer.setSex(resultSet.getString("sex"));
        developer.setSalary(resultSet.getInt("salary"));
        return developer;
    }
}
