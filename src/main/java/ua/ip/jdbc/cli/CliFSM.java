package ua.ip.jdbc.cli;

import ua.ip.jdbc.storage.DatabaseSqlManagerConnector;

import java.util.Scanner;

public class CliFSM {
    private CliState state;
    private  DatabaseSqlManagerConnector sqlConnector;
    private final Scanner scanner;

    public CliFSM(DatabaseSqlManagerConnector sqlConnector) {
        state = new IdleState(this);
        this.sqlConnector = sqlConnector;
        printedStartMessage();
        scanner = new Scanner(System.in);
        startInputLoop();
    }

    public void printedStartMessage(){
        System.out.println("Please ,write table name for further work,from list:");
        System.out.println("developers");
        System.out.println("skills");
        System.out.println("company");
        System.out.println("customer");
        System.out.println("project");
        System.out.println("Or write 'exit' for finish work with program");
    }

    public void setState(CliState state) {
        this.state = state;
        state.init();
    }

    public DatabaseSqlManagerConnector getSqlConnector() {
        return sqlConnector;
    }

    public Scanner getScanner() {
        return scanner;
    }

    private void startInputLoop() {

        while (true){
            String command = scanner.nextLine();

            switch (command){
                case "developers":
                    workingWithDeveloperCrud();
                    break;
                case "skills":
                    workingWithSkillCrud();
                    break;
                case "company":
                    workingWithCompanyCrud();
                    break;
                case "customer":
                    workingWithCustomerCrud();
                    break;
                case "project":
                    workingWithProjectCrud();
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    unknownTable(command);
                    break;
            }
        }
    }

    private void workingWithProjectCrud() {
        state.workingWithProjectCrud();
    }

    private void workingWithCustomerCrud() {
        state.workingWithCustomerCrud();
    }

    private void workingWithCompanyCrud() {
        state.workingWithCompanyCrud();
    }

    private void workingWithSkillCrud() {
        state.workingWithSkillCrud();
    }

    private void workingWithDeveloperCrud() {
        state.workingWithDeveloperCrud();
    }

    public void unknownTable(String cmd){
        state.unknownTable(cmd);
    }
    public int writeDigit() {
        int digit;
        while (true) {
            try {
                digit = Integer.parseInt(getScanner().nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid values.Write digits");
            }
        }
        return digit;
    }
}
