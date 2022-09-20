package ua.ip.jdbc.cli;

import ua.ip.jdbc.dao.CompaniesDAO;
import ua.ip.jdbc.table.Companies;

public class CompanyCrudState extends CliState {

    private CompaniesDAO companiesDAO;

    public CompanyCrudState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        System.out.println("Please, write command with list:");
        System.out.println("'create' for create new company");
        System.out.println("'read' for see list all companies ");
        System.out.println("'update' for update company with id");
        System.out.println("'delete' for delete company with id");
        System.out.println("'find' for see company with id");
        System.out.println("'back' for back to previous menu");
        companiesDAO = new CompaniesDAO(fsm.getSqlConnector());
        String command = fsm.getScanner().nextLine();
        startCustomersCrudLoop(command);
    }

    private void startCustomersCrudLoop(String command) {
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

    private void create() {
        System.out.println("Write company name:");
        String companyName = fsm.getScanner().nextLine();

        System.out.println("Write company year of foundation:");
        Integer yearOfFoundation = fsm.writeDigit();

        Companies company = new Companies();
        company.setName(companyName);
        company.setYear_of_foundation(yearOfFoundation);

        if (companiesDAO.create(company)) {
            System.out.println("Company add to database");
        } else {
            System.out.println("We have problem with add company,try again");
        }
    }

    private void readTable() {
        System.out.println("All customers list = " + companiesDAO.findAll());
    }

    private void update() {
        Integer companyId = null;
        while (true) {
            System.out.println("Write company id:");
            companyId = fsm.writeDigit();

            if (companiesDAO.findById(companyId) == null) {
                System.out.println("Company not found.Please try again");
            }else {break;}
        }


        System.out.println("Write company name:");
        String companyName = fsm.getScanner().nextLine();

        System.out.println("Write company year of foundation:");
        Integer yearOfFoundation = fsm.writeDigit();

        Companies company = new Companies(companyId,companyName,yearOfFoundation);

        companiesDAO.update(company);

        System.out.printf("Company with id = %s update.", companyId);
    }

    private void delete() {
        System.out.println("Write company id to delete:");
        int companyId = fsm.writeDigit();
        companiesDAO.delete(companyId);
        System.out.println("Company delete is complete.");
    }

    private void find() {
        System.out.println("Write company id:");
        Integer companyId= fsm.writeDigit();

        System.out.print("Customer :");
        System.out.println(companiesDAO.findById(companyId));
    }
}
