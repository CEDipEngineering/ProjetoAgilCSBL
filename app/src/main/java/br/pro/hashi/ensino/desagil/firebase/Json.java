package br.pro.hashi.ensino.desagil.firebase;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

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
}
