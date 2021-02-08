package com.example.tp_steam_dicostanzo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class affichage_liens extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_liens);

        textView = findViewById(R.id.textView);

        ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");

        for(int i = 0; i < urls.size(); i++) CreateURL(urls.get(i));
    }

    void CreateURL(String url){ textView.setText(textView.getText() + "\n\n" + url.replace(" ", ".")); }
}