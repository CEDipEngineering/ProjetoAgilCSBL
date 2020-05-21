package br.pro.hashi.ensino.desagil.firebase;

public class Exame {
    private String name;
    private boolean resultado;

    public Exame(String name, boolean resultado) {
        this.name = name;
        this.resultado = resultado;
    }

    public String getName() {
        return name;
    }

    public boolean isResultado() {
        return resultado;
    }
}
