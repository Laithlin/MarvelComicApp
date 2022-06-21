package com.example.marvelcomics;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ArrayList<String>> c_data = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("error", " dziala");

        getJsonData();

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
                ArrayList<String> temp_list = new ArrayList<String>();

                String temp_title = temp.getString("title");
                Log.d("Details-->", temp_title);
                String temp_description = temp.getString("description");
                JSONArray img = temp.getJSONArray("images");

//                JSONObject img_temp = img.getJSONObject(0);
//                String image = img_temp.getString("path");
                String image = checkImage(img);
                Log.d("Image", image);
                temp_list.add(temp_title);
                temp_list.add(temp_description);
                temp_list.add(image);

                this.c_data.add(temp_list);
                ArrayList<String> m = c_data.get(0);
                Log.d("Results", m.get(0));

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
}