/*
package com.rupesh.audiohubapp.presenter;

import com.rupesh.audiohubapp.fragments.NotificationFragment;
import com.rupesh.audiohubapp.helper.RequestHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class NotificationFragPresenter implements InterfaceRequestCallBack {

    private ArrayList<User> requestUsers;

    private RequestHelper requestHelper;

    private NotificationFragment notificationFragment;

    public NotificationFragPresenter() {
        requestHelper = new RequestHelper();
        requestHelper.interfaceRequestCallBack = this;
        notificationFragment = new NotificationFragment();
    }

    public void getNotification() {
        requestHelper.searchUser();
    }

    @Override
    public void mapRequest(ArrayList<User> users) {
        requestUsers = users;
        notificationFragment.initUI();
    }
}
*/
