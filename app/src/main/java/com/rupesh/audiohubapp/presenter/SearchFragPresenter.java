package com.rupesh.audiohubapp.presenter;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.adapters.SearchAdapter;
import com.rupesh.audiohubapp.dialogboxes.InviteDialogBox;
import com.rupesh.audiohubapp.fragments.SearchFragment;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;


public class SearchFragPresenter {

    // Declare Firebase Database reference
    private DatabaseReference mUserDataRef;
    private SearchFragment searchFragment;
    private ArrayList<User> userList;

    public SearchFragPresenter(SearchFragment searchFragment) {
        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
        this.searchFragment = searchFragment;
        userList = new ArrayList<>();
    }

    public FirebaseRecyclerOptions<User> queryData() {
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();
        return options;
    }



    public void searchUser(String searchTxt) {
        Query searchQuery = mUserDataRef.orderByChild("name").startAt(searchTxt)
                            .endAt(searchTxt + "\uf8ff");

        searchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    userList.clear();
                    for (DataSnapshot postData: dataSnapshot.getChildren()) {
                        final User userItem = postData.getValue(User.class);
                        userList.add(userItem);
                    }

                    SearchAdapter searchAdapter = new SearchAdapter(searchFragment.getContext(),userList, searchFragment);
                    searchFragment.setSearchAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Display InviteDialogBox
     * @param user
     * @param project
     */
    public void displayDialogBox(User user, Project project) {
        InviteDialogBox inviteDialogBox = new InviteDialogBox();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        bundle.putSerializable("project", project);
        inviteDialogBox.setArguments(bundle);

        inviteDialogBox.show(searchFragment.getFragmentManager(), "inviteDialog");
    }
}
