package br.pro.hashi.ensino.desagil.firebase;

public class Leito {
    private Paciente paciente;
    private int id;

    public Leito(int id) {
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return id%1000;
    }


}
