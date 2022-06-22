package com.example.marvelcomics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> titles_array = new ArrayList<String>();
    private ArrayList<String> desc_array = new ArrayList<String>();
    private ArrayList<String> img_array = new ArrayList<String>();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recView);

        getJsonData();
        MyAdapter myAdapter = new MyAdapter(this, this.titles_array, this.desc_array, this.img_array);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type to search comic ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> result_titles = new ArrayList<String>();
                ArrayList<String> result_desc = new ArrayList<String>();
                ArrayList<String> result_imgs = new ArrayList<String>();
                ArrayList<String> check_search = new ArrayList<String>();

                for(String x: titles_array)
                {
                    if(x.contains(newText)){
                        int index = titles_array.indexOf(x);
                        result_titles.add(x);
                        result_desc.add(desc_array.get(index));
                        result_imgs.add(img_array.get(index));
                        check_search.add("");
                        if (result_titles.size() == 0){
                            check_search.add("There's no such comic....");
                            result_titles.add("");
                            result_desc.add("");
                            result_imgs.add("");
                        }
                    }
                }

                ((MyAdapter) recyclerView.getAdapter()).update(result_titles, result_desc, result_imgs, check_search);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void getJsonData(){
        String jsonString = Utils.getJsonFromAssets(getApplicationContext(), "response.json");
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONObject comics_data = json.getJSONObject("data");
            JSONArray comics = comics_data.getJSONArray("results");
            for (int i = 0; i < comics.length(); i ++){
                JSONObject temp = comics.getJSONObject(i);

                String temp_title = temp.getString("title");
                String temp_description = temp.getString("description");
                JSONArray img = temp.getJSONArray("images");

                String image = checkImage(img);

                this.titles_array.add(temp_title);
                this.desc_array.add(temp_description);
                this.img_array.add(image);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String checkImage(JSONArray obj_img){
        try {
            JSONObject img_temp = obj_img.getJSONObject(0);
            return img_temp.getString("path");
        }catch (Exception e){
            return "empty";
        }

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}