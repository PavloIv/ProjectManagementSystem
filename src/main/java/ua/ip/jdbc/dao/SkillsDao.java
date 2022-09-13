package ua.ip.jdbc.dao;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Skills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsDao implements ServiceCrud<Skills>{
    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_SKILL;
    private final PreparedStatement SELECT_SKILL_BY_ID;
    private final PreparedStatement SELECT_ALL_SKILLS;
    private final PreparedStatement UPDATE_SKILL;
    private final PreparedStatement DELETE_SKILL;

    public SkillsDao(DatabaseSqlManagerConnector sqlConnector) throws SQLException {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        INSERT_SKILL = connection.prepareStatement("INSERT INTO skills(id,language,level) VALUES(?,?,?)");
        SELECT_SKILL_BY_ID = connection.prepareStatement("SELECT id,language,level FROM skills WHERE id = ?");
        SELECT_ALL_SKILLS = connection.prepareStatement("SELECT * FROM skills");
        UPDATE_SKILL = connection.prepareStatement("UPDATE skills SET id = ?,language = ?,level = ? WHERE id = ?");
        DELETE_SKILL = connection.prepareStatement("DELETE FROM skills WHERE id = ?");
    }


    @Override
    public boolean create(Skills entity) {
    try {
        INSERT_SKILL.setInt(1, entity.getId());
        INSERT_SKILL.setString(2, entity.getLanguage());
        INSERT_SKILL.setString(3, entity.getLevel());
        return INSERT_SKILL.executeUpdate() == 1;
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return false;
    }

    @Override
    public Skills findById(Integer id) throws SQLException {
        SELECT_SKILL_BY_ID.setInt(1, id);
        try (ResultSet rs = SELECT_SKILL_BY_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return convertSkill(rs);
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public List<Skills> findAll() {
        List<Skills> allDevelopers = new ArrayList<>();
        try (ResultSet rs = SELECT_ALL_SKILLS.executeQuery()) {
            while (rs.next()) {
                allDevelopers.add(convertSkill(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allDevelopers;
    }

    @Override
    public boolean update(Skills entity) {
        try {
            UPDATE_SKILL.setInt(1, entity.getId());
            UPDATE_SKILL.setString(2, entity.getLanguage());
            UPDATE_SKILL.setString(3, entity.getLevel());

            UPDATE_SKILL.setInt(4, entity.getId());

            return UPDATE_SKILL.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DELETE_SKILL.setInt(1, id);
            return DELETE_SKILL.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private Skills convertSkill(ResultSet rs) throws SQLException {
        Skills skill = new Skills();
        skill.setId(rs.getInt("id"));
        skill.setLanguage(rs.getString("language"));
        skill.setLevel(rs.getString("level"));

        return skill;
    }

}
