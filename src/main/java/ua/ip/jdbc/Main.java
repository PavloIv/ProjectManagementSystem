package ua.ip.jdbc;

import ua.ip.jdbc.service.Service;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {//localhost
      DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost","5432",
              "GrafProductCompany","postgres", "grafmk1523");
//        CustomerDao customer = new CustomerDao(sqlConnector);
//        System.out.println("service.findById(3) = " + customer.findById(3));

        Service service = new Service(sqlConnector);
//        System.out.println("SumSalary on project MobileApp = " + service.SalaryOnProjectFromName("MobileApp"));
        System.out.println("SUM salary = " + service.SalaryOnProjectFromId(1));

        System.out.println("programmer On Project From Id 3 = " + service.programmerOnProjectFromId(3));

        System.out.println("programmer On Language Java = " + service.programmerOnLanguageJava());

//        System.out.println("programmer with level middle = " + service.programmerLevel("Middle"));

        System.out.println("projects list = " + service.projectsList());
    }
}
