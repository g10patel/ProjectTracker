import java.util.ArrayList;

public class Project {

    String projectName;
    ArrayList<Task> tasks;

    public Project(String projectName, ArrayList<Task> tasks){
        this.projectName = projectName;
        this.tasks = tasks;
    }

    public static ArrayList<Project> getProjects(ArrayList<Task> tasks) {
        if (tasks.size() != 0) {
            ArrayList<Project> projects = new ArrayList<>();
            ArrayList<Task> buffer = new ArrayList<>();
            for (Task i : tasks) {
                if (tasks.indexOf(i) == 0) {
                    buffer.add(i);
                } else {
                    if (i.projectName == buffer.get(0).projectName) {
                        buffer.add(i);
                    } else {
                        Project newProject = new Project(buffer.get(0).projectName, buffer);
                        projects.add(newProject);
                        buffer.clear();
                        buffer.add(i);

                    }
                }
            }
            Project newProject = new Project(buffer.get(0).projectName, buffer);
            projects.add(newProject);
            return projects;
        }
        return null;
    }
}
