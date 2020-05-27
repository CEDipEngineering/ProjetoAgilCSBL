package br.pro.hashi.ensino.desagil.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientEditActivity extends AppCompatActivity {
    private TextView patientNameView, patientIdadeView, tempoSintomasView, leitoView, riscoView, comorbidadesView, examesView, sintomasView;
    private EditText tempoSintomasEdit, patientNameEdit, patientIdadeEdit, leitoEdit, riscoEdit;
    private ListView listaSintomasView, listaComorbidadesView;
    private String patientName;
    private List <Sintoma>  listaSintomasEnum = Arrays.asList(Sintoma.class.getEnumConstants());
    private List<Comorbidade> listaComorbidadesEnum = Arrays.asList(Comorbidade.class.getEnumConstants());
    private Button finalizarButton;
    private List<Sintoma>  sintomasSelecionados;
    private List<Comorbidade> comorbidadesSelecionadas;
    private ArrayAdapter<Sintoma> adapterSintomas;
    private ArrayAdapter<Comorbidade> adapterComorbidades;

    private int idpacient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> listaComorbidades = new ArrayList<String>();
        ArrayList<String> listaSintomas = new ArrayList<String>();
        for(Comorbidade e :listaComorbidadesEnum){
            listaComorbidades.add(e.getNomeComorbidades());
            //System.out.println(e.getNomeComorbidades());
        }
        for(Sintoma s :listaSintomasEnum){
            listaSintomas.add(s.getNome());
            //System.out.println(e.getNomeComorbidades());
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpatient);

        patientNameView = findViewById(R.id.name);
        patientIdadeView = findViewById(R.id.idade);
        patientNameEdit = findViewById(R.id.edit_name);
        patientIdadeEdit = findViewById(R.id.edit_idade);
        tempoSintomasEdit = findViewById(R.id.edit_dias);
        listaSintomasView = findViewById(R.id.list_sintomas);
        listaComorbidadesView = findViewById(R.id.list_comorbidades);
        finalizarButton = findViewById((R.id.finalizar));
        patientName = patientNameEdit.getText().toString();
        adapterSintomas = new ArrayAdapter<Sintoma>(this, android.R.layout.simple_list_item_multiple_choice, listaSintomasEnum);
        adapterComorbidades = new ArrayAdapter<Comorbidade>(this, android.R.layout.simple_list_item_multiple_choice, listaComorbidadesEnum);
        listaSintomasView.setAdapter(adapterSintomas);
        listaComorbidadesView.setAdapter(adapterComorbidades);



        Intent myIntent = getIntent();
        // Try to get message handed in when creating intent
        Paciente patient = (Paciente) myIntent.getSerializableExtra("patient");

        // If there is one, put it in the textView
        if (patient != null) {
            patientNameEdit.setText(patient.getName());
            patientIdadeEdit.setText(Integer.toString(patient.getIdade()));
            tempoSintomasEdit.setText(Integer.toString(patient.getTempoSintomas()));
            idpacient = patient.getId();
        }

        for (int i = 0; i < listaComorbidadesView.getCount(); i++) {
            Comorbidade comorbidade = (Comorbidade) listaComorbidadesView.getItemAtPosition(i);
            if (patient.getComorbidades().contains(comorbidade)){
                listaComorbidadesView.setItemChecked(i, true);
            }

            for (int b = 0; b < listaSintomasView.getCount(); b++) {
                Sintoma sintoma = (Sintoma) listaSintomasView.getItemAtPosition(b);
                if (patient.getSintomas().contains(sintoma)){
                    listaSintomasView.setItemChecked(b, true);
                }
            }
        }



        finalizarButton.setOnClickListener((view) -> {
            switch(view.getId()){
                case R.id.finalizar:
                    sintomasSelecionados = new ArrayList<Sintoma>();
                    comorbidadesSelecionadas = new ArrayList<Comorbidade>();
                    SparseBooleanArray sintomasChecked = listaSintomasView.getCheckedItemPositions();
                    //System.out.println(listaSintomasView);
                    SparseBooleanArray comorbidadesChecked = listaComorbidadesView.getCheckedItemPositions();
                    for(int i = 0;i<sintomasChecked.size(); i++){
                        int key =  sintomasChecked.keyAt(i);
                        boolean value = sintomasChecked.get(key);
                        if(value){
                            sintomasSelecionados.add((Sintoma) listaSintomasView.getItemAtPosition(key));
                        }
                    }
                    for(int i = 0;i<comorbidadesChecked.size(); i++){
                        int key =  comorbidadesChecked.keyAt(i);
                        boolean value = comorbidadesChecked.get(key);
                        if(value){
                            comorbidadesSelecionadas.add((Comorbidade) listaComorbidadesView.getItemAtPosition(key));
                        }
                    }

                    patientName = patientNameEdit.getText().toString();
                    int idade = Integer.parseInt(patientIdadeEdit.getText().toString());
                    int tempoSint = Integer.parseInt(tempoSintomasEdit.getText().toString());
                    Paciente Paciente1 = new Paciente(patientName, idpacient, idade, tempoSint, comorbidadesSelecionadas, sintomasSelecionados);

                    //intent
                    Intent intent = new Intent(PatientEditActivity.this, PatientActivity.class);
                    // Tem que passar o paciente atual também;
                    intent.putExtra("patient", Paciente1);
                    startActivity(intent);

            }
            //System.out.println(patient.getComorbidades());
        });
    }
}