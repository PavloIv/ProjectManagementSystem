package ua.ip.jdbc;

import ua.ip.jdbc.cli.CliFSM;
import ua.ip.jdbc.storage.DatabaseInitService;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost", "5432",
                    "GrafProductCompany", "postgres", "grafmk1523");
        new DatabaseInitService().initDb(sqlConnector.getUrl(), sqlConnector.getUser(), sqlConnector.getPassword());

        new CliFSM(sqlConnector);


    }
}
