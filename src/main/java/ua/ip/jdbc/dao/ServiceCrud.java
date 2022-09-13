package ua.ip.jdbc.dao;

import java.sql.SQLException;
import java.util.List;

public interface ServiceCrud<T> {
    boolean create(T entity);

    T findById(Integer id) throws SQLException;

    List<T> findAll();

    boolean update(T entity);

    boolean delete(Integer id);
}
