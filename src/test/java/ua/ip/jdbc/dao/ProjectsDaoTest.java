package ua.ip.jdbc.dao;

import org.junit.jupiter.api.Test;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Projects;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectsDaoTest {
    DatabaseSqlManagerConnector sqlConnector = new DatabaseSqlManagerConnector("localhost",
            "5432",
            "GrafProductCompany",
            "postgres",
            "grafmk1523");
    ProjectsDao testProjectsDao;

    {
        testProjectsDao = new ProjectsDao(sqlConnector);
    }

    @Test
    public void testCreateNewProject(){
        Projects web = new Projects();
        web.setId(2000000000);
        web.setName("Web");
        web.setDescription("WebInterface");
        web.setCost(10000);
        web.setCompany_id(3);
        web.setCustomer_id(3);
        boolean actualCreateProject = testProjectsDao.create(web);
        boolean expectedCreateProjectResult = true;
        assertEquals(expectedCreateProjectResult,actualCreateProject);
    }

    @Test
    public void testCreateNewProjectWithIncorrectDates(){
        Projects web = new Projects();
        web.setId(4);
        web.setName("Web");
        web.setDescription("WebInterface");
        web.setCost(10000);
        web.setCompany_id(3);
        web.setCustomer_id(3);
        boolean actualCreateProject = testProjectsDao.create(web);
        boolean expectedCreateProjectResult = false;
        assertEquals(expectedCreateProjectResult,actualCreateProject);
    }

    @Test
    public void testFindProjectWithIdTwoBillion() throws SQLException {
        Projects actualProjectFound = testProjectsDao.findById(2000000000);
        Projects expectedProjectFound = new Projects();
        expectedProjectFound.setId(2000000000);
        expectedProjectFound.setName("Web");
        expectedProjectFound.setDescription("WebInterface");
        expectedProjectFound.setCost(10000);
        expectedProjectFound.setCompany_id(3);
        expectedProjectFound.setCustomer_id(3);

        assertEquals(expectedProjectFound, actualProjectFound);
    }

    @Test
    public void testFindProjectWithIdDefunct() throws SQLException {
        Projects actualProjectFound = testProjectsDao.findById(2000000001);
        Projects expectedProjectFound = null;
        assertEquals(expectedProjectFound, actualProjectFound);
    }

    @Test
    public void testUpdateProjectWithIdTwoBillion() {
        Projects web = new Projects();
        web.setId(4);
        web.setName("Web");
        web.setDescription("WebInterface");
        web.setCost(10000);
        web.setCompany_id(4);
        web.setCustomer_id(3);
        boolean actualProjectUpdate = testProjectsDao.update(web);
        boolean expectedProjectUpdateResult = true;
        assertEquals(expectedProjectUpdateResult, actualProjectUpdate);

        web.setCompany_id(3);
        testProjectsDao.update(web);
    }

    @Test
    public void testUpdateProjectWithIncorrectlyDates() {
        Projects web = new Projects();
        web.setId(2000000002);
        web.setName("Web");
        web.setDescription("WebInterface");
        web.setCost(10000);
        web.setCompany_id(4);
        web.setCustomer_id(3);
        boolean actualProjectUpdate = testProjectsDao.update(web);
        boolean expectedProjectUpdateResult = false;
        assertEquals(expectedProjectUpdateResult, actualProjectUpdate);

        web.setCompany_id(3);
        testProjectsDao.update(web);
    }

    @Test
    public void testDataCorrectnessUpdate() throws SQLException {
        Projects sec = new Projects();
        sec.setId(2000000000);
        sec.setName("Security");
        sec.setDescription("Security");
        sec.setCost(10000);
        sec.setCompany_id(3);
        sec.setCustomer_id(4);
        testProjectsDao.update(sec);

        Projects actualProject = testProjectsDao.findById(2000000000);
        Projects expectedProject = new Projects();
        expectedProject.setId(2000000000);
        expectedProject.setName("Security");
        expectedProject.setDescription("Security");
        expectedProject.setCost(10000);
        expectedProject.setCompany_id(3);
        expectedProject.setCustomer_id(4);
        assertEquals(expectedProject,actualProject);

        Projects web = new Projects(2000000000,"Web","WebInterface",10000,3,3);
        testProjectsDao.update(web);
    }
    @Test
    public void testDeleteResultFromMethod(){
        boolean actualDeleteResult = testProjectsDao.delete(2000000000);
        boolean expectedDeleteResult = true;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

    @Test
    public void testDeleteResultFromMethodWithIncorrectId(){
        boolean actualDeleteResult = testProjectsDao.delete(2000000004);
        boolean expectedDeleteResult = false;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

}