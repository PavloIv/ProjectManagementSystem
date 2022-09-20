package ua.ip.jdbc.dao;

import org.junit.jupiter.api.Test;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Customers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {
    DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost",
            "5432",
            "GrafProductCompany",
            "postgres",
            "grafmk1523");
    CustomerDao testServiceCustomers;

    {
        testServiceCustomers = new CustomerDao(sqlConnector);
    }

    @Test
    public void testCreateNewCustomer(){
        Customers amazon = new Customers();
        amazon.setId(2000000000);
        amazon.setName("Amazon");
        amazon.setWebsite("https://amazon.com.ua/");
        boolean actualCreateCustomer = testServiceCustomers.create(amazon);
        boolean expectedCreateCustomerResult = true;
        assertEquals(expectedCreateCustomerResult,actualCreateCustomer);
    }

    @Test
    public void testCreateNewDeveloperWithIncorrectDates(){
        Customers amazon = new Customers();
        amazon.setId(3);
        amazon.setName("Amazon");
        boolean actualCreateCustomer = testServiceCustomers.create(amazon);
        boolean expectedCreateCustomerResult = false;
        assertEquals(expectedCreateCustomerResult,actualCreateCustomer);
    }

    @Test
    public void testFindDeveloperWithIdTwoBillion() throws SQLException {
        Customers actualCustomerFound = testServiceCustomers.findById(2000000000);
        Customers expectedCustomerFound = new Customers();
        expectedCustomerFound.setId(2000000000);
        expectedCustomerFound.setName("Amazon");
        expectedCustomerFound.setWebsite("https://amazon.com.ua/");

        assertEquals(expectedCustomerFound, actualCustomerFound);
    }

    @Test
    public void testFindCustomerWithIdDefunct() throws SQLException {
        Customers actualCustomerFound = testServiceCustomers.findById(2000000001);
        Customers expectedDeveloperFound = null;
        assertEquals(expectedDeveloperFound, actualCustomerFound);
    }

    @Test
    public void testUpdateDeveloperWithIdTwoBillion() {
        Customers amazon = new Customers();
        amazon.setId(2000000000);
        amazon.setName("Amazon");
        amazon.setWebsite("https://bing.com.ua/");
        boolean actualCystomerUpdate = testServiceCustomers.update(amazon);
        boolean expectedCustomerUpdateResult = true;
        assertEquals(expectedCustomerUpdateResult, actualCystomerUpdate);

        amazon.setWebsite("https://amazon.com.ua/");
        testServiceCustomers.update(amazon);
    }

    @Test
    public void testUpdateDeveloperWithIncorrectlyDates() {
        Customers amazon = new Customers();
        amazon.setId(2000000002);
        amazon.setName("Amazon");
        amazon.setWebsite("https://maz.com.ua/");
        boolean actualCustomerUpdate = testServiceCustomers.update(amazon);
        boolean expectedCustomerUpdateResult = false;
        assertEquals(expectedCustomerUpdateResult, actualCustomerUpdate);

        amazon.setWebsite("https://amazon.com.ua/");;
        testServiceCustomers.update(amazon);
    }

    @Test
    public void testDataCorrectnessUpdate() throws SQLException {
        Customers spaceX = new Customers();
        spaceX.setId(2000000000);
        spaceX.setName("SpaceX");
        spaceX.setWebsite("https://spaceX.com.ua/");
        testServiceCustomers.update(spaceX);

        Customers actualCustomer = testServiceCustomers.findById(2000000000);
        Customers expectedCustomer = new Customers();
        expectedCustomer.setId(2000000000);
        expectedCustomer.setName("SpaceX");
        expectedCustomer.setWebsite("https://spaceX.com.ua/");

        assertEquals(expectedCustomer,actualCustomer);

        Customers amazon = new Customers(2000000000,"Amazon","https://amazon.com.ua/");
        testServiceCustomers.update(amazon);
    }

    @Test
    public void testDeleteResultFromMethod(){
        boolean actualDeleteResult = testServiceCustomers.delete(2000000000);
        boolean expectedDeleteResult = true;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

    @Test
    public void testDeleteResultFromMethodWithIncorrectId(){
        boolean actualDeleteResult = testServiceCustomers.delete(2000000004);
        boolean expectedDeleteResult = false;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }
}