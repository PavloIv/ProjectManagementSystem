package ua.ip.jdbc.dao;

import org.junit.jupiter.api.Test;
import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;
import ua.ip.jdbc.table.Skills;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SkillsDaoTest {
    DatabaseSqlManagerConnector SqlConnector = new DatabaseSqlManagerConnector("localhost",
            "5432",
            "GrafProductCompany",
            "postgres",
            "grafmk1523");
    SkillsDao testServiceSkill;

    {
        try {
            testServiceSkill = new SkillsDao(SqlConnector);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateNewSkill(){
        Skills ts = new Skills();
        ts.setId(2000000000);
        ts.setLanguage("TypeScript");
        ts.setLevel("Junior");
        boolean actualCreateSkill = testServiceSkill.create(ts);
        boolean expectedCreateSkillResult = true;
        assertEquals(expectedCreateSkillResult,actualCreateSkill);
    }

    @Test
    public void testCreateNewSkillWithIncorrectDates(){
        Skills ts = new Skills();
        ts.setId(2);
        ts.setLanguage("TypeScript");
        ts.setLevel("Junior");
        boolean actualCreateSkill = testServiceSkill.create(ts);
        boolean expectedCreateSkillResult = false;
        assertEquals(expectedCreateSkillResult,actualCreateSkill);
    }

    @Test
    public void testFindDeveloperWithIdTwoBillion() throws SQLException {
        Skills actualSkillFound = testServiceSkill.findById(2000000000);
        Skills expectedSkillFound = new Skills();
        expectedSkillFound.setId(2000000000);
        expectedSkillFound.setLanguage("TypeScript");
        expectedSkillFound.setLevel("Junior");

        assertEquals(expectedSkillFound, actualSkillFound);
    }

    @Test
    public void testFindSkillWithIdDefunct() throws SQLException {
        Skills actualSkillFound = testServiceSkill.findById(2000000001);
        Skills expectedSkillFound = null;
        assertEquals(expectedSkillFound, actualSkillFound);
    }

    @Test
    public void testUpdateSkillWithIdTwoBillion() {
        Skills ts = new Skills();
        ts.setId(2000000000);
        ts.setLanguage("TypeScript");
        ts.setLevel("Middle");
        boolean actualSkillUpdate = testServiceSkill.update(ts);
        boolean expectedSkillUpdateResult = true;
        assertEquals(expectedSkillUpdateResult, actualSkillUpdate);

        ts.setLevel("Junior");
        testServiceSkill.update(ts);
    }

    @Test
    public void testUpdateDeveloperWithIncorrectlyDates() {
        Skills ts = new Skills();
        ts.setId(2000000002);
        ts.setLanguage("TypeScript");
        ts.setLevel("Middle");
        boolean actualSkillUpdate = testServiceSkill.update(ts);
        boolean expectedSkillUpdateResult = false;
        assertEquals(expectedSkillUpdateResult, actualSkillUpdate);

        ts.setLevel("Junior");
        testServiceSkill.update(ts);
    }

    @Test
    public void testDataCorrectnessUpdate() throws SQLException {
        Skills http = new Skills();
        http.setId(2000000000);
        http.setLanguage("HTTP");
        http.setLevel("Senior");

        testServiceSkill.update(http);

        Skills actualSkills = testServiceSkill.findById(2000000000);
        Skills expectedSkill = new Skills();
        expectedSkill.setId(2000000000);
        expectedSkill.setLanguage("HTTP");
        expectedSkill.setLevel("Senior");
        assertEquals(expectedSkill,actualSkills);

        Skills ts = new Skills(2000000000,"TypeScript","Junior");
        testServiceSkill.update(ts);
    }

    @Test
    public void testDeleteResultFromMethod(){
        boolean actualDeleteResult = testServiceSkill.delete(2000000000);
        boolean expectedDeleteResult = true;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

    @Test
    public void testDeleteResultFromMethodWithIncorrectId(){
        boolean actualDeleteResult = testServiceSkill.delete(2000000004);
        boolean expectedDeleteResult = false;
        assertEquals(expectedDeleteResult,actualDeleteResult);
    }

}