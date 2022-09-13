package ua.ip.jdbc.cli;

public class CliState {
    protected CliFSM fsm;

    public CliState(CliFSM fsm) {
        this.fsm = fsm;
    }
    public void init(){

    }

    public void workingWithDeveloperCrud() {
    }

    public void workingWithSkillCrud() {
    }

    public void workingWithCompanyCrud() {
    }

    public void workingWithCustomerCrud() {
    }

    public void workingWithProjectCrud() {
    }

    public void comeBack() {}

    public void unknownTable(String cmd){
    }
}
