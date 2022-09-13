package ua.ip.jdbc.table;

import java.util.Objects;

public class Companies {
    private Integer id;
    private String name;
    private Integer year_of_foundation;

    public Companies(Integer id, String name, Integer year_of_foundation) {
        this.id = id;
        this.name = name;
        this.year_of_foundation = year_of_foundation;
    }

    public Companies() {
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

    public Integer getYear_of_foundation() {
        return year_of_foundation;
    }

    public void setYear_of_foundation(Integer year_of_foundation) {
        this.year_of_foundation = year_of_foundation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Companies companies = (Companies) o;
        return Objects.equals(id, companies.id) && Objects.equals(name, companies.name) && Objects.equals(year_of_foundation, companies.year_of_foundation);
    }

}
