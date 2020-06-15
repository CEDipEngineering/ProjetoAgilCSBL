package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeStampActivity extends Json {
    private ListView summaryView;
    private ListView summaryViewNotas;
    private HashMap<String, String> tempSintomasData;
    private HashMap<String, String> tempNotasMedico;
    private TextView patientNameView;
    private ArrayAdapter<String> adapterData;
    private ArrayAdapter<String> adapterDataNotas;
    private int patientId;
    private int leitoId;
    private Paciente patient_edited = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_stamp);
        patientNameView = findViewById(R.id.name);
        summaryView = findViewById(R.id.list_data);
        summaryViewNotas = findViewById(R.id.list_anotacoes);
        ArrayList<String> adapterDataString = new ArrayList<String>();
        ArrayList<String> adapterDataStringNotas = new ArrayList<String>();

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
                for (Map.Entry me : tempSintomasData.entrySet()) {
                    if(!me.getValue().equals("0")) {
                        adapterDataString.add(me.getKey() + "" + me.getValue());
                    }
                }
                patientId = patient_edited.getId();
                adapterData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  adapterDataString);
                summaryView.setAdapter(adapterData);


                summaryViewNotas = findViewById(R.id.list_anotacoes);

                tempNotasMedico = patient_edited.getNotasMedico();
                for (Map.Entry me : tempNotasMedico.entrySet()) {
                    if(!me.getValue().equals("0")) {
                        adapterDataStringNotas.add(me.getKey() + ": adicionado em  " + me.getValue());
                    }
                }
                patientId = patient_edited.getId();
                adapterDataNotas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  adapterDataStringNotas);
                summaryViewNotas.setAdapter(adapterDataNotas);
            }
        }
        catch (JSONException e) {
        e.printStackTrace();
    }
    }
}