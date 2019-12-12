package com.task.room.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.room.FavNews;
import com.task.room.viewModels.NewsViewModel;
import com.task.room.R;
import com.task.room.adapters.FavAdapter;

import java.util.List;

public class FavFragment extends Fragment {


    private NewsViewModel pageViewModel;
    private RecyclerView recyclerView;
    private FavAdapter favAdapter;



    public static FavFragment newInstance() {
        return new FavFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fav_fragment, container, false);
        recyclerView = root.findViewById(R.id.favNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favAdapter = new FavAdapter(getActivity(),pageViewModel);
        recyclerView.setAdapter(favAdapter);






        pageViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<FavNews>>() {
            @Override
            public void onChanged(List<FavNews> favNewsList) {

                favAdapter.setNotes(favNewsList);

                favAdapter.notifyDataSetChanged();


            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }
}
