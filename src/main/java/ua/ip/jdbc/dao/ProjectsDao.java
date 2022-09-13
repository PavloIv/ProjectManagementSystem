package ua.ip.jdbc.dao;

import ua.ip.jdbc.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDao implements ServiceCrud<Projects>{
    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_PROJECT;
    private final PreparedStatement SELECT_PROJECT_BY_ID;
    private final PreparedStatement SELECT_ALL_PROJECTS;
    private final PreparedStatement UPDATE_PROJECT;
    private final PreparedStatement DELETE_PROJECT;

    public ProjectsDao(DatabaseSqlManagerConnector sqlConnector) throws SQLException {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        INSERT_PROJECT = connection.prepareStatement("INSERT INTO projects" +
                "(id,name,description,cost,company_id,customer_id) VALUES(?,?,?,?,?,?)");
        SELECT_PROJECT_BY_ID = connection.prepareStatement("SELECT id,name,description,cost,company_id,customer_id" +
                " FROM projects WHERE id = ?"); ;
        SELECT_ALL_PROJECTS = connection.prepareStatement("SELECT * FROM projects");
        UPDATE_PROJECT = connection.prepareStatement("UPDATE projects SET id = ?,name = ?," + "description = ?," +
                "cost = ?, company_id = ?,customer_id = ?  WHERE id = ?");
        DELETE_PROJECT = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
    }

    @Override
    public boolean create(Projects entity) {
        try {
            INSERT_PROJECT.setInt(1, entity.getId());
            INSERT_PROJECT.setString(2, entity.getName());
            INSERT_PROJECT.setString(3, entity.getDescription());
            INSERT_PROJECT.setInt(4, entity.getCost());
            INSERT_PROJECT.setInt(5, entity.getCompany_id());
            INSERT_PROJECT.setInt(6, entity.getCustomer_id());

            return INSERT_PROJECT.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Projects findById(Integer id) throws SQLException {
        SELECT_PROJECT_BY_ID.setInt(1, id);
        try (ResultSet rs = SELECT_PROJECT_BY_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return convertProjects(rs);
        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public List<Projects> findAll() {
        List<Projects> allProjects = new ArrayList<>();
        try (ResultSet rs = SELECT_ALL_PROJECTS.executeQuery()) {
            while (rs.next()) {
                allProjects.add(convertProjects(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allProjects;
    }

    @Override
    public boolean update(Projects entity) {
        try {
            UPDATE_PROJECT.setInt(1, entity.getId());
            UPDATE_PROJECT.setString(2, entity.getName());
            UPDATE_PROJECT.setString(3, entity.getDescription());
            UPDATE_PROJECT.setInt(4, entity.getCost());
            UPDATE_PROJECT.setInt(5, entity.getCompany_id());
            UPDATE_PROJECT.setInt(6, entity.getCustomer_id());
            UPDATE_PROJECT.setInt(7, entity.getId());

            return UPDATE_PROJECT.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DELETE_PROJECT.setInt(1, id);
            return DELETE_PROJECT.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Projects convertProjects(ResultSet rs) throws SQLException {
        Projects project = new Projects();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setCost(rs.getInt("cost"));
        project.setCompany_id(rs.getInt("company_id"));
        project.setCustomer_id(rs.getInt("customer_id"));
        return project;
    }
}
