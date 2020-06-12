package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import java.util.HashMap;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeStampActivity extends Json {
    private ListView summaryView;
    private HashMap<String, String> tempSintomasData;
    private TextView patientNameView;
    private int patientId;
    private int leitoId;
    private Paciente patient_edited = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_stamp);
        patientNameView = findViewById(R.id.name);


        Intent myIntent = getIntent();
        patientId = myIntent.getIntExtra("idPaciente", -1);
        String json = loadData();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("database");
            JSONArray JSONpatients = data.getJSONArray("patients");
            if (patientId != -1) {
                int i = findIndex(JSONpatients, "id", patientId);

                JSONObject JSONpatient = JSONpatients.getJSONObject(i);

                patient_edited = new Paciente(JSONpatient);
                tempSintomasData = patient_edited.getSintomasData();
                patientId = patient_edited.getId();
            }
        }
        catch (JSONException e) {
        e.printStackTrace();
    }
        System.out.println(tempSintomasData);
    }
}