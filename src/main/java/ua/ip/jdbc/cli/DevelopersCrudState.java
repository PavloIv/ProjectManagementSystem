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
        System.out.println("create");
        System.out.println("read");
        System.out.println("update");
        System.out.println("delete");
        System.out.println("find");
        System.out.println("salary on project");
        System.out.println("back");
        developersDao = new DevelopersDao(fsm.getSqlConnector());
        String command = fsm.getScanner().nextLine();
        startDevelopersCrudLoop(command);
    }

    private void startDevelopersCrudLoop(String command) {
        boolean back = false;
        switch (command) {
            case "create":
                createNewDeveloper();
                break;
            case "read":
                readTable();
                break;
            case "update":
                updateDeveloper();
                break;
            case "delete":
                deleteDeveloper();
                break;
            case "find":
                findDeveloperWithId();
                break;
            case "salary on project":
                calculateSalaryOnProjectFromName();
                break;
            case "salary on project with id":
                calculateSalaryOnProjectFromId();
                break;
            case "show programmer on project":
                showProgrammerOnProjectFromProjectName();
                break;
            case "show programmer on project with id":
                showProgrammerOnProjectFromProjectId();
                break;
            case "show programmer on language":
                showProgrammerOnLanguage();
                break;
            case "show programmer with level":
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

    private void createNewDeveloper() {
        System.out.println("Write developer name:");
        String developerName = fsm.getScanner().nextLine();

        System.out.println("Write developer sex:");
        String developerSex = fsm.getScanner().nextLine();

        System.out.println("Write developer age:");
        int developerAge = fsm.getScanner().nextInt();

        System.out.println("Write developer salary:");
        int developerSalary = fsm.getScanner().nextInt();


        Developers developer = new Developers(developerName, developerAge, developerSex, developerSalary);

        if (developersDao.create(developer)) {
            System.out.println("Developer add to database");
        } else {
            System.out.println("We have problem with add developer,try again");
        }
    }

    private void readTable() {
        System.out.println("All developers list = " + developersDao.findAll());
    }

    private void findDeveloperWithId() {
        System.out.println("Write developer id:");
        int developerId = fsm.getScanner().nextInt();

        System.out.printf("Developer:", developerId);
        System.out.println(developersDao.findById(developerId));
    }

    private void updateDeveloper() {
        Developers developer = new Developers();
        System.out.println("Write developer id:");
        int developerId = fsm.getScanner().nextInt();
        developer.setId(developerId);

        System.out.println("Write developer name:");
        String developerName = fsm.getScanner().nextLine();
        developer.setName(developerName);

        System.out.println("Write developer age:");
        int developerAge = fsm.getScanner().nextInt();
        developer.setAge(developerAge);

        System.out.println("Write developer sex:");
        String developerSex = fsm.getScanner().nextLine();
        developer.setSex(developerSex);

        System.out.println("Write developer salary:");
        int developerSalary = fsm.getScanner().nextInt();
        developer.setSalary(developerSalary);

        developersDao.update(developer);

        System.out.printf("Developer with id = %s update.", developerId);
    }

    private void deleteDeveloper() {
        System.out.println("Write developer id to delete:");
        int developerId = fsm.getScanner().nextInt();
        developersDao.delete(developerId);
        System.out.println("Developer delete is complete.");
    }

    private void showProgrammersWithLevel() {
        System.out.println("Write language:");
        String languageName = fsm.getScanner().nextLine();

        System.out.printf("List of programmers working on the language %s : ", languageName);
        System.out.println(developersDao.showProgrammerOnLanguage(languageName));
    }

    private void showProgrammerOnLanguage() {
        System.out.println("Write language:");
        String languageName = fsm.getScanner().nextLine();

        System.out.printf("List of programmers working on the language %s : ", languageName);
        System.out.println(developersDao.showProgrammerOnLanguage(languageName));
    }

    private void showProgrammerOnProjectFromProjectId() {
        System.out.println("Write project id:");
        int projectId = fsm.getScanner().nextInt();

        System.out.printf("List of programmers working on the project with id %s : ", projectId);
        System.out.println(developersDao.showProgrammerOnProjectFromProjectId(projectId));
    }

    private void showProgrammerOnProjectFromProjectName() {
        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.printf("List of programmers working on the project %s : ", projectName);
        System.out.println(developersDao.showProgrammerOnProjectFromProjectName(projectName));
    }

    private void calculateSalaryOnProjectFromId() {
        System.out.println("Write project id:");
        int projectId = fsm.getScanner().nextInt();

        System.out.printf("SumSalary on project %s = ", projectId);
        System.out.println(developersDao.calculateSalaryOnProjectFromId(projectId));
    }

    private void calculateSalaryOnProjectFromName() {
        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.printf("SumSalary on project %s = ", projectName);
        System.out.println(developersDao.calculateSalaryOnProjectFromName(projectName));
    }


}
