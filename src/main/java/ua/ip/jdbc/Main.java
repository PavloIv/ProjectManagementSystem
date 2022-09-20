package ua.ip.jdbc;

import ua.ip.jdbc.cli.CliFSM;
import ua.ip.jdbc.dao.CustomerDao;
import ua.ip.jdbc.dao.DevelopersDao;
import ua.ip.jdbc.dao.ProjectsDao;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Projects;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {

        DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost","5432",
              "GrafProductCompany","postgres", "grafmk1523");
//      new DatabaseInitService().initDb(sqlConnector.getUrl(),sqlConnector.getUser(),sqlConnector.getPassword());

        new CliFSM(sqlConnector);


    }
}
