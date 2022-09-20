package ua.ip.jdbc.table;

public class ProjectsFormatCreationDateNameNumberProgrammer {
    private String creationDate;
    private String name;
    private Integer numberProgrammerOnProject;


    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberProgrammerOnProject(Integer numberProgrammerOnProject) {
        this.numberProgrammerOnProject = numberProgrammerOnProject;
    }

    @Override
    public String toString() {
        return "\n" + "{" +
                "creationDate='" + creationDate + '\'' +
                ", name='" + name + '\'' +
                ", numberProgrammerOnProject=" + numberProgrammerOnProject +
                '}' + "\n";
    }
}
