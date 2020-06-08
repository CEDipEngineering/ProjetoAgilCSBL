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
    private Button addButton;
    private ArrayList<Integer> idGridView = new ArrayList<Integer>();
    private ArrayList<String> nameGridView = new ArrayList<String>();
    private ArrayList<String> nameSpinner;


    private void update(){

        idGridView = new ArrayList<Integer>();
        nameGridView = new ArrayList<String>();
        for(Leito leito: currAla.getLeitos()){
            idGridView.add(leito.getNumber());
            if(leito.getPaciente() != null) {
                nameGridView.add(leito.getPaciente().getName());
            } else {
                nameGridView.add("vago");
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
            JSONArray JSONpacientes = data.getJSONArray("patients");
            JSONArray JSONwings = data.getJSONArray("wings");
            Alas = new Ala[JSONwings.length()];
            for (int a = 0; a < Alas.length; a++) {
                Alas[a] = new Ala(JSONwings.getJSONObject(a));
                int ocup = 0;
                for (int l = 0; l < Alas[a].getCapacidade(); l++) {
                    int id = l+1+Alas[a].getNumber()*1000;
                    Leito newL = new Leito(id);
                    for (int i = 0; i < JSONpacientes.length(); i++) {
                        JSONObject paciente = JSONpacientes.getJSONObject(i);
                        if (paciente.getInt("leito") == id) {
                            newL.setPaciente(new Paciente(paciente));
                            ocup+=1;
                            break;
                        }
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

        mAdapter = new GridViewAdapter(this,idGridView, nameGridView);
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

        addButton = findViewById(R.id.addButton);

        ArrayList<String> nameSpinner;


        Alas = new Ala[1];

        getAlas(0);


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

                    mAdapter = new GridViewAdapter(AlaActivity.this,idGridView, nameGridView);
                    gridView.setAdapter(mAdapter);

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
                    if (leito.getId()-currAla.getNumber()*1000 == (i+1)){
                        leito_intent = leito;
                    }
                }
                Intent intent = new Intent(AlaActivity.this, PatientActivity.class);
                // Tem que passar o paciente atual também;
                if(leito_intent.getPaciente() == null) {
                    intent = new Intent(AlaActivity.this, PatientEditActivity.class);
                }
                intent.putExtra("leito", leito_intent.getId());
                startActivity(intent);
            }
        });

        addButton.setOnClickListener((view) -> {
            int id = 0;
            String json = loadData();
            try {

                JSONObject root = new JSONObject(json);
                JSONObject data = root.getJSONObject("database");
                JSONArray alas = data.getJSONArray("wings");
                JSONObject ala = new JSONObject();

                while (id== alas.getJSONObject(id).getInt("wings")) { id++; }

                String name = "Ala "+ id+1;
                ala.put("nome", name);
                ala.put("id", id);
                ala.put("capacidade", 12);

                alas.put(alas.length(),ala);
                data.put("alas",alas);
                root.put("database", data);

                saveData(root.toString());



                } catch (JSONException e) {

                    e.printStackTrace();
                }

            getAlas(id);
            update();
        });

    }
}
