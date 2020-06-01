package br.pro.hashi.ensino.desagil.firebase;

import java.util.LinkedList;

public class Ala {
    private String name;
    private LinkedList<Leito> leitos;
    private int capacidade;
    private int ocupacao;

    public Ala(String name, LinkedList<Leito> leitos, int ocupacao, int capacidade) {
        this.name = name;
        this.leitos = leitos;
        this.ocupacao = ocupacao;
        this.capacidade = capacidade;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Leito> getLeitos() {
        return leitos;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getOcupação() {
        return ocupacao;
    }

    public void setOcupação(int ocupação) {
        this.ocupacao = ocupacao;
    }
}
