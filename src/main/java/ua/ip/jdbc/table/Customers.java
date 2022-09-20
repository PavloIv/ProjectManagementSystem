package ua.ip.jdbc.table;

import java.util.Objects;

public  class Customers {
    private Integer id;
    private String name;
    private String Website;

    public Customers(Integer id, String name, String website) {
        this.id = id;
        this.name = name;
        Website = website;
    }

    public Customers() {
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

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customers = (Customers) o;
        return Objects.equals(id, customers.id) && Objects.equals(name, customers.name) && Objects.equals(Website, customers.Website);
    }

    @Override
    public String toString() {
        return  "\n" + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Website='" + Website + '\'' +
                '}' + "\n";
    }
}
