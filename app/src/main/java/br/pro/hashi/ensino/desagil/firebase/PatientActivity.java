package br.pro.hashi.ensino.desagil.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PatientActivity extends AppCompatActivity{
    private Spinner patientSpinner;
    private Button examesButton, editButton;
    private ListView summaryView, symptomView, comorbityView;
    private Paciente currPatient;
    private HashMap<String , Paciente> converter = new HashMap<>();

    private void update(){
        List<Comorbidade> tempComorb = this.currPatient.getComorbidades();
        List<Sintoma> tempSintoma = this.currPatient.getSintomas();

        ArrayList<String> patientComorbs = new ArrayList<String>();
        ArrayList<String> patientSymptoms = new ArrayList<String>();
        ArrayList<String> patientSummary = new ArrayList<String>();

        for (Sintoma s : tempSintoma){
            patientSymptoms.add(s.getNome());
        }
        for (Comorbidade c : tempComorb){
            patientComorbs.add(c.getNomeComorbidades());
        }

        patientSummary.add("Nome: " + this.currPatient.getName());
        patientSummary.add("Idade: " + this.currPatient.getIdade());
        patientSummary.add("Dias com sintomas: " + this.currPatient.getTempoSintomas());
        patientSummary.add("Leito: " + this.currPatient.getLeito());
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        patientSpinner = findViewById(R.id.patientSpinner);

        examesButton = findViewById(R.id.examesButton);
        editButton = findViewById(R.id.editButton);

        summaryView = findViewById(R.id.summaryView);
        symptomView = findViewById(R.id.symptomView);
        comorbityView = findViewById(R.id.comorbityView);

        editButton.setOnClickListener((view) -> {
            Intent intent = new Intent(PatientActivity.this, PatientEditActivity.class);
            // Tem que passar o paciente atual também;
            startActivity(intent);
        });


        LinkedList<Comorbidade> Comorbs1, Comorbs2, Comorbs3;
        Comorbs1 = new LinkedList<Comorbidade>();
        Comorbs2 = new LinkedList<Comorbidade>();
        Comorbs3 = new LinkedList<Comorbidade>();
        Comorbs1.add(Comorbidade.CARDIO);
        Comorbs1.add(Comorbidade.DIABETES);
        Comorbs1.add(Comorbidade.HIV);
        Comorbs2.add(Comorbidade.IMUNODEF);
        Comorbs2.add(Comorbidade.NEOPLASIA);
        Comorbs3.add(Comorbidade.PULMONAR);

        LinkedList<Sintoma> Sintomas1, Sintomas2, Sintomas3;
        Sintomas1 = new LinkedList<Sintoma>();
        Sintomas2 = new LinkedList<Sintoma>();
        Sintomas3 = new LinkedList<Sintoma>();
        Sintomas1.add(Sintoma.CONJUTIVITE);
        Sintomas1.add(Sintoma.ABDOMEM);
        Sintomas1.add(Sintoma.AGEUSIA);
        Sintomas2.add(Sintoma.ANOSMIA);
        Sintomas2.add(Sintoma.AR);
        Sintomas3.add(Sintoma.AGEUSIA);
        Paciente[] Patients = new Paciente[3];
        Patients[0] = new Paciente("Rafael", 1, 21, 7, Comorbs1, Sintomas1);
        Patients[1] = new Paciente("Raquel", 2, 11, 3, Comorbs2, Sintomas2);
        Patients[2] = new Paciente("Ronaldo", 3, 41, 2, Comorbs3, Sintomas3);

        for (int i = 0; i<Patients.length; i++) {
            System.out.println(Patients[i].getIdade());
            converter.put(Patients[i].getName(), Patients[i]);
        }
//            System.out.println(converter);

        ArrayList arraySpinner = new ArrayList();
        arraySpinner.add(Patients[0].getName());
        arraySpinner.add(Patients[1].getName());
        arraySpinner.add(Patients[2].getName());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(adapter);
        this.currPatient = Patients[0];
        update();


        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    System.out.println(patientSpinner.getSelectedItem().toString());
                    System.out.println(converter);
                    currPatient = converter.get(patientSpinner.getSelectedItem().toString());

                    System.out.println(currPatient);
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }
}
