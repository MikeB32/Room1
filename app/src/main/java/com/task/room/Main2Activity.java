package com.task.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        web_View = findViewById(R.id.webView);
        web_View.setWebViewClient(new WebViewClient());
        web_View.loadUrl(url2);
    }
}
