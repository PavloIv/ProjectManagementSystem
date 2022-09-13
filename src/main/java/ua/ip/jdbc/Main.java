package ua.ip.jdbc;

import ua.ip.jdbc.cli.CliFSM;
import ua.ip.jdbc.dao.CustomerDao;
import ua.ip.jdbc.dao.DevelopersDao;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost","5432",
              "GrafProductCompany","postgres", "grafmk1523");
//      new DatabaseInitService().initDb(sqlConnector.getUrl(),sqlConnector.getUser(),sqlConnector.getPassword());

        new CliFSM(sqlConnector);


//        Service service = new Service(sqlConnector);
//        System.out.println("SumSalary on project MobileApp = " + service.salaryOnProjectFromName("MobileApp"));
//
//        System.out.println("SUM salary = " + service.salaryOnProjectFromId(1));
//
//        System.out.println("programmer On Project MobilApp = " + service.programmerOnProjectFromName("MobileApp"));
//
//        System.out.println("programmer On Project From Id 3 = " + service.programmerOnProjectFromId(3));
//
//        System.out.println("programmer On Language = " + service.programmerOnLanguage("Java"));
//
//        System.out.println("programmer On Language Java = " + service.programmerOnLanguageJava());
//
//        System.out.println("programmer with level middle = " + service.programmerLevel("Middle"));
//
//        System.out.println("projects list = " + service.projectsList());
    }
}
