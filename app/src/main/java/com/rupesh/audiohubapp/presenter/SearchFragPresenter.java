package com.rupesh.audiohubapp.presenter;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.fragments.SearchFragment;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class SearchFragPresenter {

    // Declare Firebase Database reference
    private DatabaseReference mUserDataRef;
    private SearchFragment searchFragment;

    public SearchFragPresenter(SearchFragment searchFragment) {
        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
        this.searchFragment = searchFragment;
    }

    public FirebaseRecyclerOptions<User> queryData() {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();
        return options;
    }

    public void displayDialogBox(User user, Project project) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        // can be null when Main -> AllUser
        bundle.putSerializable("project", project);
        inviteDialogBox.setArguments(bundle);

        //inviteDialogBox.inviteInterface = this;
        inviteDialogBox.show(searchFragment.getFragmentManager(), "inviteDialog");
    }
}
