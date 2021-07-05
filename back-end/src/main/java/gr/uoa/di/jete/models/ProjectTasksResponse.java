package gr.uoa.di.jete.models;

import java.util.List;

public class ProjectTasksResponse {
    List<ProjectTasks> tasksList;

    public ProjectTasksResponse(List<ProjectTasks> tasksList){
        this.tasksList = tasksList;
    }

    public List<ProjectTasks> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<ProjectTasks> tasksList) {
        this.tasksList = tasksList;
    }
}
