package ua.ip.jdbc.cli;


import ua.ip.jdbc.dao.SkillsDao;
import ua.ip.jdbc.table.Skills;

public class SkillsCrudState extends CliState{

    private SkillsDao skillsDao;
    public SkillsCrudState(CliFSM fsm) {
        super(fsm);
    }
    @Override
    public void init() {
        System.out.println("Please, write skill with list:");
        System.out.println("'create' for create new skill");
        System.out.println("'read' for see list all skills ");
        System.out.println("'update' for update skill with id");
        System.out.println("'delete' for delete skill with id");
        System.out.println("'back' for back to previous menu");
        skillsDao = new SkillsDao(fsm.getSqlConnector());
        String command = fsm.getScanner().nextLine();
        startCustomersCrudLoop(command);
    }

    private void startCustomersCrudLoop(String command) {
        boolean back = false;
        switch (command) {
            case "create":
                createNewDeveloper();
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
                find();
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
        System.out.println("Write language:");
        String skillLanguage = fsm.getScanner().nextLine();

        System.out.println("Write skill level:");
        String skillLevel = fsm.getScanner().nextLine();

        Skills skill = new Skills();
        skill.setLanguage(skillLanguage);
        skill.setLevel(skillLevel);

        if (skillsDao.create(skill)) {
            System.out.println("Skill add to database");
        } else {
            System.out.println("We have problem with add skill,try again");
        }
    }

    private void readTable() {
        System.out.println("All skill list = " + skillsDao.findAll());
    }

    private void update() {
        Integer skillId = null;
        while (true) {
            System.out.println("Write skill id:");
            skillId = fsm.writeDigit();

            if (skillsDao.findById(skillId) == null) {
                System.out.println("Skill not found.Please try again");
            }else {break;}
        }


        System.out.println("Write language:");
        String skillLanguage = fsm.getScanner().nextLine();

        System.out.println("Write skill level:");
        String skillLevel = fsm.getScanner().nextLine();

        Skills skill = new Skills(skillId,skillLanguage,skillLevel);

        skillsDao.update(skill);

        System.out.printf("Skill with id = %s update.", skillId);
    }

    private void delete() {
        System.out.println("Write skill id to delete:");
        Integer skillId = fsm.writeDigit();
        skillsDao.delete(skillId);
        System.out.println("Skill delete is complete.");
    }

    private void find() {
        System.out.println("Write skill id:");
        Integer skillId= fsm.writeDigit();

        System.out.print("Skill :");
        System.out.println(skillsDao.findById(skillId));
    }
}
