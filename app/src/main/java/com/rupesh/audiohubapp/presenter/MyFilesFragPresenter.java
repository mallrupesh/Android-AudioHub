package com.rupesh.audiohubapp.presenter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rupesh.audiohubapp.model.File;

public class MyFilesFragPresenter {

    private DatabaseReference myFilesDataRef;

    public MyFilesFragPresenter() {
        myFilesDataRef = FirebaseDatabase.getInstance().getReference().child("Files");
    }

    public FirebaseRecyclerOptions<File> queryData() {
        FirebaseRecyclerOptions<File> options =
                new FirebaseRecyclerOptions.Builder<File>()
                        .setQuery(myFilesDataRef.orderByChild("creatorId")
                                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()), File.class).build();
        return options;
    }
}
