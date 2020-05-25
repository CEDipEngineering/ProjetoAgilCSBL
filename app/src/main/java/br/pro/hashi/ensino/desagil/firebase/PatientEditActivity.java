package br.pro.hashi.ensino.desagil.firebase;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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
        listaSintomasView = findViewById(R.id.list_sintomas);
        listaComorbidadesView = findViewById(R.id.list_comorbidades);
        finalizarButton = findViewById((R.id.finalizar));
        patientName = patientNameEdit.getText().toString();
        adapterSintomas = new ArrayAdapter<Sintoma>(this, android.R.layout.simple_list_item_multiple_choice, listaSintomasEnum);
        adapterComorbidades = new ArrayAdapter<Comorbidade>(this, android.R.layout.simple_list_item_multiple_choice, listaComorbidadesEnum);
        listaSintomasView.setAdapter(adapterSintomas);
        listaComorbidadesView.setAdapter(adapterComorbidades);

        finalizarButton.setOnClickListener((view) -> {
            switch(view.getId()){
                case R.id.finalizar:
                    sintomasSelecionados = new ArrayList<Sintoma>();
                    comorbidadesSelecionadas = new ArrayList<Comorbidade>();
                    SparseBooleanArray sintomasChecked = listaSintomasView.getCheckedItemPositions();
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
                    Paciente Paciente1 = new Paciente("Rafael", 1, 21, 7, comorbidadesSelecionadas, sintomasSelecionados);
            }
            System.out.println(sintomasSelecionados);
            System.out.println(comorbidadesSelecionadas);
            System.out.println(patientNameEdit.getText().toString());
            System.out.println(patientIdadeEdit.getText().toString());
        });
    }
}