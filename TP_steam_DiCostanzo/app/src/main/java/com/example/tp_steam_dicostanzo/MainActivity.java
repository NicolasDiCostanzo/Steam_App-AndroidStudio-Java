package com.example.tp_steam_dicostanzo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button searchButton, csButton, dotaButton;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.search);
        csButton = findViewById(R.id.cs_button);
        dotaButton = findViewById(R.id.dota_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int appid = Integer.parseInt(editText.getText().toString());
                SendRequestWithCustomAPPID(appid);
            }
        });

        csButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { SendRequestWithCustomAPPID(730); }
        });

        dotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { SendRequestWithCustomAPPID(570); }
        });


    }

    void SendRequestWithCustomAPPID(int appid){
        try {
            json = getResponseText("https://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=" + String.valueOf(appid) + "&count=3&maxlength=300&format=json");

            JSONArray newsItems = new JSONObject(json).getJSONObject("appnews").getJSONArray("newsitems");
            ArrayList<String> urls = new ArrayList<>();

            int limitNbOfLinks = 5;

            for (int i = 0; i < newsItems.length() && i < limitNbOfLinks; i++)
                urls.add(newsItems.getJSONObject(i).getString("url"));

            Intent newActivity = new Intent(this, affichage_liens.class);
            newActivity.putExtra("urls", urls);
            startActivity(newActivity);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String getResponseText(String stringUrl) throws IOException
    {
        StringBuilder response  = new StringBuilder();

        URL url = new URL(stringUrl);
        HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();

        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
            String strLine = null;

            while ((strLine = input.readLine()) != null)
                response.append(strLine);

            input.close();
        }
        return response.toString();
    }
}