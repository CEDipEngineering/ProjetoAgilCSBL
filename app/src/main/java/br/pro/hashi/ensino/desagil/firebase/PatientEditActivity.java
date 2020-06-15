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
import android.widget.Toast;

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
    private EditText tempoSintomasEdit, patientNameEdit, patientIdadeEdit, leitoEdit, riscoEdit, patientNote;
    private ListView listaSintomasView, listaComorbidadesView;
    private List<String> listaSintomasEnum = Arrays.asList(Sintoma.getNameArray());
    private List<String> listaComorbidadesEnum = Arrays.asList(Comorbidade.getNameArray());
    private Button finalizarButton, cancelarButton, altarButton, deletarButton;
    private List<Sintoma> sintomasSelecionados;
    private List<Comorbidade> comorbidadesSelecionadas;
    private ArrayAdapter<String> adapterSintomas;
    private ArrayAdapter<String> adapterComorbidades;
    private HashMap<String, String> tempSintomasData = new HashMap<String, String>();
    private HashMap<String, String> tempNotasMedico = new HashMap<String, String>();

    private String patientName;
    private int patientIdade, tempoSint;

    private int patientId;
    private int leitoId;
    private boolean add;

    private Paciente patient_edited = null;


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
        patientNote = findViewById(R.id.edit_note);
        leitoEdit = findViewById(R.id.edit_leito);
        tempoSintomasEdit = findViewById(R.id.edit_dias);
        listaSintomasView = findViewById(R.id.list_sintomas);
        listaComorbidadesView = findViewById(R.id.list_comorbidades);
        finalizarButton = findViewById((R.id.finalizar));
        cancelarButton = findViewById((R.id.cancelar));
        altarButton = findViewById((R.id.alta));
        deletarButton = findViewById((R.id.deletar));
        adapterSintomas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaSintomasEnum);
        adapterComorbidades = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listaComorbidadesEnum);
        listaSintomasView.setAdapter(adapterSintomas);
        listaComorbidadesView.setAdapter(adapterComorbidades);


        LinkedList<Sintoma> Sintomas1 = new LinkedList<Sintoma>();
        LinkedList<Comorbidade> Comorbs1 = new LinkedList<Comorbidade>();
        add = false;


        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        patientId = myIntent.getIntExtra("idPaciente", -1);
        leitoId = myIntent.getIntExtra("idLeito", -1);

        int alaId = -1;
        if (leitoId != -1) {
            leitoEdit.setText(Integer.toString(leitoId));
        }

        // If there is one, put it in the textView


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
                tempNotasMedico = patient_edited.getNotasMedico();
                patientId = patient_edited.getId();
                if (patient_edited.getLeitoId() >= 0) {
                    leitoEdit.setText(Integer.toString(patient_edited.getLeitoId()));
                }
                if (patient_edited.getComorbidades() != null) {
                    for (int c = 0; c < listaComorbidadesView.getCount(); c++) {
                        Comorbidade comorbidade = Comorbidade.getByName(listaComorbidadesView.getItemAtPosition(c).toString());
                        if (patient_edited.getComorbidades().contains(comorbidade)) {
                            listaComorbidadesView.setItemChecked(i, true);
                        }
                    }
                }
                if (patient_edited.getSintomas() != null) {
                    for (int b = 0; b < listaSintomasView.getCount(); b++) {
                        Sintoma sintoma = Sintoma.getByName(listaSintomasView.getItemAtPosition(b).toString());
                        if (patient_edited.getSintomas().contains(sintoma)) {
                            listaSintomasView.setItemChecked(b, true);
                        }
                    }
                }

                patientNameEdit.setHint(patient_edited.getName());
                patientIdadeEdit.setHint(Integer.toString(patient_edited.getIdade()));
                tempoSintomasEdit.setHint(Integer.toString(patient_edited.getTempoSintomas()));


            } else {
                add = true;
                patientId = getNext(JSONpatients, "id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        leitoEdit.setEnabled(false);
        if (add) {
            deletarButton.setVisibility(View.GONE);
            altarButton.setVisibility(View.GONE);
        }


        finalizarButton.setOnClickListener((view) -> {
            boolean erro = false;
            if (!(patientNameEdit.getText().toString().equals("")) ) {
                patientName = patientNameEdit.getText().toString();
            } else if (patientNameEdit.getHint() != null) {
                patientName = patientNameEdit.getHint().toString();
            } else {
                erro = true;
            }
            if (!(patientIdadeEdit.getText().toString().equals("")) ) {
                patientIdade = Integer.parseInt(patientIdadeEdit.getText().toString());
            } else if (patientIdadeEdit.getHint()  != null) {
                patientIdade = Integer.parseInt(patientIdadeEdit.getHint().toString());
            } else {
                erro = true;
            }
            if (!(tempoSintomasEdit.getText().toString().equals("")) ) {
                tempoSint = Integer.parseInt(tempoSintomasEdit.getText().toString());
            } else if (tempoSintomasEdit.getHint()  != null) {
                tempoSint = Integer.parseInt(tempoSintomasEdit.getHint().toString());
            } else {
                erro = true;
            }


            if (!erro) {

                sintomasSelecionados = new ArrayList<Sintoma>();
                comorbidadesSelecionadas = new ArrayList<Comorbidade>();
                SparseBooleanArray sintomasChecked = listaSintomasView.getCheckedItemPositions();
                SparseBooleanArray comorbidadesChecked = listaComorbidadesView.getCheckedItemPositions();
                for (int i = 0; i < sintomasChecked.size(); i++) {
                    int key = sintomasChecked.keyAt(i);
                    boolean value = sintomasChecked.get(key);
                    if (value) {
                        sintomasSelecionados.add(Sintoma.getByName(listaSintomasView.getItemAtPosition(key).toString()));
                    }
                }
                for (int i = 0; i < comorbidadesChecked.size(); i++) {
                    int key = comorbidadesChecked.keyAt(i);
                    boolean value = comorbidadesChecked.get(key);
                    if (value) {
                        comorbidadesSelecionadas.add(Comorbidade.getByName(listaComorbidadesView.getItemAtPosition(key).toString()));
                    }
                    String currentTime = Calendar.getInstance().getTime().toString();
                    Set<String> keys = tempSintomasData.keySet();
                    String currentTimeEdited = currentTime.replace("GMT-03:00", "");
                    for(Sintoma s: sintomasSelecionados){
                        String sintomaNome = s.getNome();
                        if(!keys.contains(sintomaNome)){
                            tempSintomasData.put(sintomaNome + " "+ "modificado em : ", currentTimeEdited);
                        }

                    }
                    for(Sintoma s: patient_edited.getSintomas()){
                        String sintomaNome = s.getNome();
                        if(!sintomasSelecionados.contains(s)){
                            tempSintomasData.remove(sintomaNome);
                            tempSintomasData.put(sintomaNome + " " +  "modificado em : ", currentTimeEdited);
                        }
                    }
                    tempNotasMedico.put(patientNote.getText().toString(), currentTime);
                }
                System.out.println(tempSintomasData);


                //// INTEGRACAO JAVA -> PYTHON/////
                String URL = "https://pythontojava3.herokuapp.com/api/post_some_data";
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = jsonObject.put("text", "10");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        double risco = 0;
                        try {
                            risco = response.getDouble("value") / 100;
                            String currentTime = Calendar.getInstance().getTime().toString();
                            Paciente NovoPaciente = new Paciente(patientName, patientId, patientIdade, tempoSint, comorbidadesSelecionadas, sintomasSelecionados, risco, tempSintomasData, tempNotasMedico);
                            String json = loadData();
                            try {
                                JSONObject root = new JSONObject(json);
                                JSONObject data = root.getJSONObject("database");
                                JSONArray JSONpatients = data.getJSONArray("patients");
                                JSONObject JSONpatient = new JSONObject();
                                if (!add) {
                                    JSONpatient = JSONpatients.getJSONObject(patientId);
                                }

                                if (!(leitoEdit.getText().toString().equals(""))) {
                                    JSONpatient.put("leito", Integer.parseInt(leitoEdit.getText().toString()));
                                } else {
                                    JSONpatient.put("leito", -1);
                                }

                                JSONpatient.put("risco", risco);
                                JSONpatient.put("nome", NovoPaciente.getName());
                                JSONpatient.put("id", NovoPaciente.getId());
                                JSONpatient.put("idade", NovoPaciente.getIdade());
                                JSONpatient.put("tempoSintomas", NovoPaciente.getTempoSintomas());
                                JSONpatient.put("sintomasData", new JSONObject(NovoPaciente.getSintomasData()));
                                JSONpatient.put("notasMedico", new JSONObject(NovoPaciente.getNotasMedico()));
                                JSONpatient.put("sintomas", (JSONArray) NovoPaciente.getIdSintomas());
                                JSONpatient.put("comorbidades", NovoPaciente.getIdComorbidades());
                                if (add) {
                                    JSONpatients.put(JSONpatients.length(), JSONpatient);
                                    data.put("patients", JSONpatients);
                                    root.put("database", data);
                                }
                                saveData(root.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //intent
                            Toast toast = Toast.makeText(getApplicationContext(), "Paciente modificado com sucesso", Toast.LENGTH_SHORT);
                            if (add) {
                                toast = Toast.makeText(getApplicationContext(), "Paciente adicionado com sucesso", Toast.LENGTH_SHORT);
                            }
                            Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                            // Tem que passar o paciente atual também;
                            intent.putExtra("idPaciente", patientId);
                            startActivity(intent);

                            toast.show();

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

            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Não foi possivel modificar paciente", Toast.LENGTH_SHORT);
                if (add) {
                    toast = Toast.makeText(getApplicationContext(), "Não foi possivel adicionar paciente", Toast.LENGTH_SHORT);
                }
                toast.show();

            }
        });

        cancelarButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientEditActivity.this, AlaActivity.class);
            // Tem que passar o paciente atual também;
            if (leitoId != -1) {
                int idAla = (leitoId/1000);
                intent.putExtra("idAla", idAla-1);
            }  else if (!add) {
                intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                intent.putExtra("idPaciente", patientId);
            }
            startActivity(intent);
        });


        deletarButton.setOnClickListener((view) -> {
            String json_f = loadData();
            try {
                JSONObject root = new JSONObject(json_f);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONpatients = data.getJSONArray("patients");
                if (JSONpatients.length() != 1){
                    int i = findIndex(JSONpatients,"id",patientId);

                    JSONpatients.remove(i);
                    data.put("patients",JSONpatients);
                    root.put("database", data);
                    saveData(root.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Paciente removido com sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                    if (patient_edited.getLeitoId() >= 0) {
                        int idAla = patient_edited.getLeitoId()/1000;
                        intent = new Intent(PatientEditActivity.this, AlaActivity.class);
                        intent.putExtra("idAla", idAla);
                    } else {
                        patientId = getPrevious(JSONpatients,"id",patientId);
                        intent.putExtra("idPaciente", patientId);
                    }
                    startActivity(intent);


                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível remover o paciente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        altarButton.setOnClickListener((view) -> {
            String json_f = loadData();
            try {
                JSONObject root = new JSONObject(json_f);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONpatients = data.getJSONArray("patients");
                int i = findIndex(JSONpatients,"id",patientId);
                JSONObject JSONpatient = JSONpatients.getJSONObject(i);

                JSONpatient.put("leito",-1);

                data.put("patients",JSONpatients);
                root.put("database", data);
                saveData(root.toString());

                Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                intent.putExtra("idPaciente", patientId);
                startActivity(intent);

                Toast toast = Toast.makeText(getApplicationContext(), "Paciente removido do leito com sucesso", Toast.LENGTH_SHORT);
                toast.show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        deletarButton.setOnClickListener((view) -> {
            String json_f = loadData();
            try {
                JSONObject root = new JSONObject(json_f);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONpatients = data.getJSONArray("patients");
                if (JSONpatients.length() != 1){
                    int i = findIndex(JSONpatients,"id",patientId);

                    JSONpatients.remove(i);
                    data.put("patients",JSONpatients);
                    root.put("database", data);
                    saveData(root.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Paciente removido com sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                    if (patient_edited.getLeitoId() >= 0) {
                        int idAla = patient_edited.getLeitoId()/1000;
                        intent = new Intent(PatientEditActivity.this, AlaActivity.class);
                        intent.putExtra("idAla", idAla);
                    } else {
                        patientId = getPrevious(JSONpatients,"id",patientId);
                        intent.putExtra("idPaciente", patientId);
                    }
                    startActivity(intent);


                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível remover o paciente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

}