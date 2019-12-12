package com.task.room.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.task.room.CheckInternet;
import com.task.room.R;
import com.task.room.viewModels.NewsViewModel;


public class WebPageActivity extends AppCompatActivity {

    private WebView web_View;
    private NewsViewModel newsViewModel;
    private Intent webData;
    private String url;
    private int id;
    private String offlineData;
    private String encodedHtml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_webpage);
        newsViewModel = new NewsViewModel(getApplication());
        webData = getIntent();


        web_View = findViewById(R.id.webView);
        web_View.setWebViewClient(new WebViewClient());

        if (CheckInternet.isNetwork(getApplicationContext())) {
            url = webData.getStringExtra("url");
            if(url!=null) {
                web_View.loadUrl(url);
            }
        } else {
            id = webData.getIntExtra("data",0);
                offlineData = newsViewModel.singleLoad(id);
                if(offlineData.equals("")){
                    Toast.makeText(getApplicationContext(),"Offline not available",Toast.LENGTH_SHORT).show();
                }else{
                    encodedHtml = Base64.encodeToString(offlineData.getBytes(), Base64.NO_PADDING);
                    web_View.loadData(encodedHtml, "text/html", "base64");
                }

        }

    }

    @Override
    public void onBackPressed() {
        if (web_View.canGoBack()) {
            web_View.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
