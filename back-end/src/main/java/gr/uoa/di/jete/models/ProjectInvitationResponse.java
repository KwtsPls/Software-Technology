package gr.uoa.di.jete.models;

import java.util.List;

public class ProjectInvitationResponse {
    List<ProjectInvitation> invitationList;

    public ProjectInvitationResponse(List<ProjectInvitation> invitationList){
        this.invitationList = invitationList;
    }

    public List<ProjectInvitation> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<ProjectInvitation> invitationList) {
        this.invitationList = invitationList;
    }
}
