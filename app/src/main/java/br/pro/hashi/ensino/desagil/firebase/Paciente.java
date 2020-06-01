package br.pro.hashi.ensino.desagil.firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Paciente implements Serializable {
    private String name;
    private int id, idade, tempoSintomas;
    private Leito leito;
    private double risco = 0.67; //Por enquanto
    private List<Comorbidade> comorbidades;
    private LinkedList<Exame> exames;
    private List<Sintoma> sintomas;

    public Paciente(String name, int id, int idade, int tempoSintomas, List<Comorbidade> comorbidades, List<Sintoma> sintomas) {
        this.name = name;
        this.id = id;
        this.idade = idade;
        this.tempoSintomas = tempoSintomas;
        this.comorbidades = comorbidades;
        this.sintomas = sintomas;
    }

    public Paciente(JSONObject pacient) {
        try {
            this.name = pacient.getString("nome");
            this.id = pacient.getInt("id");
            this.idade = pacient.getInt("idade");
            this.tempoSintomas = pacient.getInt("tempoSintomas");

            ArrayList<Comorbidade> comorbs= new ArrayList<Comorbidade>();
            JSONArray jarcomorbs = pacient.getJSONArray("comorbidades");
            if (jarcomorbs != null) {
                for (int i=0;i<jarcomorbs.length();i++) {
                    comorbs.add((Comorbidade)jarcomorbs.get(i));
                }
            }

            ArrayList<Sintoma> sints= new ArrayList<Sintoma>();
            JSONArray jarsints = pacient.getJSONArray("sintomas");
            if (jarsints != null) {
                for (int i=0;i<jarsints.length();i++) {
                    sints.add((Sintoma)jarcomorbs.get(i));
                }
            }

            this.comorbidades = comorbs;
            this.sintomas = sints;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setTempoSintomas(int tempoSintomas) {
        this.tempoSintomas = tempoSintomas;
    }

    public void setLeito(Leito leito) {
        this.leito = leito;
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

    public Leito getLeito() {
        return leito;
    }

    public double getRisco() {
        return risco;
    }

    public List<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public LinkedList<Exame> getExames() {
        return exames;
    }

    public List<Sintoma> getSintomas() {
        return sintomas;
    }
}