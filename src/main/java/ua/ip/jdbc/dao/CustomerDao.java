package ua.ip.jdbc.dao;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements ServiceCrud<Customers>{
    private final DatabaseSqlManagerConnector sqlConnector;
    private final PreparedStatement INSERT_CUSTOMER;
    private final PreparedStatement SELECT_CUSTOMER_BY_ID;
    private final PreparedStatement SELECT_ALL_CUSTOMERS;
    private final PreparedStatement UPDATE_CUSTOMER;
    private final PreparedStatement DELETE_CUSTOMER;



    public CustomerDao(DatabaseSqlManagerConnector sqlConnector) {
        this.sqlConnector = sqlConnector;
        Connection connection = sqlConnector.getConnection();
        try {
        INSERT_CUSTOMER = connection.prepareStatement("INSERT INTO customers(id,name,website) VALUES(?,?,?)");
        SELECT_CUSTOMER_BY_ID = connection.prepareStatement("SELECT id,name,website FROM customers WHERE id = ?");
        SELECT_ALL_CUSTOMERS = connection.prepareStatement("SELECT * FROM customers");
        UPDATE_CUSTOMER = connection.prepareStatement("UPDATE customers SET id = ?,name = ?,website = ?  WHERE id = ?");
        DELETE_CUSTOMER = connection.prepareStatement("DELETE FROM customers WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean create(Customers entity) {
        try {
            INSERT_CUSTOMER.setInt(1, entity.getId());
            INSERT_CUSTOMER.setString(2, entity.getName());
            INSERT_CUSTOMER.setString(3,entity.getWebsite());
            return INSERT_CUSTOMER.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Customers findById(Integer id){
        try {
            SELECT_CUSTOMER_BY_ID.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (ResultSet rs = SELECT_CUSTOMER_BY_ID.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return convertCustomer(rs);
        } catch (Exception ex) {
            return null;
        }
    }



    @Override
    public List findAll() {
        List<Customers> allCustomers = new ArrayList<>();
        try (ResultSet rs = SELECT_ALL_CUSTOMERS.executeQuery()) {
            while (rs.next()) {
                allCustomers.add(convertCustomer(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public boolean update(Customers entity) {
        try {
            UPDATE_CUSTOMER.setInt(1, entity.getId());
            UPDATE_CUSTOMER.setString(2, entity.getName());
            UPDATE_CUSTOMER.setString(3, entity.getWebsite());
            UPDATE_CUSTOMER.setInt(4, entity.getId());

            return UPDATE_CUSTOMER.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            DELETE_CUSTOMER.setInt(1, id);
            return DELETE_CUSTOMER.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Customers convertCustomer(ResultSet rs) throws SQLException {
        Customers customer = new Customers();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setWebsite(rs.getString("website"));
        return customer;
    }
}
