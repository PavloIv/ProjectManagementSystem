package ua.ip.jdbc.dao;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Projects;
import ua.ip.jdbc.table.ProjectsFormatCreationDateNameNumberProgrammer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectsDao implements ServiceCrud<Projects> {
    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_PROJECT;
    private final PreparedStatement SELECT_PROJECT_BY_ID;
    private final PreparedStatement SELECT_ALL_PROJECTS;
    private final PreparedStatement UPDATE_PROJECT;
    private final PreparedStatement DELETE_PROJECT;
    private final PreparedStatement PROJECT_LIST;

    public ProjectsDao(DatabaseSqlManagerConnector sqlConnector) {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        try {
            INSERT_PROJECT = connection.prepareStatement("INSERT INTO projects" +
                    "(id,name,description,cost,company_id,customer_id) VALUES(?,?,?,?,?,?)");
            SELECT_PROJECT_BY_ID = connection.prepareStatement("SELECT id,name,description,cost,company_id," +
                    "customer_id FROM projects WHERE id = ?");
            SELECT_ALL_PROJECTS = connection.prepareStatement("SELECT * FROM projects");
            UPDATE_PROJECT = connection.prepareStatement("UPDATE projects SET id = ?,name = ?,description = ?," +
                    "cost = ?, company_id = ?,customer_id = ?  WHERE id = ?");
            DELETE_PROJECT = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
            PROJECT_LIST = connection.prepareStatement("SELECT  creation_date,name," +
                    "COUNT(developers_projects.developer_id) as Number_programer_on_project " +
                    "FROM projects LEFT JOIN developers_projects ON projects.id = developers_projects.project_id " +
                    "GROUP BY projects.id " +
                    "ORDER by projects.id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public Projects findById(Integer id){
        try {
            SELECT_PROJECT_BY_ID.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public List<ProjectsFormatCreationDateNameNumberProgrammer> showProjectsListInFormat() {
        List<ProjectsFormatCreationDateNameNumberProgrammer> programmerLevel = new ArrayList<>();
        try (ResultSet rs = PROJECT_LIST.executeQuery()) {
            while (rs.next()) {
                programmerLevel.add(convertProjectsV2(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerLevel;
    }

    private ProjectsFormatCreationDateNameNumberProgrammer convertProjectsV2(ResultSet resultSet) throws SQLException {
        ProjectsFormatCreationDateNameNumberProgrammer projectsFormatCreationDateNameNumberProgramer =
                new ProjectsFormatCreationDateNameNumberProgrammer();
        projectsFormatCreationDateNameNumberProgramer
                .setCreationDate(String.valueOf(resultSet.getDate("creation_date")));
        projectsFormatCreationDateNameNumberProgramer
                .setName(resultSet.getString("name"));
        projectsFormatCreationDateNameNumberProgramer
                .setNumberProgrammerOnProject(resultSet.getInt("Number_programer_on_project"));

        return projectsFormatCreationDateNameNumberProgramer;
    }
}
