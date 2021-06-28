package gr.uoa.di.jete.exceptions;


import gr.uoa.di.jete.models.AssigneeId;

public class AssigneeNotFoundException extends RuntimeException {
    public AssigneeNotFoundException(AssigneeId id) {
        super("Could not find Assignee with user_id"+ id.getUser_id() + " in project " + id.getProject_id()
                + " in sprint and epic" + id.getSprint_id() + "&" + id.getEpic_id() + " in story " + id.getStory_id() + " and in task " + id.getTask_id());
    }
}
