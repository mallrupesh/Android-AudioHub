package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.SearchListAdapter;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.presenter.SearchFragPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchListAdapter.OnItemClickListener{

    private View rootView;

    // Declare RecycleView
    private RecyclerView allUsersRecyclerView;

    // Declare adapter to be used with RecyclerView
    SearchListAdapter searchListAdapter;

    // Declare Firebase Database reference
    //private DatabaseReference mUserDataRef;

    private SearchFragPresenter searchFragPresenter;

    private SearchListAdapter.OnItemClickListener listener;

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

        //mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        project = (Project) getArguments().getSerializable("project");

        searchFragPresenter = new SearchFragPresenter(this);

        // As this activity implements the AllUserAdapter nested interface
        listener = SearchFragment.this;

        initUI();

        return rootView;
    }

    private void initUI(){
        allUsersRecyclerView = rootView.findViewById(R.id.recycleListViewSearch);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new SearchListAdapter(searchFragPresenter.queryData(), listener, getContext());
        allUsersRecyclerView.setAdapter(searchListAdapter);
    }

    // On Activity start, set adapter to start listening
    @Override
    public void onStart() {
        super.onStart();
        searchListAdapter.startListening();
    }

    // On Activity stop, set adapter to stop listening
    @Override
    public void onStop() {
        super.onStop();
        searchListAdapter.stopListening();
    }

    @Override
    public void onItemClicked(View v, User user) {
        searchFragPresenter.displayDialogBox(user, project);
    }
}
