package br.pro.hashi.ensino.desagil.firebase;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Json extends AppCompatActivity {


    private String loadJSON() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void saveData(String s) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", s);
        editor.apply();
    }

    public String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", 0);
        String json = sharedPreferences.getString("data", null);

        if (json == null) {
            json = loadJSON();
            saveData(json);
        }

        return json;
    }

    protected JSONArray sortData(JSONArray js,String key) {
        JSONArray sortedArray = new JSONArray();
        try {
            while (js.length() != 0) {
                int gv = 9999999;
                int sj = 0;
                for (int o = 0; o < js.length(); o++) {
                    JSONObject jo = js.getJSONObject(o);
                    if (jo.getInt(key) <= gv) {
                        gv = jo.getInt(key);
                        sj = o;
                    }
                }
                sortedArray.put(sortedArray.length(),js.getJSONObject(sj));
                js.remove(sj);
            }
            return sortedArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return js;
        }
    }

    protected int getMax(JSONArray js,String key) {
        try {
            if (js.length() != 0) {
                int gv = -1;
                for (int o = 0; o < js.length(); o++) {
                    JSONObject jo = js.getJSONObject(o);
                    if (jo.getInt(key) <= gv) {
                        gv = jo.getInt(key);
                    }
                }
                return gv;
            } else {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected int getNext(JSONArray js,String key) {
        int i = 0;
        int j = 0;
        int[] ids = new int[js.length()];
        try {
            while (j < js.length()) {
                ids[j] = js.getJSONObject(j).getInt(key);
                j++;
            }
            Arrays.sort(ids);
            while ( (i < ids.length) && i == ids[i]) { i++; }
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHH");
            System.out.println(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("O Id do getNext é " + i);
        return i;
    }

    protected int getPrevious(JSONArray js,String key,int id) {
        id--;
        int pos = findIndex(js,key,id);
        while (pos < 0 && id > 0) {
            id -= 1;
            pos = findIndex(js,key,id);
        }
        while (pos < 0) {
            id +=1;
            pos = findIndex(js,key,id);
        }
        return id;
    }

    protected int findIndex(JSONArray js,String key,int id) {
        try {
            int i = 0;
            while (i < js.length() && js.getJSONObject(i).getInt(key) != id) { i++;}
            if (i < js.length()) {
                return i;
            } else {
                return -1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    protected int findPreviousIndex(JSONArray js,String key,int id) {
        int i = getPrevious( js, key, id);
        return findIndex(js, key, i);
    }
}
