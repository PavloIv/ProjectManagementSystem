package ua.ip.jdbc.table;

public class ProjectsFormatCreationDateNameNumberProgramer {
    private String creationDate;
    private String name;
    private Integer numberProgramerOnProject;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberProgramerOnProject() {
        return numberProgramerOnProject;
    }

    public void setNumberProgramerOnProject(Integer numberProgramerOnProject) {
        this.numberProgramerOnProject = numberProgramerOnProject;
    }

    @Override
    public String toString() {
        return "\n" + "{" +
                "creationDate='" + creationDate + '\'' +
                ", name='" + name + '\'' +
                ", numberProgramerOnProject=" + numberProgramerOnProject +
                '}' + "\n";
    }
}
