package com.example.vb.test_gson_retrofit_realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://api.github.com" ;
    private Realm realm;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    Clubs clubs;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

    }

    public void ParseGSON (View view)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                        URL url = new URL("https://api.myjson.com/bins/1gx4b1");


                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    resultJson = buffer.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("resultHSOBN", String.valueOf(resultJson.length()));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                clubs = gson.fromJson(resultJson, Clubs.class);
                Log.d("Realm", String.valueOf(clubs.getClubs().size()));
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void ParseRETROFIT (View view)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Retrofit client = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GithubAPIService service = client.create(GithubAPIService.class);
                Call<GithubUser> call = service.getUser("ZapevalovAnton");
                try {
                    GithubUser k = call.execute().body();
                    Log.d("resultHSOBN", k.getName() + k.getEventsUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void AddRealm(View view)
    {
        realm.beginTransaction();
        realm.deleteAll();
            realm.copyToRealm(clubs.getClubs());
        //ggg;g
        realm.commitTransaction();
        Log.d("Realm", "addRealm");
    }

    public void ReadRealm(View view)
    {
        realm.beginTransaction();
        List<Club> clubbss = realm.copyFromRealm(realm.where(Club.class).findAll());
        realm.commitTransaction();
            Log.d("Realm", String.valueOf(clubbss.size()));

    }

    public void onDestroy() {

        super.onDestroy();

    }

}
