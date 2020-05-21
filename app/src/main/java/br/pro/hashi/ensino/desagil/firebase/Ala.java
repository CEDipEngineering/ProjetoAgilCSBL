package br.pro.hashi.ensino.desagil.firebase;

import java.util.LinkedList;

public class Ala {
    private String name;
    private LinkedList<Leito> leitos;
    private int capacidade;
    private double ocupação;

    public String getName() {
        return name;
    }

    public LinkedList<Leito> getLeitos() {
        return leitos;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public double getOcupação() {
        return ocupação;
    }

    public void setOcupação(double ocupação) {
        this.ocupação = ocupação;
    }
}
