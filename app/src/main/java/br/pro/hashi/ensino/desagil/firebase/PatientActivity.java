package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.graphics.Color;
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
    private Button examesButton, editButton, addButton, alaButton;
    private ListView summaryView, symptomView, comorbityView;
    private ImageView Sibilos, Ageusia;
    private Paciente currPatient;
    private GridView gridView;
    private Calendar currentTime;
    private HashMap<String , Paciente> converter = new HashMap<>();
    private ArrayList<Integer> idSintomas = new ArrayList<Integer>();
    private HashMap<String, String> tempSintomasData;
    private ArrayList<Sintoma> symptomGridView = new ArrayList<>();

    public void grayOut(ImageView view) {
        view.setColorFilter(Color.argb(150,200,200,200));
    }



    private void update(){
        List<Comorbidade> tempComorb = this.currPatient.getComorbidades();
        List<Sintoma> tempSintoma = this.currPatient.getSintomas();
        ArrayList<String> patientComorbs = new ArrayList<String>();
        ArrayList<String> patientSymptoms = new ArrayList<String>();
        ArrayList<String> patientSummary = new ArrayList<String>();
        tempSintomasData = this.currPatient.getSintomasData();
        if (tempSintoma != null && tempComorb != null) {
            for (Sintoma s : tempSintoma){
                patientSymptoms.add(s.getNome());

            }
            for (Comorbidade c : tempComorb){
                patientComorbs.add(c.getNomeComorbidades());
            }
        }


        patientSummary.add("Nome: " + this.currPatient.getName());
        patientSummary.add("Idade: " + this.currPatient.getIdade());
        patientSummary.add("Dias com sintomas: " + this.currPatient.getTempoSintomas());
        if (this.currPatient.getLeitoId() >= 0) {
            patientSummary.add("Leito: " + this.currPatient.getLeitoId());
        } else {
            patientSummary.add("Leito: ");
        }
        patientSummary.add("Risco: " + this.currPatient.getRisco()*100 + "%");

        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientSummary);
        summaryView.setAdapter(adapter0);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientSymptoms);
        symptomView.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientComorbs);
        comorbityView.setAdapter(adapter2);


        symptomGridView = new ArrayList<Sintoma>();

        if (!symptomGridAdapter.isShowallsymptoms()) {
            for (Sintoma s : this.currPatient.getSintomas()) {
                symptomGridView.add(s);
            }
        } else {
            for (Sintoma s : Sintoma.class.getEnumConstants()) {
                symptomGridView.add(s);
            }
        }

        symptomGridAdapter = new SymptomGridAdapter(PatientActivity.this, symptomGridView, this.currPatient);
        gridView.setAdapter(symptomGridAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patientSpinner = findViewById(R.id.patientSpinner);
        examesButton = findViewById(R.id.examesButton);
        editButton = findViewById(R.id.editButton);
        addButton = findViewById(R.id.addButton);
        alaButton = findViewById(R.id.alaButton);
        summaryView = findViewById(R.id.summaryView);
        symptomView = findViewById(R.id.symptomView);
        comorbityView = findViewById(R.id.comorbityView);
        gridView = findViewById(R.id.symptomGrid);


        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        int patientid = myIntent.getIntExtra("patientid",-1);
        int leitoid = myIntent.getIntExtra("leito",-1);

        int index = 0;



        LinkedList<Comorbidade> Comorbs1, Comorbs2, Comorbs3;
        Comorbs1 = new LinkedList<Comorbidade>();



        LinkedList<Sintoma> Sintomas1, Sintomas2, Sintomas3;
        Sintomas1 = new LinkedList<Sintoma>();



        Paciente[] Patients;

        String json_f = loadData();
        try {
            JSONObject root = new JSONObject(json_f);
            JSONObject data = root.getJSONObject("database");
            JSONArray JSONpacientes = data.getJSONArray("patients");

            Patients = new Paciente[JSONpacientes.length()];

            if (patientid != -1) {
                index = findIndex(JSONpacientes,"id",patientid);
            } else if (leitoid != -1) {
                index = findIndex(JSONpacientes,"leito",leitoid);
            }


            for (int i = 0; i < JSONpacientes.length(); i++) {
                JSONObject paciente = JSONpacientes.getJSONObject(i);
                Patients[i] = new Paciente(paciente);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Patients = new Paciente[1];
            Patients[0] = new Paciente("Rafael", 1, 21, 7, Comorbs1, Sintomas1, 0.67, tempSintomasData);
        }



        ArrayList arraySpinner = new ArrayList();
        for (int i = 0; i<Patients.length; i++) {
            converter.put(Patients[i].getName(), Patients[i]);
            arraySpinner.add(Patients[i].getName());
        }






        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(adapter);

        this.currPatient = Patients[index];
        patientSpinner.setSelection(adapter.getPosition(currPatient.getName()));



        update();



        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    currPatient = converter.get(patientSpinner.getSelectedItem().toString());
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
            intent.putExtra("patientid", currPatient.getId());
            startActivity(intent);
        });

        addButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, PatientEditActivity.class);
            // Tem que passar o paciente atual também;
            //intent.putExtra("patientid", -1);
            startActivity(intent);
        });

        List<Comorbidade> tempComorb = this.currPatient.getComorbidades();
        JSONArray jsonSintomas = this.currPatient.getIdSintomas();
        List<ImageView> imagens = Arrays.asList(Sibilos, Ageusia);
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
//        for(ImageView i: imagens){
//            if(idSintomas.contains(Integer.parseInt((String) i.getTag()))){
//                System.out.println("GREY");
//                grayOut(i);
//            }
//        }

        alaButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, AlaActivity.class);
            // Tem que passar o paciente atual também;
            intent.putExtra("alaid", currPatient.getAlaId());
            startActivity(intent);
        });

    }


}
