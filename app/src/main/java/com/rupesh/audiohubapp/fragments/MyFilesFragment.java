package com.rupesh.audiohubapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.adapters.MyFilesListAdapter;
import com.rupesh.audiohubapp.presenter.MyFilesFragPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFilesFragment extends Fragment {

    private View rootView;
    private RecyclerView myFilesRecyclerView;
    private MyFilesListAdapter myFilesListAdapter;
    private MyFilesFragPresenter myFilesFragPresenter;

    public MyFilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_files, container, false);

        myFilesFragPresenter = new MyFilesFragPresenter();

        initUI();

        return rootView;
    }


    private void initUI() {
        myFilesRecyclerView = rootView.findViewById(R.id.recycleListViewMyFiles);
        myFilesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myFilesListAdapter = new MyFilesListAdapter(myFilesFragPresenter.queryData());
        myFilesRecyclerView.setAdapter(myFilesListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        myFilesListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myFilesListAdapter.stopListening();
    }
}
