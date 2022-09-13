package ua.ip.jdbc.dao;

import org.junit.jupiter.api.Test;
import ua.ip.jdbc.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Companies;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CompaniesDaoTest {
    DatabaseSqlManagerConnector mySqlConnector = new DatabaseSqlManagerConnector("localhost", "5432",//50289
            "GrafProductCompany", "postgres", "grafmk1523");
    CompaniesDAO testServiceCompanies;

    {
        try {
            testServiceCompanies = new CompaniesDAO(mySqlConnector);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateNewCompany(){
        Companies microsoft = new Companies();
        microsoft.setId(2000000000);
        microsoft.setName("Microsoft");
        microsoft.setYear_of_foundation(1975);
        boolean actualCreateDeveloper = testServiceCompanies.create(microsoft);
        boolean expectedCreateDeveloperResult = true;
        assertEquals(expectedCreateDeveloperResult,actualCreateDeveloper);
    }

    @Test
    public void testCreateNewCompaniesWithIncorrectDates(){
        Companies microsoft = new Companies();
        microsoft.setId(4);
        microsoft.setName("mkdvs");
        microsoft.setYear_of_foundation(2123);
        boolean actualCreateDeveloper = testServiceCompanies.create(microsoft);
        boolean expectedCreateDeveloperResult = false;
        assertEquals(expectedCreateDeveloperResult,actualCreateDeveloper);
    }

    @Test
    public void testFindCompanyWithIdTwoBillion() throws SQLException {
        Companies actualCompanyFound = testServiceCompanies.findById(2000000000);
        Companies expectedCompanyFound = new Companies();
        expectedCompanyFound.setId(2000000000);
        expectedCompanyFound.setName("Microsoft");
        expectedCompanyFound.setYear_of_foundation(1975);

        assertEquals(expectedCompanyFound, actualCompanyFound);
    }

    @Test
    public void testFindCompanyWithIdDefunct() throws SQLException {
        Companies actualCompanyFound = testServiceCompanies.findById(2000000001);
        Companies expectedCompanyFound = null;
        assertEquals(expectedCompanyFound, actualCompanyFound);
    }

//    @Test
//    public void testUpdateCompanyWithIdTwoBillion() {
//        Companies apple = new Companies();
//        apple.setId(2000000000);
//        apple.setName("Apple_Inc");
//        apple.setYear_of_foundation(1976);
//
//        boolean actualCompanyUpdate = testServiceCompanies.update(apple);
//        boolean expectedCompanyUpdateResult = true;
//        assertEquals(expectedCompanyUpdateResult, actualCompanyUpdate);
//
//    }

    @Test
    public void testUpdateCompanyWithIncorrectlyDates() {
        Companies apple = new Companies();
        apple.setId(2000000004);
        apple.setName("Apple_Inc");
        apple.setYear_of_foundation(1976);
        boolean actualDeveloperUpdate = testServiceCompanies.update(apple);
        boolean expectedDeveloperUpdateResult = false;
        assertEquals(expectedDeveloperUpdateResult, actualDeveloperUpdate);
    }

    @Test
    public void testDataCorrectnessUpdate() throws SQLException {
        Companies apple = new Companies();
        apple.setId(2000000000);
        apple.setName("Apple_Inc");
        apple.setYear_of_foundation(1976);
        testServiceCompanies.update(apple);

        Companies actualCompany = testServiceCompanies.findById(2000000000);
        Companies expectedCompany = new Companies();
        expectedCompany.setId(2000000000);
        expectedCompany.setName("Apple_Inc");
        expectedCompany.setYear_of_foundation(1976);

        assertEquals(expectedCompany,actualCompany);

        Companies microsoft = new Companies(2000000000,"Microsoft",1975);
        testServiceCompanies.update(microsoft);
    }

    @Test
    public void testDeleteResultFromMethod(){
        boolean actualDeleteResult = testServiceCompanies.delete(2000000000);
        boolean expectedDeleteResult = true;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

    @Test
    public void testDeleteResultFromMethodWithIncorrectId(){
        boolean actualDeleteResult = testServiceCompanies.delete(2000000004);
        boolean expectedDeleteResult = false;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

}