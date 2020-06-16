package br.pro.hashi.ensino.desagil.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlaActivity extends Json {

    private GridViewAdapter mAdapter;

    private Ala currAla;
    private LinkedList<Leito> leitos = new LinkedList<Leito>();
    Ala[] Alas;
    private GridView gridView;
    private Spinner alaSpinner;
    private TextView lotacaoText;
    private Button leitoAddButton, leitoDeleteButton, alaAddButton,alaDeleteButton;
    private ArrayList<Integer> idGridView = new ArrayList<Integer>();
    private ArrayList<String> nameGridView = new ArrayList<String>();
    private ArrayList<Double> riskPatient = new ArrayList<Double>();
    private ArrayList<String> nameSpinner;


    private void update(){

        idGridView = new ArrayList<Integer>();
        nameGridView = new ArrayList<String>();
        riskPatient = new ArrayList<Double>();
        for(Leito leito: currAla.getLeitos()){
            idGridView.add(leito.getNumber());
            if(leito.getPaciente() != null) {
                nameGridView.add(leito.getPaciente().getName());
                riskPatient.add(leito.getPaciente().getRisco());
            } else {
                nameGridView.add("vago");
                riskPatient.add((double) -1);
            }
        }


        lotacaoText.setText("Lotação:\n" + currAla.getOcupação() + "/" + currAla.getCapacidade());
    }

    private void getAlas(int pos){
        nameSpinner = new ArrayList<String>();
        String posname = "Ala 0";
        String json_f = loadData();
        try {
            JSONObject root = new JSONObject(json_f);
            JSONObject data = root.getJSONObject("database");
            JSONArray JSONpatients = data.getJSONArray("patients");
            JSONArray JSONwings = data.getJSONArray("wings");
            Alas = new Ala[JSONwings.length()];
            for (int a = 0; a < Alas.length; a++) {
                Alas[a] = new Ala(JSONwings.getJSONObject(a));
                int ocup = 0;
                for (int l = 0; l < Alas[a].getCapacidade(); l++) {
                    int id = l+1+Alas[a].getNumber()*1000;
                    Leito newL = new Leito(id);
                    int i = findIndex(JSONpatients,"leito",id);
                    if ( i != -1) {
                        JSONObject paciente = JSONpatients.getJSONObject(i);
                        newL.setPaciente(new Paciente(paciente));

                        ocup+=1;
                    }
                    Alas[a].addLeito(newL);
                }
                Alas[a].setOcupação(ocup);
                nameSpinner.add(Alas[a].getName());
                if (Alas[a].getId() == pos) { posname = Alas[a].getName(); }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            leitos.add(new Leito(1));
            Alas[0] = new Ala(1,"Ala 1", leitos,1,1);
        }

        mAdapter = new GridViewAdapter(this,idGridView, nameGridView, riskPatient);
        gridView.setAdapter(mAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AlaActivity.this, android.R.layout.simple_spinner_dropdown_item, nameSpinner);
        alaSpinner.setAdapter(adapter);
        alaSpinner.setSelection(adapter.getPosition(posname));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ala);

        gridView = findViewById(R.id.ala);
        alaSpinner = findViewById(R.id.spinnerAla);
        lotacaoText = findViewById(R.id.lotacao);

        alaAddButton = findViewById(R.id.alaAddButton);
        alaDeleteButton = findViewById(R.id.alaDeleteButton);
        leitoAddButton = findViewById(R.id.leitoAddButton);
        leitoDeleteButton = findViewById(R.id.leitoDeleteButton);

        ArrayList<String> nameSpinner;


        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        int alaId = myIntent.getIntExtra("idAla",-1);

        Alas = new Ala[1];

        if (alaId == -1) {
            getAlas(0);
        } else {
            getAlas(alaId);
        }


        //update();


        alaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    for(Ala ala: Alas) {
                        if(ala.getName().equals(alaSpinner.getSelectedItem())) {
                            currAla = ala;
                        }
                    }



                    update();

                    mAdapter = new GridViewAdapter(AlaActivity.this,idGridView, nameGridView, riskPatient);
                    gridView.setAdapter(mAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Leito leito_intent = null;

            for(Leito leito: currAla.getLeitos()){
                if (leito.getId()-currAla.getNumber()*1000 == (i+1)){
                    leito_intent = leito;
                }
            }
            Intent intent = new Intent(AlaActivity.this, PatientEditActivity.class);
            // Tem que passar o paciente atual também;
            if(leito_intent.getPaciente() != null) {
                intent = new Intent(AlaActivity.this, PatientActivity.class);
                intent.putExtra("idPaciente", leito_intent.getPaciente().getId());
            }
            intent.putExtra("idLeito", leito_intent.getId());
            intent.putExtra("idAla", currAla.getId());
            startActivity(intent);
        });

        alaAddButton.setOnClickListener((view) -> {
            int id = 0;
            String json = loadData();
            try {
                JSONObject root = new JSONObject(json);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONwings = data.getJSONArray("wings");
                JSONObject JSONwing = new JSONObject();


                id = getNext(JSONwings,"id");

                String name = "Ala "+ (id+1);
                JSONwing.put("nome", name);
                JSONwing.put("id", id);
                JSONwing.put("capacidade", 12);

                JSONwings.put(JSONwings.length(),JSONwing);
                JSONwings = sortData(JSONwings,"id");
                data.put("wings",JSONwings);
                root.put("database", data);

                saveData(root.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Ala adicionada com sucesso", Toast.LENGTH_SHORT);
                toast.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            getAlas(id);
            update();
        });

        alaDeleteButton.setOnClickListener((view) -> {
            int id = currAla.getId();
            String json = loadData();
            try {
                JSONObject root = new JSONObject(json);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONpatients = data.getJSONArray("patients");
                JSONArray JSONwings = data.getJSONArray("wings");
                if (JSONwings.length() != 1){
                    int i = findIndex(JSONwings,"id",id);

                    for(Leito leito: currAla.getLeitos()){
                        if(leito.getPaciente() != null) {
                            int patid = leito.getPaciente().getId();
                            int a = findIndex(JSONpatients,"id",patid);;
                            JSONpatients.getJSONObject(a).put("leito",-1);
                        }
                    }

                    JSONwings.remove(i);
                    data.put("wings",JSONwings);
                    data.put("patients",JSONpatients);
                    root.put("database", data);
                    saveData(root.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Ala removida com sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                    id = getPrevious(JSONwings,"id",id);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível remover ala", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getAlas(id);
            update();
        });

        leitoAddButton.setOnClickListener((view) -> {
            int id = currAla.getId();
            String json = loadData();
            try {
                JSONObject root = new JSONObject(json);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONwings = data.getJSONArray("wings");

                int i = findIndex(JSONwings,"id",currAla.getId());
                JSONObject JSONwing = JSONwings.getJSONObject(i);

                int cap = JSONwing.getInt("capacidade");
                JSONwing.put("capacidade", (cap + 1));

                JSONwings.put(i,JSONwing);
                data.put("wings",JSONwings);
                root.put("database", data);

                saveData(root.toString());
                Toast toast = Toast.makeText(getApplicationContext(), "Leito adicionado com sucesso", Toast.LENGTH_SHORT);
                toast.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            getAlas(id);
            update();
        });

        leitoDeleteButton.setOnClickListener((view) -> {
            int id = currAla.getId();
            String json = loadData();
            try {
                JSONObject root = new JSONObject(json);
                JSONObject data = root.getJSONObject("database");
                JSONArray JSONwings = data.getJSONArray("wings");
                JSONArray JSONpatients = data.getJSONArray("patients");


                int i = findIndex(JSONwings,"id",currAla.getId());
                JSONObject JSONwing = JSONwings.getJSONObject(i);

                int cap = JSONwing.getInt("capacidade");

                if (cap > 1) {
                    JSONwing.put("capacidade", (cap - 1));

                    Leito lastleito = new Leito(0);
                    for (Leito leto : currAla.getLeitos()) {
                        lastleito = leto;
                    }
                    if (lastleito.getPaciente() != null) {
                        int p = findIndex(JSONpatients,"id",lastleito.getPaciente().getId());;
                        JSONpatients.getJSONObject(p).put("leito",-1);
                        data.put("patients",JSONpatients);
                    }

                    JSONwings.put(i,JSONwing);
                    data.put("wings",JSONwings);
                    root.put("database", data);

                    saveData(root.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Leito removido com sucesso", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Não foi possível remover leito", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            getAlas(id);
            update();
        });
    }
}
