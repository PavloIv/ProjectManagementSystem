package ua.ip.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseSqlManagerConnectorTest {

    @Test
    public void testConnecting() throws SQLException {
        DatabaseSqlManagerConnector mySqlConnectorTest = new DatabaseSqlManagerConnector("localhost",
                "5432",
                "GrafProductCompany",
                "postgres",
                "grafmk1523");
        Connection connection = mySqlConnectorTest.getConnection();
        boolean reachable = !connection.isClosed();
        boolean expectedResult = true;
        assertEquals(reachable,expectedResult);
    }

}