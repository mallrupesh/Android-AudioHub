package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.SearchAdapter;
import com.rupesh.audiohubapp.adapters.SearchListAdapter;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.presenter.SearchFragPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchListAdapter.OnItemClickListener, SearchAdapter.OnItemSearchListener{

    private View rootView;

    // Declare RecycleView
    private RecyclerView allUsersRecyclerView;

    // Declare adapter to be used with RecyclerView
    SearchListAdapter searchListAdapter;


    private EditText searchText;

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

        searchText = rootView.findViewById(R.id.search_fragment_editText);

        project = (Project) getArguments().getSerializable("project");

        searchFragPresenter = new SearchFragPresenter(this);

        // As this activity implements the AllUserAdapter nested interface
        listener = SearchFragment.this;

        initUI();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    searchFragPresenter.searchUser(s.toString());
                } else {
                    searchFragPresenter.searchUser("");
                }
            }
        });


        return rootView;
    }

    private void initUI(){
        allUsersRecyclerView = rootView.findViewById(R.id.recycleListViewSearch);
        allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new SearchListAdapter(searchFragPresenter.queryData(), listener, getContext());
        allUsersRecyclerView.setAdapter(searchListAdapter);
    }


    public void setSearchAdapter(SearchAdapter searchAdapter) {
        allUsersRecyclerView.setAdapter(searchAdapter);
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


    @Override
    public void onItemSearched(View v, User user) {
        searchFragPresenter.displayDialogBox(user, project);
    }
}
