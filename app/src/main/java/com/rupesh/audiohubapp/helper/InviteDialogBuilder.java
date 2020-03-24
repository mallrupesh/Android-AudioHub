package com.rupesh.audiohubapp.helper;

public class InviteDialogBuilder {

    public Boolean inviteNeeded;
    public Boolean declineNeeded;

    public InviteDialogBuilder() {
        this.inviteNeeded = false;
        this.declineNeeded = false;
    }

    public  InviteDialogBuilder(Boolean inviteNeeded, Boolean declineNeeded) {
        this.inviteNeeded = inviteNeeded;
        this.declineNeeded = declineNeeded;
    }

    public Boolean getInviteNeeded() {
        return inviteNeeded;
    }

    public void setInviteNeeded(Boolean inviteNeeded) {
        this.inviteNeeded = inviteNeeded;
    }

    public Boolean getDeclineNeeded() {
        return declineNeeded;
    }

    public void setDeclineNeeded(Boolean declineNeeded) {
        this.declineNeeded = declineNeeded;
    }
}
