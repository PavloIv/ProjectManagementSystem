package ua.ip.jdbc.cli;

import ua.ip.jdbc.dao.CompaniesDAO;
import ua.ip.jdbc.dao.CustomerDao;
import ua.ip.jdbc.dao.ProjectsDao;
import ua.ip.jdbc.table.Projects;


public class ProjectsCrudState extends CliState{

    private ProjectsDao projectsDao;
    private CompaniesDAO companiesDAO;
    private CustomerDao customerDao;
    public ProjectsCrudState(CliFSM fsm) {
        super(fsm);
    }
    @Override
    public void init() {
        System.out.println("Please, write project with list:");
        System.out.println("'create' for create new project");
        System.out.println("'read' for see list all projects ");
        System.out.println("'update' for update project with id");
        System.out.println("'delete' for delete project with id");
        System.out.println("'find' for see project with id");
        System.out.println("'show' for see list programmer in format creationDate nameNumber numberProgrammerOnProject");
        System.out.println("'back' for back to previous menu");
        projectsDao = new ProjectsDao(fsm.getSqlConnector());
        companiesDAO = new CompaniesDAO(fsm.getSqlConnector());
        customerDao = new CustomerDao(fsm.getSqlConnector());
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
            case "show":
                showListProgrammerInFormat();
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
        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.println("Write project description:");
        String projectDescription = fsm.getScanner().nextLine();

        System.out.println(companiesDAO.findAll());
        System.out.println("Write company id from list companies upper:");
        Integer companyId;
        while (true) {
            companyId = fsm.writeDigit();

            if (companiesDAO.findById(companyId) == null) {
                System.out.println("Company not found.Please try again");
            }else {break;}
        }

        System.out.println(customerDao.findAll());
        System.out.println("Write customer id from list customers upper:");
        Integer customerId;
        while (true) {
            customerId = fsm.writeDigit();

            if (customerDao.findById(customerId) == null) {
                System.out.println("Customer not found.Please try again");
            }else {break;}
        }

        Projects project = new Projects();
        project.setName(projectName);
        project.setDescription(projectDescription);
        project.setCompany_id(companyId);
        project.setCustomer_id(customerId);

        if (projectsDao.create(project)) {
            System.out.println("Project add to database");
        } else {
            System.out.println("We have problem with add project,try again");
        }
    }

    private void readTable() {
        System.out.println("All projects list = " + projectsDao.findAll());
    }

    private void update() {

        Integer projectId;
        while (true) {
            System.out.println("Write project id:");
            projectId = fsm.writeDigit();

            if (projectsDao.findById(projectId) == null) {
                System.out.println("Project not found.Please try again");
            }else {break;}
        }

        System.out.println("Write project name:");
        String projectName = fsm.getScanner().nextLine();

        System.out.println("Write project description:");
        String projectDescription = fsm.getScanner().nextLine();

        System.out.println(companiesDAO.findAll());
        System.out.println("Write company id from list companies upper:");
        Integer companyId;
        while (true) {
            companyId = fsm.writeDigit();

            if (companiesDAO.findById(companyId) == null) {
                System.out.println("Company not found.Please try again");
            }else {break;}
        }

        System.out.println(customerDao.findAll());
        System.out.println("Write customer id from list customers upper:");
        Integer customerId;
        while (true) {
            customerId = fsm.writeDigit();

            if (customerDao.findById(customerId) == null) {
                System.out.println("Customer not found.Please try again");
            }else {break;}
        }

        Projects project = new Projects(projectId,projectName,projectDescription,companyId,customerId);

        projectsDao.update(project);

        System.out.printf("Project with id = %s update.", projectId);
    }

    private void delete() {
        System.out.println("Write project id to delete:");
        Integer projectId = fsm.writeDigit();
        projectsDao.delete(projectId);
        System.out.println("Project delete is complete.");
    }

    private void find() {
        System.out.println("Write project id:");
        Integer projectId= fsm.writeDigit();

        System.out.print("Project :");
        System.out.println(projectsDao.findById(projectId));
    }

    private void showListProgrammerInFormat() {
        System.out.println(projectsDao.showProjectsListInFormat());
    }
}
