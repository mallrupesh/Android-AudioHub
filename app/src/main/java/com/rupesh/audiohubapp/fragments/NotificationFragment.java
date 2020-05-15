
package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.NotificationListAdapter;
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.helper.RequestHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceRequestCallBack;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class NotificationFragment extends Fragment implements InterfaceRequestCallBack, NotificationListAdapter.OnItemClickListener {

    private View rootView;

    private ArrayList<User> requestUsers;

    private NotificationListAdapter notificationListAdapter;

    private RecyclerView notificationRecyclerView;

    private RequestHelper requestHelper;

    private NotificationListAdapter.OnItemClickListener listener;

   // private NotificationFragPresenter notificationFragPresenter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        //notificationFragPresenter = new NotificationFragPresenter();
        listener = this;
        requestHelper = new RequestHelper();
        requestHelper.interfaceRequestCallBack = this;
        requestHelper.searchUser();

       // notificationFragPresenter.getNotification();

        return rootView;
    }

    public void initUI() {
        notificationRecyclerView = rootView.findViewById(R.id.recycleListViewNotification);
        notificationListAdapter = new NotificationListAdapter(requestUsers,listener, getContext());
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRecyclerView.setAdapter(notificationListAdapter);
    }

    @Override
    public void mapRequest(ArrayList<User> users) {
        requestUsers = users;
        initUI();
    }


    @Override
    public void onItemClicked(View v, User user) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        inviteDialogBox.setArguments(bundle);

        // can be null when Main -> AllUser
        bundle.putSerializable("project", null);
        inviteDialogBox.setArguments(bundle);

        //inviteDialogBox.inviteInterface = this;
        inviteDialogBox.show(getFragmentManager(),"inviteDialog");
    }
}

