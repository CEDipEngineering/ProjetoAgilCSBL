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
import java.util.LinkedList;

public class PatientActivity extends AppCompatActivity{
    private Spinner patientSpinner;
    private Button examesButton, editButton;
    private ListView summaryView, symptomView, comorbityView;
    private Paciente currPatient;

    private void update(){
        ArrayList<Comorbidade> patientComorbs = new ArrayList<Comorbidade>(this.currPatient.getComorbidades());
        ArrayList<Sintoma> patientSymptoms = new ArrayList<Sintoma>(this.currPatient.getSintomas());
        ArrayList<String> patientSummary = new ArrayList<String>();

        patientSummary.add("Nome: " + this.currPatient.getName());
        patientSummary.add("Idade: " + this.currPatient.getIdade());
        patientSummary.add("Dias com sintomas: " + this.currPatient.getTempoSintomas());
        patientSummary.add("Leito: " + this.currPatient.getLeito());
        patientSummary.add("Risco: " + this.currPatient.getRisco()*100 + "%");

        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                patientSummary);
        summaryView.setAdapter(adapter0);

        ArrayAdapter<Sintoma> adapter1 = new ArrayAdapter<Sintoma>(this,
                android.R.layout.simple_list_item_1,
                patientSymptoms);
        symptomView.setAdapter(adapter1);

        ArrayAdapter<Comorbidade> adapter2 = new ArrayAdapter<Comorbidade>(this,
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
            Intent intent = new Intent(PatientActivity.this, EditorActivity.class);
            // Tem que passar o paciente atual também;
            startActivity(intent);
        });
        // bloco if somente para poder colapsar a parte de criar exemplors
        if (true) {
            LinkedList<Comorbidade> Comorbs1, Comorbs2, Comorbs3;
            Comorbidade obesidade = new Comorbidade("Obesidade");
            Comorbidade diabetes = new Comorbidade("Diabetes");
            Comorbidade hiv = new Comorbidade("HIV");
            Comorbidade cancerEsofago = new Comorbidade("Câncer esofágico");
            Comorbidade cegueira = new Comorbidade("Cegueira");
            Comorbs1 = new LinkedList<Comorbidade>();
            Comorbs2 = new LinkedList<Comorbidade>();
            Comorbs3 = new LinkedList<Comorbidade>();
            Comorbs1.add(obesidade);
            Comorbs1.add(hiv);
            Comorbs1.add(cancerEsofago);
            Comorbs2.add(hiv);
            Comorbs2.add(cegueira);
            Comorbs3.add(diabetes);

            LinkedList<Sintoma> Sintomas1, Sintomas2, Sintomas3;
            Sintoma coriza = new Sintoma("Coriza");
            Sintoma faltaAr = new Sintoma("Falta de Ar");
            Sintoma febre = new Sintoma("Febre");
            Sintoma hypoNatremia = new Sintoma("Hiponatremia");
            Sintoma dorNoPe = new Sintoma("Dor no pé");
            Sintomas1 = new LinkedList<Sintoma>();
            Sintomas2 = new LinkedList<Sintoma>();
            Sintomas3 = new LinkedList<Sintoma>();
            Sintomas1.add(coriza);
            Sintomas1.add(faltaAr);
            Sintomas1.add(dorNoPe);
            Sintomas2.add(hypoNatremia);
            Sintomas2.add(febre);
            Sintomas3.add(dorNoPe);
            Paciente[] arraySpinner = new Paciente[3];
            arraySpinner[0] = new Paciente("Rafael", 1, 21, 7, Comorbs1, Sintomas1);
            arraySpinner[1] = new Paciente("Raquel", 2, 11, 3, Comorbs2, Sintomas2);
            arraySpinner[2] = new Paciente("Ronaldo", 3, 41, 2, Comorbs3, Sintomas3);
            ArrayAdapter<Paciente> adapter = new ArrayAdapter<Paciente>(this, android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            patientSpinner.setAdapter(adapter);
            this.currPatient = arraySpinner[0];
        }


        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();

                try {
                    System.out.println(patientSpinner.getSelectedItem());
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}

