package br.pro.hashi.ensino.desagil.firebase;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Paciente {
    private String name;
    private int id, idade, tempoSintomas;
    private int idLeito;
    private double risco = 0.67; //Por enquanto
    private List<Comorbidade> comorbidades;
    private LinkedList<Exame> exames;
    private List<Sintoma> sintomas;
    private HashMap<String, String> sintomasData;


    public Paciente(String name, int id, int idade, int tempoSintomas, List<Comorbidade> comorbidades, List<Sintoma> sintomas, double risco, HashMap<String, String> sintomasData) {
        this.name = name;
        this.id = id;
        this.idade = idade;
        this.tempoSintomas = tempoSintomas;
        this.comorbidades = comorbidades;
        this.sintomas = sintomas;
        this.risco = risco;
        this.sintomasData = sintomasData;
    }

    public Paciente(JSONObject JSONpatient) {
        try {
            this.name = JSONpatient.getString("nome");
            this.id = JSONpatient.getInt("id");
            this.idade = JSONpatient.getInt("idade");
            this.tempoSintomas = JSONpatient.getInt("tempoSintomas");
            this.risco = JSONpatient.getDouble("risco");
            this.idLeito = JSONpatient.getInt("leito");


            ArrayList<Comorbidade> comorbs= new ArrayList<Comorbidade>();
            JSONArray jarcomorbs = JSONpatient.getJSONArray("comorbidades");
            if (jarcomorbs != null) {
                for (int i=0;i<jarcomorbs.length();i++) {
                    comorbs.add(Comorbidade.getById((Integer)jarcomorbs.get(i)));
                }
            }

            ArrayList<Sintoma> sints= new ArrayList<Sintoma>();
            JSONArray jarsints = JSONpatient.getJSONArray("sintomas");
            if (jarsints != null) {
                for (int i=0;i<jarsints.length();i++) {
                    sints.add(Sintoma.getById((Integer)jarsints.get(i)));
                }
            }

            this.comorbidades = comorbs;
            this.sintomas = sints;
            JSONObject jarsinData = JSONpatient.getJSONObject("sintomasData");
            HashMap<String, String> sintomasData = new Gson().fromJson(jarsinData.toString(), HashMap.class);
            this.sintomasData = sintomasData;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setSintomasData(HashMap<String, String> sintomasData) {
        this.sintomasData = sintomasData;
    }

    public void setTempoSintomas(int tempoSintomas) {
        this.tempoSintomas = tempoSintomas;
    }

    public void setLeitoId(int idLeito) {
        this.idLeito = idLeito;
    }

    public void addComorbidade(Comorbidade comorbidade) {
        this.comorbidades.add(comorbidade);
    }

    public void addExame(Exame Exame) {
        this.exames.add(Exame);
    }

    public void addSintoma(Sintoma Sintoma) {
        this.sintomas.add(Sintoma);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getIdade() {
        return idade;
    }

    public int getTempoSintomas() {
        return tempoSintomas;
    }

    public HashMap<String, String> getSintomasData() {
        return sintomasData;
    }

    public int getLeitoId() {
        return idLeito;
    }

    public int getAlaId() {
        return idLeito/1000;
    }

    public double getRisco() {
        return risco;
    }

    public List<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public JSONArray getIdComorbidades() {
        JSONArray ids = new JSONArray();
        for (Comorbidade comor : comorbidades) {
            ids.put(comor.getId());
        }
        return ids;
    }

    public JSONArray getIdSintomas() {
        JSONArray ids = new JSONArray();
        for (Sintoma sint : sintomas) {
            ids.put(sint.getId());
        }
        return ids;
    }

    public LinkedList<Exame> getExames() {
        return exames;
    }

    public List<Sintoma> getSintomas() {
        return sintomas;
    }
}