package ua.ip.jdbc.dao;

import org.junit.jupiter.api.Test;
import ua.ip.jdbc.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Developers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DevelopersDaoTest {
    DatabaseSqlManagerConnector mySqlConnector = new DatabaseSqlManagerConnector("localhost",
            "5432",
            "GrafProductCompany",
            "postgres",
            "grafmk1523");
    DevelopersDao testDevelopersDao;

    {
        try {
            testDevelopersDao = new DevelopersDao(mySqlConnector);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateNewDeveloper(){
        Developers valya = new Developers();
        valya.setId(2000000000);
        valya.setName("Valya");
        valya.setAge(25);
        valya.setSex("female");
        valya.setSalary(1100);
        boolean actualCreateDeveloper = testDevelopersDao.create(valya);
        boolean expectedCreateDeveloperResult = true;
        assertEquals(expectedCreateDeveloperResult,actualCreateDeveloper);
    }

    @Test
    public void testCreateNewDeveloperWithIncorrectDates(){
        Developers valya = new Developers();
        valya.setId(4);
        valya.setAge(25);
        valya.setSex("female");
        valya.setSalary(1100);
        boolean actualCreateDeveloper = testDevelopersDao.create(valya);
        boolean expectedCreateDeveloperResult = false;
        assertEquals(expectedCreateDeveloperResult,actualCreateDeveloper);
    }

    @Test
    public void testFindDeveloperWithIdTwoBillion() throws SQLException {
        Developers actualDeveloperFound = testDevelopersDao.findById(2000000000);
        Developers expectedDeveloperFound = new Developers();
        expectedDeveloperFound.setId(2000000000);
        expectedDeveloperFound.setName("Valya");
        expectedDeveloperFound.setAge(25);
        expectedDeveloperFound.setSex("female");
        expectedDeveloperFound.setSalary(1100);

        assertEquals(expectedDeveloperFound, actualDeveloperFound);
    }

    @Test
    public void testFindDeveloperWithIdDefunct() throws SQLException {
        Developers actualDeveloperFound = testDevelopersDao.findById(2000000001);
        Developers expectedDeveloperFound = null;
        assertEquals(expectedDeveloperFound, actualDeveloperFound);
    }

    @Test
    public void testUpdateDeveloperWithIdTwoBillion() {
        Developers valya = new Developers();
        valya.setId(2000000000);
        valya.setName("Valya");
        valya.setAge(25);
        valya.setSex("female");
        valya.setSalary(1200);
        boolean actualDeveloperUpdate = testDevelopersDao.update(valya);
        boolean expectedDeveloperUpdateResult = true;
        assertEquals(expectedDeveloperUpdateResult, actualDeveloperUpdate);

        valya.setSalary(1100);
        testDevelopersDao.update(valya);
    }

    @Test
    public void testUpdateDeveloperWithIncorrectlyDates() {
        Developers valya = new Developers();
        valya.setId(2000000002);
        valya.setName("Valya");
        valya.setAge(25);
        valya.setSex("female");
        valya.setSalary(1200);
        boolean actualDeveloperUpdate = testDevelopersDao.update(valya);
        boolean expectedDeveloperUpdateResult = false;
        assertEquals(expectedDeveloperUpdateResult, actualDeveloperUpdate);

        valya.setSalary(1100);
        testDevelopersDao.update(valya);
    }

    @Test
    public void testDataCorrectnessUpdate() throws SQLException {
        Developers iryna = new Developers();
        iryna.setId(2000000000);
        iryna.setName("Iryna");
        iryna.setAge(27);
        iryna.setSex("female");
        iryna.setSalary(1200);
        testDevelopersDao.update(iryna);

        Developers actualDeveloper = testDevelopersDao.findById(2000000000);
        Developers expectedDeveloper = new Developers();
        expectedDeveloper.setId(2000000000);
        expectedDeveloper.setName("Iryna");
        expectedDeveloper.setAge(27);
        expectedDeveloper.setSex("female");
        expectedDeveloper.setSalary(1200);
        assertEquals(expectedDeveloper,actualDeveloper);

        Developers valya = new Developers(2000000000,"Valya",25,"female",1100);
        testDevelopersDao.update(valya);
    }
    @Test
    public void testDeleteResultFromMethod(){
        boolean actualDeleteResult = testDevelopersDao.delete(2000000000);
        boolean expectedDeleteResult = true;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

    @Test
    public void testDeleteResultFromMethodWithIncorrectId(){
        boolean actualDeleteResult = testDevelopersDao.delete(2000000004);
        boolean expectedDeleteResult = false;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }
}