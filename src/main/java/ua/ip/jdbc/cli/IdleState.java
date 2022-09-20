package ua.ip.jdbc.cli;

public class IdleState extends CliState{
    public IdleState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        fsm.printedStartMessage();
    }

    @Override
    public void workingWithDeveloperCrud() {
        fsm.setState(new DevelopersCrudState(fsm));
    }
    @Override
    public void workingWithSkillCrud() {
        fsm.setState(new SkillsCrudState(fsm));
    }
    @Override
    public void workingWithCompanyCrud() {
        fsm.setState(new CompanyCrudState(fsm));
    }
    @Override
    public void workingWithCustomerCrud() {
        fsm.setState(new CustomerCrudState(fsm));
    }
    @Override
    public void workingWithProjectCrud() {
        fsm.setState(new ProjectsCrudState(fsm));
    }
    @Override
    public void unknownTable(String cmd) {
        System.out.println("Table doesn't found: " + cmd);
    }

}
