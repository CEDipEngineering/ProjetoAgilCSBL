package br.pro.hashi.ensino.desagil.firebase;

import java.util.LinkedList;
import java.util.Random;

public class Paciente {
    private String name;
    private int id, idade, tempoSintomas;
    private Leito leito;
    private double risco = 0.67; //Por enquanto
    private LinkedList<Comorbidade> comorbidades;
    private LinkedList<Exame> exames;
    private LinkedList<Sintoma> sintomas;

    public Paciente(String name, int id, int idade, int tempoSintomas, LinkedList<Comorbidade> comorbidades, LinkedList<Sintoma> sintomas) {
        this.name = name;
        this.id = id;
        this.idade = idade;
        this.tempoSintomas = tempoSintomas;
        this.comorbidades = comorbidades;
        this.sintomas = sintomas;
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

    // O Array adapter precisa disso.
    @Override
    public String toString(){
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

    public LinkedList<Comorbidade> getComorbidades() {
        return comorbidades;
    }

    public LinkedList<Exame> getExames() {
        return exames;
    }

    public LinkedList<Sintoma> getSintomas() {
        return sintomas;
    }
}
