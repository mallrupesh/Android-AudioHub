package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.AllUserListAdapter;
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements AllUserListAdapter.OnItemClickListener{

    private View rootView;

    // Declare RecycleView
    private RecyclerView allUsersRecyclerView;

    // Declare adapter to be used with RecyclerView
    AllUserListAdapter allUserListAdapter;

    // Declare Firebase Database reference
    private DatabaseReference mUserDataRef;

    private AllUserListAdapter.OnItemClickListener listener;

    // Declare String to track Project id
    //private String projectId;

    // Member -> AllUser (NotNull) Main -> AllUser (null)
    private Project project;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        project = (Project) getArguments().getSerializable("project");

        // As this activity implements the AllUserAdapter nested interface
        listener = SearchFragment.this;

        initUI();

        return rootView;
    }

    private void initUI(){
        allUsersRecyclerView = rootView.findViewById(R.id.recycleListViewSearch);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO list
        // 1. filter all user who are already in the project.
        // 2. list sud contain all the user who are not in the project.
        // 3. we need a list of users from the MembersFragment to filter them out from the search.

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();
        allUserListAdapter = new AllUserListAdapter(options, listener, getContext());
        allUsersRecyclerView.setAdapter(allUserListAdapter);
    }

    // On Activity start, set adapter to start listening
    @Override
    public void onStart() {
        super.onStart();
        allUserListAdapter.startListening();
    }


    // On Activity stop, set adapter to stop listening
    @Override
    public void onStop() {
        super.onStop();
        allUserListAdapter.stopListening();
    }

    @Override
    public void onItemClicked(View v, User user) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        // can be null when Main -> AllUser
        bundle.putSerializable("project", project);
        inviteDialogBox.setArguments(bundle);

        //inviteDialogBox.inviteInterface = this;
        assert getFragmentManager() != null;
        inviteDialogBox.show(getFragmentManager(),"inviteDialog");
    }


}
