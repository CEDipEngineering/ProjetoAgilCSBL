package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PatientEditActivity extends Json {
    private TextView patientNameView, patientIdadeView, tempoSintomasView, leitoView, riscoView, comorbidadesView, examesView, sintomasView;
    private EditText tempoSintomasEdit, patientNameEdit, patientIdadeEdit, leitoEdit, riscoEdit;
    private ListView listaSintomasView, listaComorbidadesView;
    private String patientName;
    private List <String>  listaSintomasEnum = Arrays.asList(Sintoma.getNameArray());
    private List<String> listaComorbidadesEnum = Arrays.asList(Comorbidade.getNameArray());
    private Button finalizarButton;
    private List<Sintoma>  sintomasSelecionados;
    private List<Comorbidade> comorbidadesSelecionadas;
    private ArrayAdapter<String> adapterSintomas;
    private ArrayAdapter<String> adapterComorbidades;
    private HashMap<String, String> tempSintomasData;

    private int idpacient;
    private boolean add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> listaComorbidades = new ArrayList<String>();
        ArrayList<String> listaSintomas = new ArrayList<String>();

        /*for(Comorbidade e :listaComorbidadesEnum){
            listaComorbidades.add(e.getNomeComorbidades());
        }
        for(Sintoma s :listaSintomasEnum){
            listaSintomas.add(s.getNome());
        }*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpatient);

        patientNameView = findViewById(R.id.name);
        patientIdadeView = findViewById(R.id.idade);
        patientNameEdit = findViewById(R.id.edit_name);
        patientIdadeEdit = findViewById(R.id.edit_idade);
        leitoEdit = findViewById(R.id.edit_leito);
        tempoSintomasEdit = findViewById(R.id.edit_dias);
        listaSintomasView = findViewById(R.id.list_sintomas);
        listaComorbidadesView = findViewById(R.id.list_comorbidades);
        finalizarButton = findViewById((R.id.finalizar));
        patientName = patientNameEdit.getText().toString();
        adapterSintomas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaSintomasEnum);
        adapterComorbidades = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaComorbidadesEnum);
        listaSintomasView.setAdapter(adapterSintomas);
        listaComorbidadesView.setAdapter(adapterComorbidades);




        LinkedList<Sintoma> Sintomas1 = new LinkedList<Sintoma>();
        LinkedList<Comorbidade> Comorbs1 = new LinkedList<Comorbidade>();
        add = false;


        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        int patientid = myIntent.getIntExtra("patientid",-1);
        int leito = myIntent.getIntExtra("leito",-1);

        if (leito != -1) {
            leitoEdit.setText(Integer.toString(leito));
        }

        // If there is one, put it in the textView


        String json = loadData();
        Paciente patient = null;
        try {
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("database");
            JSONArray patientes = data.getJSONArray("patients");
            if (patientid != -1) {
                int i = 0;
                while (patientes.getJSONObject(i).getInt("id") != patientid) { i++;}


                if (i < patientes.length()) {
                    JSONObject patiente = patientes.getJSONObject(i);

                    patient = new Paciente(patiente);
                    tempSintomasData = patient.getSintomasData();
                    idpacient = patient.getId();
                    if (patient.getLeitoId() >= 0) {
                        leitoEdit.setText(Integer.toString(patient.getLeitoId()));
                    }
                    patientNameEdit.setText(patient.getName());
                    patientIdadeEdit.setText(Integer.toString(patient.getIdade()));
                    tempoSintomasEdit.setText(Integer.toString(patient.getTempoSintomas()));
                } else {
                    add = true;
                    patientIdadeEdit.setText(Integer.toString(0));
                    tempoSintomasEdit.setText(Integer.toString(0));
                    idpacient = patientes.length();
                }
                patientNameEdit.setText(patient.getName());
                patientIdadeEdit.setText(Integer.toString(patient.getIdade()));
                tempoSintomasEdit.setText(Integer.toString(patient.getTempoSintomas()));

            } else {
                add = true;
                patientIdadeEdit.setText(Integer.toString(0));
                tempoSintomasEdit.setText(Integer.toString(0));
                idpacient = getNext(patientes,"id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        leitoEdit.setEnabled(false);


        int c = 0;


        for (int i = 0; i < listaComorbidadesView.getCount(); i++) {
            Comorbidade comorbidade = Comorbidade.getByName( listaComorbidadesView.getItemAtPosition(i).toString());
            if (patient.getComorbidades() != null && patient.getSintomas() != null) {
                if (patient.getComorbidades().contains(comorbidade)){
                    listaComorbidadesView.setItemChecked(i, true);
                }
                for (int b = 0; b < listaSintomasView.getCount(); b++) {
                    Sintoma sintoma = Sintoma.getByName(listaSintomasView.getItemAtPosition(b).toString());


                    if (patient.getSintomas().contains(sintoma)) {
                        listaSintomasView.setItemChecked(b, true);
                    }

                }
            }
        }


        Paciente finalPatient = patient;
        finalizarButton.setOnClickListener((view) -> {
            switch(view.getId()){
                case R.id.finalizar:
                    sintomasSelecionados = new ArrayList<Sintoma>();
                    comorbidadesSelecionadas = new ArrayList<Comorbidade>();
                    SparseBooleanArray sintomasChecked = listaSintomasView.getCheckedItemPositions();
                    SparseBooleanArray comorbidadesChecked = listaComorbidadesView.getCheckedItemPositions();
                    tempSintomasData = finalPatient.getSintomasData();
                    for(int i = 0;i<sintomasChecked.size(); i++){
                        int key =  sintomasChecked.keyAt(i);
                        boolean value = sintomasChecked.get(key);
                        if(value){
                            sintomasSelecionados.add(Sintoma.getByName(listaSintomasView.getItemAtPosition(key).toString()));
                        }
                    }
                    for(int i = 0;i<comorbidadesChecked.size(); i++){
                        int key =  comorbidadesChecked.keyAt(i);
                        boolean value = comorbidadesChecked.get(key);
                        if(value){
                            comorbidadesSelecionadas.add(Comorbidade.getByName( listaComorbidadesView.getItemAtPosition(key).toString()));
                        }
                    }
                    String currentTime = Calendar.getInstance().getTime().toString();
                    Set<String> keys = tempSintomasData.keySet();
                    for(Sintoma s: sintomasSelecionados){
                        if(!keys.contains(s.toString())){
                            tempSintomasData.put(s.toString(),currentTime);
                        }

                    }
                    System.out.println(tempSintomasData);


                    //// INTEGRACAO JAVA -> PYTHON/////
                    String URL  = "https://pythontojava3.herokuapp.com/api/post_some_data";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject = jsonObject.put("text","10");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Rest Response", response.toString());
                            double risco = 0;
                            try {
                                risco = response.getDouble("value")/100;
                                patientName = patientNameEdit.getText().toString();
                                int idade = Integer.parseInt(patientIdadeEdit.getText().toString());
                                int tempoSint = Integer.parseInt(tempoSintomasEdit.getText().toString());
                                String currentTime = Calendar.getInstance().getTime().toString();
                                Paciente Paciente1 = new Paciente(patientName, idpacient, idade, tempoSint, comorbidadesSelecionadas, sintomasSelecionados, risco, tempSintomasData);
                                String json = loadData();
                                try {

                                    JSONObject root = new JSONObject(json);
                                    JSONObject data = root.getJSONObject("database");
                                    JSONArray patientes = data.getJSONArray("patients");
                                    JSONObject patiente = new JSONObject();
                                    if (!add) {
                                        patiente = patientes.getJSONObject(idpacient);
                                    }

                                    if (!(leitoEdit.getText().toString().equals(""))) {
                                        patiente.put("leito",Integer.parseInt(leitoEdit.getText().toString()));
                                    } else {
                                        patiente.put("leito",-1);
                                    }

                                    patiente.put("risco", risco);
                                    patiente.put("nome", Paciente1.getName());
                                    patiente.put("id", Paciente1.getId());
                                    patiente.put("idade", Paciente1.getIdade());
                                    patiente.put("tempoSintomas",Paciente1.getTempoSintomas());
                                    patiente.put("sintomasData", new JSONObject(Paciente1.getSintomasData()));
                                    patiente.put("sintomas",(JSONArray)Paciente1.getIdSintomas());
                                    patiente.put("comorbidades",Paciente1.getIdComorbidades());
                                    if (add) {
                                        patientes.put(patientes.length(),patiente);
                                        data.put("patients",patientes);
                                        root.put("database", data);
                                    }
                                    saveData(root.toString());



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //intent

                                Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                                // Tem que passar o paciente atual também;
                                intent.putExtra("patientid", idpacient);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Rest Response", error.toString());

                        }
                    });
                    requestQueue.add(objectRequest);
            }

        });
    }

}