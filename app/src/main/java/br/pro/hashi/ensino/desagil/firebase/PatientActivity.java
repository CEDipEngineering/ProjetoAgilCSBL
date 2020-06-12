package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PatientActivity extends Json {
    private SymptomGridAdapter symptomGridAdapter;

    private Spinner patientSpinner;
    private Button examesButton, editButton, addButton, alaButton, logButton;
    private ListView summaryView, symptomView, comorbityView;
    private ImageView symptomImage;
    private Paciente currPaciente;
    private Calendar currentTime;
    private HashMap<String , Paciente> converter = new HashMap<>();
    private ArrayList<Integer> idSintomas = new ArrayList<Integer>();
    private HashMap<String, String> tempSintomasData;
    private HashMap<Sintoma, Drawable> drawableHashMap;
    private Resources r;

    // Change this to show all symptoms around body, versus only the ones the patient has.
    private final static boolean showAllSymptoms = false;


    private void update(){
        List<Comorbidade> tempComorb = this.currPaciente.getComorbidades();
        List<Sintoma> tempSintoma = this.currPaciente.getSintomas();
        ArrayList<String> patientComorbs = new ArrayList<String>();
        ArrayList<String> patientSymptoms = new ArrayList<String>();
        ArrayList<String> patientSummary = new ArrayList<String>();
        tempSintomasData = this.currPaciente.getSintomasData();
        if (tempSintoma != null && tempComorb != null) {
            for (Sintoma s : tempSintoma){
                patientSymptoms.add(s.getNome());

            }
            for (Comorbidade c : tempComorb){
                patientComorbs.add(c.getNomeComorbidades());
            }
        }


        patientSummary.add("Nome: " + this.currPaciente.getName());
        patientSummary.add("Idade: " + this.currPaciente.getIdade());
        patientSummary.add("Dias com sintomas: " + this.currPaciente.getTempoSintomas());
        if (this.currPaciente.getLeitoId() >= 0) {
            patientSummary.add("Leito: " + this.currPaciente.getLeitoId());
        } else {
            patientSummary.add("Leito: ");
        }
        patientSummary.add("Risco: " + this.currPaciente.getRisco()*100 + "%");

        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientSummary);
        summaryView.setAdapter(adapter0);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientComorbs);
        comorbityView.setAdapter(adapter2);


        if (currPaciente != null) {
            Drawable[] layers = new Drawable[currPaciente.getSintomas().size() + 1];
            if (this.showAllSymptoms) {
                layers[0] = r.getDrawable(R.drawable.body_symptoms, null);
            } else {
                layers[0] = r.getDrawable(R.drawable.body, null);
            }
            for (int i = 1; i < currPaciente.getSintomas().size()+1; i++) {
                Sintoma symptom = currPaciente.getSintomas().get(i-1);
                try {
                    layers[i] = drawableHashMap.get(symptom);
                } catch (Exception e) {
                    e.printStackTrace();
                    layers[i] = r.getDrawable(R.drawable.body, null);
                    System.out.println("Recurso para sintoma: " + symptom.toString() + "nao encontrado! Adicione um recurso na pasta e no HashMap 'drawableHashMap'");
                }

            }
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            symptomImage.setImageDrawable(layerDrawable);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patientSpinner = findViewById(R.id.patientSpinner);
//        examesButton = findViewById(R.id.examesButton);
        editButton = findViewById(R.id.editButton);
        addButton = findViewById(R.id.addButton);
        alaButton = findViewById(R.id.alaButton);
        logButton = findViewById(R.id.logButton);
        summaryView = findViewById(R.id.summaryView);
        comorbityView = findViewById(R.id.comorbityView);
        symptomImage = findViewById(R.id.symptomImage);
        this.r = getResources();
        drawableHashMap = new HashMap<Sintoma, Drawable>();
        drawableHashMap.put(Sintoma.ANOSMIA, r.getDrawable(R.drawable.anosmia, null));
        drawableHashMap.put(Sintoma.AGEUSIA, r.getDrawable(R.drawable.ageusia, null));
        drawableHashMap.put(Sintoma.CATARRO, r.getDrawable(R.drawable.catarro, null));
        drawableHashMap.put(Sintoma.ESCARRO, r.getDrawable(R.drawable.escarro, null));
        drawableHashMap.put(Sintoma.GARGANTA, r.getDrawable(R.drawable.garganta, null));
        drawableHashMap.put(Sintoma.RINORREIA, r.getDrawable(R.drawable.rinorreia, null));
        drawableHashMap.put(Sintoma.OUVIDO, r.getDrawable(R.drawable.ouvido, null));
        drawableHashMap.put(Sintoma.SIBILOS, r.getDrawable(R.drawable.sibilos, null));
        drawableHashMap.put(Sintoma.MUSCULAR, r.getDrawable(R.drawable.muscular, null));
        drawableHashMap.put(Sintoma.ARTICULACAO, r.getDrawable(R.drawable.articulacao, null));
        drawableHashMap.put(Sintoma.FADIGA, r.getDrawable(R.drawable.fadiga, null));
        drawableHashMap.put(Sintoma.AR, r.getDrawable(R.drawable.ar, null));
        drawableHashMap.put(Sintoma.TORACICA, r.getDrawable(R.drawable.toracica, null));
        drawableHashMap.put(Sintoma.CABEÇA, r.getDrawable(R.drawable.cabeca, null));
        drawableHashMap.put(Sintoma.CONFUSAO, r.getDrawable(R.drawable.confusao, null));
        drawableHashMap.put(Sintoma.CONVULSAO, r.getDrawable(R.drawable.convulsao, null));
        drawableHashMap.put(Sintoma.ABDOMEM, r.getDrawable(R.drawable.abdomem, null));
        drawableHashMap.put(Sintoma.VOMITO, r.getDrawable(R.drawable.vomito, null));
        drawableHashMap.put(Sintoma.DIARREIA, r.getDrawable(R.drawable.diarreia, null));
        drawableHashMap.put(Sintoma.CONJUTIVITE, r.getDrawable(R.drawable.conjuntivite, null));
        drawableHashMap.put(Sintoma.CUTANEA, r.getDrawable(R.drawable.cutanea, null));
        drawableHashMap.put(Sintoma.ULCERA, r.getDrawable(R.drawable.ulcera, null));
        drawableHashMap.put(Sintoma.LINFA, r.getDrawable(R.drawable.linfa, null));
        drawableHashMap.put(Sintoma.SANGRAMENTO, r.getDrawable(R.drawable.sangramento, null));
        drawableHashMap.put(Sintoma.SECA, r.getDrawable(R.drawable.seca, null));


        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        int patientId = myIntent.getIntExtra("idPaciente",-1);
        int leitoId = myIntent.getIntExtra("leito",-1);

        int index = 0;


        Paciente[] Pacientes;

        String json_f = loadData();
        try {
            JSONObject root = new JSONObject(json_f);
            JSONObject data = root.getJSONObject("database");
            JSONArray JSONpatients = data.getJSONArray("patients");

            Pacientes = new Paciente[JSONpatients.length()];

            if (patientId != -1) {
                index = findIndex(JSONpatients,"id",patientId);
            } else if (leitoId != -1) {
                index = findIndex(JSONpatients,"leito",leitoId);
            }


            for (int i = 0; i < JSONpatients.length(); i++) {
                JSONObject paciente = JSONpatients.getJSONObject(i);
                Pacientes[i] = new Paciente(paciente);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Pacientes = new Paciente[1];
            Pacientes[0] = new Paciente("Rafael", 1, 21, 7, new LinkedList<Comorbidade>(), new LinkedList<Sintoma>(), 0.67, tempSintomasData);
        }




        ArrayList arraySpinner = new ArrayList();
        for (int i = 0; i<Pacientes.length; i++) {
            converter.put(Pacientes[i].getName(), Pacientes[i]);
            arraySpinner.add(Pacientes[i].getName());
        }






        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(adapter);

        this.currPaciente = Pacientes[index];
        patientSpinner.setSelection(adapter.getPosition(currPaciente.getName()));



        update();



        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    currPaciente = converter.get(patientSpinner.getSelectedItem().toString());
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        editButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, PatientEditActivity.class);
            // Tem que passar o paciente atual também;
            intent.putExtra("idPaciente", currPaciente.getId());
            startActivity(intent);
        });

        addButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, PatientEditActivity.class);
            // Tem que passar o paciente atual também;
            //intent.putExtra("idPaciente", -1);
            startActivity(intent);
        });

        List<Comorbidade> tempComorb = this.currPaciente.getComorbidades();
        JSONArray jsonSintomas = this.currPaciente.getIdSintomas();
        ArrayList<String> patientSymptoms = new ArrayList<String>();

        if (jsonSintomas != null) {
            for (int i=0;i<jsonSintomas.length();i++){
                try {
                    idSintomas.add(jsonSintomas.getInt(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        alaButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, AlaActivity.class);
            // Tem que passar o paciente atual também;
            intent.putExtra("idAla", currPaciente.getAlaId());
            startActivity(intent);
        });

        logButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, TimeStampActivity.class);
            // Tem que passar o paciente atual também;
            intent.putExtra("idPaciente", currPaciente.getId());
            startActivity(intent);
        });

    }


}
