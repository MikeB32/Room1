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

    private static final String TAG = "FavFragment";

    private NewsViewModel pageViewModel;
    private RecyclerView recyclerView;
    FavAdapter favAdapter;


    /**
     * @return A new instance of fragment SpeedDialFragment.
     */
    public static FavFragment newInstance() {
        return new FavFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);


//        pageViewModel.setIndex(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fav_fragment, container, false);
        recyclerView = root.findViewById(R.id.favNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favAdapter = new FavAdapter(getActivity(),pageViewModel);
        recyclerView.setAdapter(favAdapter);






        pageViewModel.getAllNotes().observe(this, new Observer<List<FavNews>>() {
            @Override
            public void onChanged(List<FavNews> favNewsList) {

                favAdapter.setNotes(favNewsList);

                favAdapter.notifyDataSetChanged();


            }
        });

        return root;
    }


}
