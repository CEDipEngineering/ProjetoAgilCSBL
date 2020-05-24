package br.pro.hashi.ensino.desagil.firebase;

public enum Comorbidade {
    CARDIO(1,"Doença Cardiovascular, inculuindo hipertensão"),
    DIABETES(2,"Diabetes"),
    HEPATICA(3,"Doença Hepática"),
    NEURO(4,"Doença Neurológica crônica ou neuromuscular"),
    IMUNODEF(5,"Imunodeficiência"),
    HIV(6,"HIV"),
    RENAL(7,"Doença renal"),
    PULMONAR(8,"Doença pulmonar crônica"),
    NEOPLASIA(9,"Neoplasia(Tumor sólido ou hematológico)");

    public String nomeComorbidades;
    public int id;
    Comorbidade(int id,String nome) {
        this.nomeComorbidades = nome;
        this.id = id;
    }
    public String getNomeComorbidades(){
        return this.nomeComorbidades;
    }
}