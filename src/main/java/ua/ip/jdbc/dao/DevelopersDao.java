package ua.ip.jdbc.dao;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
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
    private final PreparedStatement SALARY_ON_PROJECT_FROM_NAME;
    private final PreparedStatement SALARY_ON_PROJECT_FROM_ID;
    private final PreparedStatement PROGRAMMER_ON_PROJECT_FROM_NAME;
    private final PreparedStatement PROGRAMMER_ON_PROJECT_FROM_ID;
    private final PreparedStatement PROGRAMMER_ON_LANGUAGE_JAVA;
    private final PreparedStatement PROGRAMMER_ON_LANGUAGE;
    private final PreparedStatement PROGRAMMER_LEVEL;

    public DevelopersDao(DatabaseSqlManagerConnector sqlConnector) {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        try {
            INSERT_DEVELOPER = connection.prepareStatement("INSERT INTO developers(name,age,sex,salary) " +
                    "VALUES(?,?,?,?)");
            SELECT_DEVELOPER_BY_ID = connection.prepareStatement("SELECT id,name,age,sex,salary FROM developers " +
                    "WHERE id = ?");
            SELECT_ALL_DEVELOPERS = connection.prepareStatement("SELECT * FROM developers");
            UPDATE_DEVELOPER = connection.prepareStatement("UPDATE developers SET id = ?,name = ?," +
                    "age = ?,sex = ?, salary = ?  WHERE id = ?");
            DELETE_DEVELOPER = connection.prepareStatement("DELETE FROM developers WHERE id = ?");
            SALARY_ON_PROJECT_FROM_NAME = connection.prepareStatement("SELECT SUM(salary) FROM developers " +
                    "WHERE id IN (SELECT developer_id FROM developers_projects WHERE project_id IN " +
                    "(SELECT id FROM projects WHERE name = ?))");
            SALARY_ON_PROJECT_FROM_ID = connection.prepareStatement("SELECT SUM(salary) FROM developers " +
                    "WHERE id IN (SELECT developer_id FROM developers_projects WHERE project_id = ?)");
            PROGRAMMER_ON_PROJECT_FROM_NAME = connection.prepareStatement("SELECT id,name,age,sex,salary " +
                    "FROM developers WHERE id IN (SELECT developer_id FROM developers_projects WHERE project_id IN" +
                    "(SELECT id FROM projects WHERE name = ?))");
            PROGRAMMER_ON_PROJECT_FROM_ID = connection.prepareStatement("SELECT id,name,age,sex,salary " +
                    "FROM developers WHERE id IN (SELECT developer_id FROM developers_projects WHERE project_id = ?)");
            PROGRAMMER_ON_LANGUAGE_JAVA = connection.prepareStatement("SELECT id,name,age,sex,salary " +
                    "FROM developers WHERE id IN (SELECT developer_id FROM developers_skills WHERE skill_id IN" +
                    "(SELECT id FROM skills WHERE language = 'Java'))");
            PROGRAMMER_ON_LANGUAGE = connection.prepareStatement("SELECT id,name,age,sex,salary " +
                    "FROM developers WHERE id IN (SELECT developer_id FROM developers_skills WHERE skill_id IN" +
                    "(SELECT id FROM skills WHERE language = ?))");
            PROGRAMMER_LEVEL = connection.prepareStatement("SELECT id,name,age,sex,salary " +
                    "FROM developers WHERE id IN (SELECT developer_id FROM developers_skills WHERE skill_id IN " +
                    "(SELECT id FROM skills WHERE level Like ?))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean create(Developers entity) {
        try {
            INSERT_DEVELOPER.setString(1, entity.getName());
            INSERT_DEVELOPER.setInt(2, entity.getAge());
            INSERT_DEVELOPER.setString(3, entity.getSex());
            INSERT_DEVELOPER.setInt(4, entity.getSalary());
            return INSERT_DEVELOPER.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Developers findById(Integer id) {
        try {
            SELECT_DEVELOPER_BY_ID.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public Integer calculateSalaryOnProjectFromName(String name) {
        try {
            SALARY_ON_PROJECT_FROM_NAME.setString(1, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = SALARY_ON_PROJECT_FROM_NAME.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return rs.getInt("sum");
        } catch (Exception ex) {
            return null;
        }
    }

    public Integer calculateSalaryOnProjectFromId(Integer project_id) {
        try {
            SALARY_ON_PROJECT_FROM_ID.setInt(1, project_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = SALARY_ON_PROJECT_FROM_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return rs.getInt("sum");
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Developers> showProgrammerOnProjectFromProjectName(String projectName) {
        List<Developers> programmerOnProjectFromId = new ArrayList<>();
        try {
            PROGRAMMER_ON_PROJECT_FROM_NAME.setString(1, projectName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = PROGRAMMER_ON_PROJECT_FROM_NAME.executeQuery()) {
            while (rs.next()) {
                programmerOnProjectFromId.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerOnProjectFromId;
    }

    public List<Developers> showProgrammerOnProjectFromProjectId(Integer project_id){
        List<Developers> programmerOnProjectFromId = new ArrayList<>();
        try {
            PROGRAMMER_ON_PROJECT_FROM_ID.setInt(1, project_id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try (ResultSet rs = PROGRAMMER_ON_PROJECT_FROM_ID.executeQuery()) {
            while (rs.next()) {
                programmerOnProjectFromId.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerOnProjectFromId;
    }

    public List<Developers> showProgrammerOnLanguageJava() {
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

    public List<Developers> showProgrammerOnLanguage(String languageName){
        List<Developers> programmerOnProjectOnLanguageJava = new ArrayList<>();
        try {
            PROGRAMMER_ON_LANGUAGE.setString(1, languageName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = PROGRAMMER_ON_LANGUAGE.executeQuery()) {
            while (rs.next()) {
                programmerOnProjectOnLanguageJava.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerOnProjectOnLanguageJava;
    }

    public List<Developers> showProgrammersWithLevel(String level) {
        List<Developers> programmerLevel = new ArrayList<>();
        try {
            PROGRAMMER_LEVEL.setString(1, level);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = PROGRAMMER_LEVEL.executeQuery()) {
            while (rs.next()) {
                programmerLevel.add(convertDeveloper(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return programmerLevel;
    }

    private Developers convertDeveloper(ResultSet resultSet) {
        Developers developer = new Developers();
        try {
        developer.setId(resultSet.getInt("id"));
        developer.setName(resultSet.getString("name"));
        developer.setAge(resultSet.getInt("age"));
        developer.setSex(resultSet.getString("sex"));
        developer.setSalary(resultSet.getInt("salary"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return developer;
    }
}
