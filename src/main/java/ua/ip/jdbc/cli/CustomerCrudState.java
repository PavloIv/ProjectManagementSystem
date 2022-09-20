package ua.ip.jdbc.cli;

import ua.ip.jdbc.dao.CustomerDao;
import ua.ip.jdbc.table.Customers;

public class CustomerCrudState extends CliState{

    private CustomerDao customerDao;

    public CustomerCrudState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        System.out.println("Please, write command with list:");
        System.out.println("'create' for create new customer");
        System.out.println("'read' for see list all customers ");
        System.out.println("'update' for update customer with id");
        System.out.println("'delete' for delete customer with id");
        System.out.println("'find' for see customer with id");
        System.out.println("'back' for back to previous menu");
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
        System.out.println("Write customer name:");
        String customerName = fsm.getScanner().nextLine();

        System.out.println("Write customer website:");
        String customerWebsite = fsm.getScanner().nextLine();

        Customers customer = new Customers();
        customer.setName(customerName);
        customer.setWebsite(customerWebsite);

        if (customerDao.create(customer)) {
            System.out.println("Customer add to database");
        } else {
            System.out.println("We have problem with add customer,try again");
        }
    }

    private void readTable() {
        System.out.println("All customers list = " + customerDao.findAll());
    }

    private void update() {

        System.out.println("Write customer id:");
        Integer customerId = fsm.writeDigit();

        System.out.println("Write customer name:");
        String customerName = fsm.getScanner().nextLine();

        System.out.println("Write customer age:");
        String customerWebsite = fsm.getScanner().nextLine();

        Customers customers = new Customers(customerId,customerName,customerWebsite);

        customerDao.update(customers);

        System.out.printf("Customer with id = %s update.", customerId);
    }

    private void delete() {
        System.out.println("Write customer id to delete:");
        int customerId = fsm.writeDigit();
        customerDao.delete(customerId);
        System.out.println("Customer delete is complete.");
    }

    private void find() {
        System.out.println("Write customer id:");
        Integer customerId= fsm.writeDigit();

        System.out.print("Customer :");
        System.out.println(customerDao.findById(customerId));
    }
}
