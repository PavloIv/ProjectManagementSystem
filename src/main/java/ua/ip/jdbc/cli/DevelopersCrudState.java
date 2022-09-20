package ua.ip.jdbc.cli;

import ua.ip.jdbc.dao.DevelopersDao;
import ua.ip.jdbc.table.Developers;


public class DevelopersCrudState extends CliState {
    private DevelopersDao developersDao;

    public DevelopersCrudState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        System.out.println("Please, write command with list:");
        System.out.println("'create' for create new developer");
        System.out.println("'read' for see list all developers ");
        System.out.println("'update' for update developer with id");
        System.out.println("'delete' for delete developer with id");
        System.out.println("'find' for see  developer with id");
        System.out.println("'salaryProject' for see total salary on project with name");
        System.out.println("'salaryId' for see total salary on project with id");
        System.out.println("'showProject' for see  programmer on project with name");
        System.out.println("'showId' for see  programmer on project with id");
        System.out.println("'showLanguage' for see  programmer develop on language");
        System.out.println("'showJava' for see  programmer develop on Java");
        System.out.println("'showLevel' for see programmer with skill level");
        System.out.println("'back' for back to previous menu");
        developersDao = new DevelopersDao(fsm.getSqlConnector());
        String command = fsm.getScanner().nextLine();
        startDevelopersCrudLoop(command);
    }

    private void startDevelopersCrudLoop(String command) {
        boolean back = false;
        switch (command) {
            case "create":
                create();
                break;
            case "read":
                readTable();
                break;
            case "update":
                update();
                break;
            case "delete":
                delete();
                break;
            case "find":
                findDeveloperWithId();
                break;
            case "salaryProject":
                calculateSalaryOnProjectFromName();
                break;
            case "salaryId":
                calculateSalaryOnProjectFromId();
                break;
            case "showProject":
                showProgrammerOnProjectFromProjectName();
                break;
            case "showId":
                showProgrammerOnProjectFromProjectId();
                break;
            case "showLanguage":
                showProgrammerOnLanguage();
                break;
            case "showJava":
                showProgrammerOnJava();
                break;
            case "showLevel":
                showProgrammersWithLevel();
                break;
            case "back":
                back = true;
                break;
            default:
                System.out.println("Command not found");
                break;
        }
        if (back) {
            fsm.setState(new IdleState(fsm));
        } else {
            init();
        }
    }

    private void create() {
        System.out.println("Write developer name:");
        String developerName = fsm.getScanner().nextLine();

        System.out.println("Write developer age");
        Integer developerAge = fsm.writeDigit();

        System.out.println("Write developer sex:");
        String developerSex = fsm.getScanner().nextLine();

        System.out.println("Write developer salary:");
        Integer developerSalary = fsm.writeDigit();
        Developers developer = new Developers();
        developer.setName(developerName);
        developer.setAge(developerAge);
        developer.setSex(developerSex);
        developer.setSalary(developerSalary);

        if (developersDao.create(developer)) {
            System.out.println("Developer add to database");
        } else {
            System.out.println("We have problem with add developer,try again");
        }
    }

    private void readTable() {
        System.out.println("All developers list = " + developersDao.findAll());
    }

    private void update() {

        System.out.println("Write developer id:");
        Integer developerId = fsm.writeDigit();

        System.out.println("Write developer name:");
        String developerName = fsm.getScanner().nextLine();

        System.out.println("Write developer age:");
        Integer developerAge = fsm.writeDigit();

        System.out.println("Write developer sex:");
        String developerSex = fsm.getScanner().nextLine();

        System.out.println("Write developer salary:");
        Integer developerSalary = fsm.writeDigit();

        Developers developer = new Developers(developerId, developerName, developerAge, developerSex, developerSalary);

        developersDao.update(developer);

        System.out.printf("Developer with id = %s update.", developerId);
    }

    private void delete() {
        System.out.println("Write developer id to delete:");
        Integer developerId = fsm.writeDigit();
        developersDao.delete(developerId);
        System.out.println("Developer delete is complete.");
    }

    private void findDeveloperWithId() {
        System.out.println("Write developer id:");
        Integer developerId = fsm.writeDigit();

        System.out.print("Developer :");
        System.out.println(developersDao.findById(developerId));
    }

    private void calculateSalaryOnProjectFromName() {
        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.printf("SumSalary on project %s = ", projectName);
        System.out.println(developersDao.calculateSalaryOnProjectFromName(projectName));
    }

    private void calculateSalaryOnProjectFromId() {
        System.out.println("Write project id:");
        Integer projectId = fsm.writeDigit();

        System.out.printf("SumSalary on project %s = ", projectId);
        System.out.println(developersDao.calculateSalaryOnProjectFromId(projectId));
    }

    private void showProgrammerOnProjectFromProjectName() {
        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.printf("List of programmers working on the project %s : ", projectName);
        System.out.println(developersDao.showProgrammerOnProjectFromProjectName(projectName));
    }

    private void showProgrammerOnProjectFromProjectId() {
        System.out.println("Write project id:");
        Integer projectId = fsm.writeDigit();

        System.out.printf("List of programmers working on the project with id %s : ", projectId);
        System.out.println(developersDao.showProgrammerOnProjectFromProjectId(projectId));
    }

    private void showProgrammerOnLanguage() {
        System.out.println("Write language:");
        String languageName = fsm.getScanner().nextLine();

        System.out.printf("List of programmers working on the language %s : ", languageName);
        System.out.println(developersDao.showProgrammerOnLanguage(languageName));
    }

    private void showProgrammerOnJava() {
        System.out.print("List of programmers working on the Java :");
        System.out.println(developersDao.showProgrammerOnLanguageJava());
    }

    private void showProgrammersWithLevel() {
        System.out.println("Write level:");
        String level = fsm.getScanner().nextLine();

        System.out.printf("List of programmers with skill level %s : ", level);
        System.out.println(developersDao.showProgrammersWithLevel(level));
    }
}
