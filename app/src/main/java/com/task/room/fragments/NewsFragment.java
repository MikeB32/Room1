package com.task.room.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.task.room.CheckInternet;
import com.task.room.FavNews;
import com.task.room.NoteViewModel;
import com.task.room.OnItemClick;
import com.task.room.R;
import com.task.room.adapters.NewsAdapter;
import com.task.room.model.NewsResponse;
import com.task.room.model.Result;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {


    ArrayList<Result> articleArrayList = new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView rvHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "NewsFragment";

    private NoteViewModel pageViewModel;

    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance() {
        return new NewsFragment();
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
        View root = inflater.inflate(R.layout.news_fragment, container, false);
        rvHeadline = root.findViewById(R.id.rvNews);
        swipeRefreshLayout= root.findViewById(R.id.swip_refresh_layout);




        if (CheckInternet.isNetwork(getActivity())) {
            pageViewModel.getNewsRepository().observe(this, new Observer<NewsResponse>() {
                @Override
                public void onChanged(NewsResponse newsResponse) {
                    List<Result> newsArticles = newsResponse.getResponse().getResults();

                    articleArrayList.addAll(newsArticles);


                    newsAdapter.notifyDataSetChanged();

                }
            });
        }else{
            Toast.makeText(getActivity(),"something",Toast.LENGTH_SHORT).show();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CheckInternet.isNetwork(getActivity())) {
                    pageViewModel.getNewsRepository().observe(getActivity(), new Observer<NewsResponse>() {
                        @Override
                        public void onChanged(NewsResponse newsResponse) {
                            List<Result> newsArticles = newsResponse.getResponse().getResults();
                            articleArrayList.addAll(newsArticles);

                            newsAdapter.notifyDataSetChanged();
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),"something",Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        setupRecyclerView();

        return root;
    }
    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(getActivity(), articleArrayList);
            rvHeadline.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }

    }



}