package ua.ip.jdbc.dao;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Companies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDAO implements ServiceCrud<Companies> {

    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_COMPANY;
    private final PreparedStatement SELECT_COMPANIES_BY_ID;
    private final PreparedStatement SELECT_ALL_COMPANIES;
    private final PreparedStatement UPDATE_COMPANY;
    private final PreparedStatement DELETE_COMPANY;

    public CompaniesDAO(DatabaseSqlManagerConnector sqlConnector) throws SQLException {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        INSERT_COMPANY = connection.prepareStatement("INSERT INTO companies (id,name,year_of_foundation) VALUES(?,?,?)");
        SELECT_COMPANIES_BY_ID = connection.prepareStatement("SELECT id,name,year_of_foundation FROM companies WHERE id = ?");
        SELECT_ALL_COMPANIES = connection.prepareStatement("SELECT * FROM companies");
        UPDATE_COMPANY = connection.prepareStatement("UPDATE companies SET id = ?,name = ?," +
                "year_of_foundation = ?  WHERE id = ?");
        DELETE_COMPANY = connection.prepareStatement("DELETE FROM companies WHERE id = ?");

    }

    @Override
    public boolean create(Companies entity) {
        try {
            INSERT_COMPANY.setInt(1, entity.getId());
            INSERT_COMPANY.setString(2, entity.getName());
            INSERT_COMPANY.setInt(3, entity.getYear_of_foundation());
            return INSERT_COMPANY.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Companies findById(Integer id) throws SQLException {
        SELECT_COMPANIES_BY_ID.setInt(1, id);
        try (ResultSet rs = SELECT_COMPANIES_BY_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return convertCompanies(rs);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List findAll() {
        List<Companies> allCompanies = new ArrayList<>();
        try (ResultSet rs = SELECT_ALL_COMPANIES.executeQuery()) {
            while (rs.next()) {
                allCompanies.add(convertCompanies(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCompanies;
    }

    @Override
    public boolean update(Companies entity) {
        try {
            UPDATE_COMPANY.setInt(1, entity.getId());
            UPDATE_COMPANY.setString(2, entity.getName());
            UPDATE_COMPANY.setInt(3, entity.getYear_of_foundation());
            UPDATE_COMPANY.setInt(4, entity.getId());

            return UPDATE_COMPANY.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DELETE_COMPANY.setInt(1, id);
            return DELETE_COMPANY.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Companies convertCompanies(ResultSet resultSet) throws SQLException {
        Companies companies = new Companies();
        companies.setId(resultSet.getInt("id"));
        companies.setName(resultSet.getString("name"));
        companies.setYear_of_foundation(resultSet.getInt("year_of_foundation"));

        return companies;
    }
}
