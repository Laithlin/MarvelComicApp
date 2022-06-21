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
//    private final ArrayList<String[]> c_data = new ArrayList<String[]>();
    private String[] titles_array = new String[25];
    private String[] desc_array = new String[25];
    private String[] img_array = new String[25];

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recView);
        Log.e("error", " dziala");

        getJsonData();
        Log.e("error", this.titles_array[0]);
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
            Log.d("Details-->", comics_data.getString("results"));
//            ArrayList<ArrayList<String>> c_data = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < comics.length(); i ++){
                JSONObject temp = comics.getJSONObject(i);
//                ArrayList<String> temp_list = new ArrayList<String>();

                String temp_title = temp.getString("title");
                Log.d("Details-->", temp_title);
                String temp_description = temp.getString("description");
                JSONArray img = temp.getJSONArray("images");

//                JSONObject img_temp = img.getJSONObject(0);
//                String image = img_temp.getString("path");
                String image = checkImage(img);
                Log.d("Image", image);
                String[] temp_list = {temp_title, temp_description, image};
//                temp_list.add(temp_title);
//                temp_list.add(temp_description);
//                temp_list.add(image);
                this.titles_array[i] = temp_title;
                this.desc_array[i] = temp_description;
                this.img_array[i] = image;
//                this.c_data.add(temp_list);
//                String[] m = c_data.get(0);
//                Log.d("Results", m[0]);

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