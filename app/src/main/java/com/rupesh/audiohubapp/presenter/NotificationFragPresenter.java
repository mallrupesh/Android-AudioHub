package com.rupesh.audiohubapp.presenter;

import android.os.Bundle;

import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.fragments.NotificationFragment;
import com.rupesh.audiohubapp.helper.NotificationHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class NotificationFragPresenter implements InterfaceRequestCallBack {

    private NotificationHelper notificationHelper;
    private NotificationFragment notificationFragment;

    public NotificationFragPresenter(NotificationFragment notificationFragment) {
        notificationHelper = new NotificationHelper();
        notificationHelper.interfaceRequestCallBack = this;
        this.notificationFragment = notificationFragment;
    }

    public void getNotification() {
        notificationHelper.searchUser();
    }

    @Override
    public void mapRequest(ArrayList<User> users) {
        notificationFragment.initUI(users);
    }

    public void displayDialogBox(User user) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        // can be null when Main -> AllUser
        bundle.putSerializable("project", null);
        inviteDialogBox.setArguments(bundle);

        //inviteDialogBox.inviteInterface = this;
        inviteDialogBox.show(notificationFragment.getFragmentManager(), "inviteDialog");
    }
}
