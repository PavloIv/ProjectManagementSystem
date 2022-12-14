package ua.ip.jdbc.table;


import java.util.Objects;

public class Skills {
    private Integer id;
    private String language;
    private String level;

    public Skills(Integer id, String language, String level) {
        this.id = id;
        this.language = language;
        this.level = level;
    }

    public Skills() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skills skills = (Skills) o;
        return Objects.equals(id, skills.id) && Objects.equals(language, skills.language) && Objects.equals(level, skills.level);
    }

    @Override
    public String toString() {
        return  "\n" + "{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", level='" + level + '\'' +
                '}'+ '\n';
    }
}
