package br.pro.hashi.ensino.desagil.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class AlaActivity extends Json {

    private GridViewAdapter mAdapter;

    private Ala ala1;
    private Ala ala2;
    private Ala ala3;
    private Ala currAla;
    private LinkedList<Leito> leitos = new LinkedList<Leito>();
    private LinkedList<Leito> leitos2 = new LinkedList<Leito>();
    private LinkedList<Ala> alas = new LinkedList<Ala>();
    private GridView gridView;
    private Spinner alaSpinner;
    private TextView lotacaoText;
    private ArrayList<Integer> idGridView = new ArrayList<Integer>();
    private ArrayList<String> nameGridView = new ArrayList<String>();
    private ArrayList<String> nameSpinner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ala);

        gridView = findViewById(R.id.ala);
        alaSpinner = findViewById(R.id.spinnerAla);
        lotacaoText = findViewById(R.id.lotacao);

        Leito leito1 = new Leito(1);
        Leito leito2 = new Leito(2);
        Leito leito3 = new Leito(3);
        Leito leito4 = new Leito(4);
        Leito leito5 = new Leito(5);
        Leito leito6 = new Leito(6);
        Leito leito7 = new Leito(7);
        Leito leito8 = new Leito(8);
        Leito leito9 = new Leito(9);
        Leito leito10 = new Leito(10);
        Leito leito11 = new Leito(11);
        Leito leito12 = new Leito(12);
        Leito leitoRafa = new Leito(1);
        Leito leitoRaq = new Leito(2);
        Leito leitoRon = new Leito(3);

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

        leitos2.add(leito1);
        leitos2.add(leito2);
        leitos2.add(leito3);
        leitos2.add(leito4);
        leitos2.add(leito5);
        leitos2.add(leito6);
        leitos2.add(leito7);
        leitos2.add(leito8);
        leitos2.add(leito9);
        leitos2.add(leito10);
        leitos2.add(leito11);
        leitos2.add(leito12);


        leitoRafa.setPaciente(new Paciente("Rafael", 1, 21, 7, Comorbs1, Sintomas1));
        leitoRaq.setPaciente(new Paciente("Raquel", 2, 11, 3, Comorbs2, Sintomas2));
        leitoRon.setPaciente(new Paciente("Ronaldo", 3, 41, 2, Comorbs3, Sintomas3));

        leitos.add(leitoRafa);
        leitos.add(leitoRaq);
        leitos.add(leitoRon);
        leitos.add(leito4);
        leitos.add(leito5);
        leitos.add(leito6);
        leitos.add(leito7);
        leitos.add(leito8);
        leitos.add(leito9);
        leitos.add(leito10);
        leitos.add(leito11);
        leitos.add(leito12);

        this.ala1 = new Ala("Ala 1", leitos, 3, 12);
        this.ala2 = new Ala("Ala 2", leitos2, 3,3);
        this.ala3 = new Ala("Ala 3", leitos2, 3,3);
        alas.add(ala1);
        alas.add(ala2);
        alas.add(ala3);
        nameSpinner.add(ala1.getName());
        nameSpinner.add(ala2.getName());
        nameSpinner.add(ala3.getName());

        for(Leito leito: ala1.getLeitos()){
            idGridView.add(leito.getId());
            if(leito.getPaciente() != null) {
                nameGridView.add(leito.getPaciente().getName());
            } else {
                nameGridView.add("vago");
            }
        }


        mAdapter = new GridViewAdapter(this,idGridView, nameGridView);
        gridView.setAdapter(mAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AlaActivity.this, android.R.layout.simple_spinner_dropdown_item, nameSpinner);
        alaSpinner.setAdapter(adapter);

        lotacaoText.setText(Integer.toString(ala1.getOcupação()));




        alaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    for(Ala ala: alas) {
                        if(ala.getName().equals(alaSpinner.getSelectedItem())) {
                            currAla = ala;
                        }
                    }

                    idGridView = new ArrayList<Integer>();
                    nameGridView = new ArrayList<String>();
                    for(Leito leito: currAla.getLeitos()){
                        idGridView.add(leito.getId());
                        if(leito.getPaciente() != null) {
                            nameGridView.add(leito.getPaciente().getName());
                        } else {
                            nameGridView.add("vago");
                        }
                    }

                    mAdapter = new GridViewAdapter(AlaActivity.this,idGridView, nameGridView);
                    gridView.setAdapter(mAdapter);
                    lotacaoText.setText("Lotação:\n" + currAla.getOcupação() + "/" + currAla.getCapacidade());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Leito leito_intent = null;
                for(Leito leito: currAla.getLeitos()){
                    if (leito.getId() == (i)){
                        leito_intent = leito;
                    }
                }
                Intent intent = new Intent(AlaActivity.this, PatientActivity.class);

                // Tem que passar o paciente atual também;
                try {
                    intent.putExtra("patientid", leito_intent.getPaciente().getId());
                } catch (Exception e){
                    e.printStackTrace();
                    intent = new Intent(AlaActivity.this, PatientEditActivity.class);
                }
                startActivity(intent);
            }
        });


    }
}
