
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
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.presenter.NotificationFragPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class NotificationFragment extends Fragment implements NotificationListAdapter.OnItemClickListener {

    private View rootView;

    private NotificationListAdapter notificationListAdapter;

    private RecyclerView notificationRecyclerView;

    private NotificationListAdapter.OnItemClickListener listener;

    private NotificationFragPresenter notificationFragPresenter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationFragPresenter = new NotificationFragPresenter(this);
        listener = this;
        notificationFragPresenter.getNotification();
        return rootView;
    }

    public void initUI(ArrayList<User> users) {
        //notificationFragPresenter.prepareNotification();
        notificationRecyclerView = rootView.findViewById(R.id.recycleListViewNotification);
        notificationListAdapter = new NotificationListAdapter(users,listener, getContext());
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRecyclerView.setAdapter(notificationListAdapter);
    }


    @Override
    public void onItemClicked(View v, User user) {
        notificationFragPresenter.displayDialogBox(user);
    }


}

