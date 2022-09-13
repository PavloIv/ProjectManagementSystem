package ua.ip.jdbc.table;

import java.util.Objects;

public  class Projects {
    private Integer id;
    private String name;
    private String description;
    private Integer cost;
    private Integer company_id;
    private Integer customer_id;

    public Projects(Integer id, String name, String description, Integer cost, Integer company_id, Integer customer_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.company_id = company_id;
        this.customer_id = customer_id;
    }

    public Projects() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projects projects = (Projects) o;
        return Objects.equals(id, projects.id) && Objects.equals(name, projects.name) && Objects.equals(description, projects.description) && Objects.equals(cost, projects.cost) && Objects.equals(company_id, projects.company_id) && Objects.equals(customer_id, projects.customer_id);
    }
}
