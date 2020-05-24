package br.pro.hashi.ensino.desagil.firebase;

public class LeitoUTI extends Leito {
    private Paciente paciente;
    private int id;
    public LeitoUTI(int id) {
        super(id);
    }

    public int getId(){
        return id;
    }

    public Paciente getPaciente(){
        return paciente;
    }

    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
    }
}
