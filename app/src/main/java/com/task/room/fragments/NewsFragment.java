package com.task.room.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.task.room.CheckInternet;
import com.task.room.viewModels.NewsViewModel;
import com.task.room.R;
import com.task.room.adapters.NewsAdapter;
import com.task.room.model.NewsResponse;
import com.task.room.model.Result;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment  {


    ArrayList<Result> articleArrayList = new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView rvHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Boolean isScrolling= false;
    private int currentItem , totalItems , scrollOutItems;
    private LinearLayoutManager mLayoutManager;
    private int pageNo = 1;


    String title,sectionName,url;

    private static final String TAG = "NewsFragment";

    private NewsViewModel pageViewModel;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
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
        View root = inflater.inflate(R.layout.news_fragment, container, false);
        rvHeadline = root.findViewById(R.id.news);
        swipeRefreshLayout = root.findViewById(R.id.swip_refresh_layout);


        if (CheckInternet.isNetwork(getActivity())) {
            pageViewModel.getNewsRepository(pageNo).observe(this, new Observer<NewsResponse>() {
                @Override
                public void onChanged(NewsResponse newsResponse) {
                    List<Result> newsArticles = newsResponse.getResponse().getResults();

                    articleArrayList.addAll(newsArticles);
                    newsAdapter.notifyDataSetChanged();

                }
            });
        } else {
            Toast.makeText(getActivity(), "check the internet connection", Toast.LENGTH_SHORT).show();
        }

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                if (CheckInternet.isNetwork(getActivity())) {
                    pageViewModel.getNewsRepository(pageNo).observe(getActivity(), new Observer<NewsResponse>() {
                        @Override
                        public void onChanged(NewsResponse newsResponse) {
                            List<Result> newsArticles = newsResponse.getResponse().getResults();
                            articleArrayList.addAll(newsArticles);

                            newsAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "check the internet connection", Toast.LENGTH_SHORT).show();
                }
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        rvHeadline.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItem + scrollOutItems == totalItems)){
                    isScrolling = false;
                    //add new data to the list after swiping
                    fetchData();

                }
            }
        });

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            newsAdapter = new NewsAdapter(getActivity(), articleArrayList, pageViewModel);
            rvHeadline.setLayoutManager(mLayoutManager);
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }

    }



    private void fetchData(){
        pageNo = pageNo + 1 ;
        pageViewModel.getNewsRepository(pageNo).observe(getActivity(), new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {
                List<Result> newsArticles = newsResponse.getResponse().getResults();
                articleArrayList.addAll(newsArticles);

                newsAdapter.notifyDataSetChanged();
            }
        });
    }
}