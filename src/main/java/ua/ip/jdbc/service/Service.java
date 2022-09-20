package ua.ip.jdbc.service;

import ua.ip.jdbc.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Developers;
import ua.ip.jdbc.table.ProjectsFormatCreationDateNameNumberProgramer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final DatabaseSqlManagerConnector sqlConnector;

    private PreparedStatement SALARY_ON_PROJECT_FROM_NAME;
    private PreparedStatement SALARY_ON_PROJECT_FROM_ID;
    private PreparedStatement PROGRAMMER_ON_PROJECT_FROM_NAME;
    private PreparedStatement PROGRAMMER_ON_PROJECT_FROM_ID;
    private PreparedStatement PROGRAMMER_ON_LANGUAGE_JAVA;
    private PreparedStatement PROGRAMMER_ON_LANGUAGE;
    private PreparedStatement PROGRAMMER_LEVEL;
    private PreparedStatement PROJECT_LIST;

    public Service(DatabaseSqlManagerConnector sqlConnector) throws SQLException {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        SALARY_ON_PROJECT_FROM_NAME = connection.prepareStatement("SELECT SUM(salary) FROM developers WHERE id IN" +
        "(SELECT developer_id FROM developers_projects WHERE project_id IN (SELECT id FROM projects WHERE name = '?'))");
        SALARY_ON_PROJECT_FROM_ID = connection.prepareStatement("SELECT SUM(salary) FROM developers WHERE id IN " +
                "(SELECT developer_id FROM developers_projects WHERE project_id = ?)");
        PROGRAMMER_ON_PROJECT_FROM_NAME = connection.prepareStatement("SELECT id,name FROM developers WHERE id IN" +
                "(SELECT developer_id FROM developers_projects WHERE project_id IN" +
                "(SELECT id FROM projects WHERE name = '?'))");
        PROGRAMMER_ON_PROJECT_FROM_ID = connection.prepareStatement("SELECT id,name,age,sex,salary FROM developers WHERE id IN" +
                "(SELECT developer_id FROM developers_projects WHERE project_id = ?)");
        PROGRAMMER_ON_LANGUAGE_JAVA = connection.prepareStatement("SELECT id,name,age,sex,salary FROM developers WHERE id IN" +
                "(SELECT developer_id FROM developers_skills WHERE skill_id IN" +
                "(SELECT id FROM skills WHERE language = 'Java'))");
        PROGRAMMER_ON_LANGUAGE = connection.prepareStatement("SELECT id,name FROM developers WHERE id IN" +
                "(SELECT developer_id FROM developers_skills WHERE skill_id IN" +
                "(SELECT id FROM skills WHERE language = '?'))");
        PROGRAMMER_LEVEL = connection.prepareStatement("SELECT id,name FROM developers WHERE id IN " +
                "(SELECT developer_id FROM developers_skills WHERE skill_id IN " +
                "(SELECT id FROM skills WHERE level Like '?'))");
        PROJECT_LIST = connection.prepareStatement("SELECT  creation_date,name,COUNT(developers_projects.developer_id) as Number_programer_on_project " +
                "FROM projects LEFT JOIN developers_projects ON projects.id = developers_projects.project_id " +
                "GROUP BY projects.id " +
                "ORDER by projects.id");

    }

    public Integer SalaryOnProjectFromName(String name) throws SQLException {
        SALARY_ON_PROJECT_FROM_NAME.setString(1,name );
        try (ResultSet rs = SALARY_ON_PROJECT_FROM_NAME.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return rs.getInt("sum");
        } catch (Exception ex) {
            return null;
        }
    }
    public Integer SalaryOnProjectFromId(int project_id) throws SQLException {
        SALARY_ON_PROJECT_FROM_ID.setInt(1,project_id );
        try (ResultSet rs = SALARY_ON_PROJECT_FROM_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return rs.getInt("sum");
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Developers> programmerOnProjectFromId(int project_id) throws SQLException {
        List<Developers> programmerOnProjectFromId = new ArrayList<>();
        PROGRAMMER_ON_PROJECT_FROM_ID.setInt(1,project_id);
        try (ResultSet rs = PROGRAMMER_ON_PROJECT_FROM_ID.executeQuery()) {
            while (rs.next()) {
                programmerOnProjectFromId.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerOnProjectFromId;
    }

    public List<Developers> programmerOnLanguageJava() throws SQLException {
        List<Developers> programmerOnProjectOnLanguageJava = new ArrayList<>();
        try (ResultSet rs = PROGRAMMER_ON_LANGUAGE_JAVA.executeQuery()) {
            while (rs.next()) {
                programmerOnProjectOnLanguageJava.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerOnProjectOnLanguageJava;
    }

    public List<Developers> programmerLevel(String level) throws SQLException {
        List<Developers> programmerLevel = new ArrayList<>();
        PROGRAMMER_LEVEL.setString(1,level);
        try (ResultSet rs = PROGRAMMER_LEVEL.executeQuery()) {
            while (rs.next()) {
                programmerLevel.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerLevel;
    }

    public List<ProjectsFormatCreationDateNameNumberProgramer> projectsList() throws SQLException {
        List<ProjectsFormatCreationDateNameNumberProgramer> programmerLevel = new ArrayList<>();
        try (ResultSet rs = PROJECT_LIST.executeQuery()) {
            while (rs.next()) {
                programmerLevel.add(convertProjectsV2(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerLevel;
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

    private ProjectsFormatCreationDateNameNumberProgramer convertProjectsV2(ResultSet resultSet) throws SQLException {
        ProjectsFormatCreationDateNameNumberProgramer projectsFormatCreationDateNameNumberProgramer = new ProjectsFormatCreationDateNameNumberProgramer();
        projectsFormatCreationDateNameNumberProgramer.setCreationDate(String.valueOf(resultSet.getDate("creation_date")));
        projectsFormatCreationDateNameNumberProgramer.setName(resultSet.getString("name"));
        projectsFormatCreationDateNameNumberProgramer.setNumberProgramerOnProject(resultSet.getInt("Number_programer_on_project"));
        return projectsFormatCreationDateNameNumberProgramer;
    }

}
