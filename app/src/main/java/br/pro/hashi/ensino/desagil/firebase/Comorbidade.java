package br.pro.hashi.ensino.desagil.firebase;

public enum Comorbidade {
    CARDIO(1,"Doença Cardiovascular, incluindo hipertensão"),
    DIABETES(2,"Diabetes"),
    HEPATICA(3,"Doença Hepática"),
    NEURO(4,"Doença Neurológica crônica ou neuromuscular"),
    IMUNODEF(5,"Imunodeficiência"),
    HIV(6,"HIV"),
    RENAL(7,"Doença renal"),
    PULMONAR(8,"Doença pulmonar crônica"),
    NEOPLASIA(9,"Neoplasia(Tumor sólido ou hematológico)");

    private String nomeComorbidades;
    private int id;
    Comorbidade(int id,String nome) {
        this.nomeComorbidades = nome;
        this.id = id;
    }

    public String getNomeComorbidades(){
        return this.nomeComorbidades;
    }
    public int getId(){
        return this.id;
    }

    public static Comorbidade getById(int id) {
        for(Comorbidade e : values()) {
            if(e.getId() == id) {
                return e;
            }
        }
        return CARDIO;
    }

    public static Comorbidade getByName(String name) {
        for(Comorbidade e : values()) {
            if(e.getNomeComorbidades().equals( name)) {
                return e;
            }
        }
        return CARDIO;
    }

    public static String[] getNameArray(){
        String[] output = new String[Comorbidade.class.getEnumConstants().length];
        for(int i = 0; i < Comorbidade.class.getEnumConstants().length; i++){
            output[i] = Comorbidade.class.getEnumConstants()[i].getNomeComorbidades();

        }
        return output;
    }
}
