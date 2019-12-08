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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.task.room.FavNews;
import com.task.room.NewsRepository;
import com.task.room.NoteViewModel;
import com.task.room.OnItemClick;
import com.task.room.R;
import com.task.room.adapters.FavAdapter;
import com.task.room.adapters.NewsAdapter;
import com.task.room.model.Result;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment {

    private static final String TAG = "FavFragment";

    private NoteViewModel pageViewModel;
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
        pageViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


//        pageViewModel.setIndex(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fav_fragment, container, false);
        recyclerView = root.findViewById(R.id.rvNews2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        favAdapter = new FavAdapter(getActivity(),pageViewModel);
        recyclerView.setAdapter(favAdapter);






        pageViewModel.getAllNotes().observe(this, new Observer<List<FavNews>>() {
            @Override
            public void onChanged(List<FavNews> favNewsList) {

                favAdapter.setNotes(favNewsList);



                favAdapter.notifyDataSetChanged();

                Log.e("size",favNewsList.size()+"");

            }
        });

        return root;
    }


}
