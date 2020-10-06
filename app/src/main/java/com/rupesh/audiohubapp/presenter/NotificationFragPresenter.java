package com.rupesh.audiohubapp.presenter;

import android.os.Bundle;

import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.fragments.NotificationFragment;
import com.rupesh.audiohubapp.helper.NotificationHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

/**
 * Perform piping data(User model)from NotificationHelper to Notification Fragment
 *
 */
public class NotificationFragPresenter implements InterfaceRequestCallBack {

    private NotificationHelper notificationHelper;
    private NotificationFragment notificationFragment;

    public NotificationFragPresenter(NotificationFragment notificationFragment) {
        notificationHelper = new NotificationHelper();
        notificationHelper.interfaceRequestCallBack = this;
        this.notificationFragment = notificationFragment;
    }

    public void getNotification() {
        notificationHelper.getUsers();
    }

    /**
     * Get data (User model) from NotificationHelper and call UI method
     * @param users
     */
    @Override
    public void mapRequest(ArrayList<User> users) {
        notificationFragment.initUI(users);
    }

    /**
     * Display InviteDialogBox
     * @param user
     */
    public void displayDialogBox(User user) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();

        // From NotificationListAdapter
        bundle.putSerializable("user", user);

        // Null Project list item is not accessed
        bundle.putSerializable("project", null);
        inviteDialogBox.setArguments(bundle);
        inviteDialogBox.show(notificationFragment.getFragmentManager(), "inviteDialog");
    }
}
