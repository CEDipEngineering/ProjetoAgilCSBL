package br.pro.hashi.ensino.desagil.firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Ala {
    private String name;
    private LinkedList<Leito> leitos;
    private int capacidade;
    private int ocupacao;
    private int id;

    public Ala(int id,String name, LinkedList<Leito> leitos, int ocupacao, int capacidade) {
        this.name = name;
        this.id = id;
        this.leitos = leitos;
        this.ocupacao = ocupacao;
        this.capacidade = capacidade;
    }


    public Ala(int id, String name, int ocupacao, int capacidade) {
        this.name = name;
        this.id = id;
        this.leitos = new LinkedList<Leito>();
        this.ocupacao = ocupacao;
        this.capacidade = capacidade;
    }

    public Ala(JSONObject ala) {
        this.leitos = new LinkedList<Leito>();
        try {
            this.name = ala.getString("nome");
            this.id = ala.getInt("id");
            this.capacidade = ala.getInt("capacidade");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public LinkedList<Leito> getLeitos() {
        return leitos;
    }

    public void addLeito(Leito leito) { this.leitos.add(leito); }

    public int getCapacidade() {
        return capacidade;
    }

    public int getOcupação() {
        return ocupacao;
    }

    public void setOcupação(int ocupa) {
        this.ocupacao = ocupa;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return id+1;
    }
}
