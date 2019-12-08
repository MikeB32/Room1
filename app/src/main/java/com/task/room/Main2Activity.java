package com.task.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    private WebView web_View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent getUrl = getIntent();

        String url2 = getUrl.getStringExtra("url");
        String data = getUrl.getStringExtra("data");
//        SharedPreferences sharedPreferences = getSharedPreferences("aa",MODE_PRIVATE);
//        String dd = sharedPreferences.getString("data",null);
//        String ddd = sharedPreferences.getString("newUrl",null);

//        Log.e("some some",dd);
//        Log.e("som",ddd);

        web_View = findViewById(R.id.webView);

        web_View.setWebViewClient(new WebViewClient());

        if (CheckInternet.isNetwork(getApplicationContext())) {
            if(url2!=null) {
                Log.e("url2", url2);
                web_View.loadUrl(url2);
            }
//        } else {
//            FavNews favNews = new FavNews();
//            String xx =favNews.getOfflineData();


        }

//        FavNews favNews = new FavNews();
//       String b = favNews.getOfflineData();
//        Log.e("hmmm",b);
//        web_View.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//
//
//        String encodedHtml = Base64.encodeToString(dd.getBytes(), Base64.NO_PADDING);
//
//        web_View.loadData(encodedHtml, "text/html", "base64");
////        Log.e("data2",data);
    }
}
