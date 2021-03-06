package br.pro.hashi.ensino.desagil.firebase;

public enum Sintoma {
    ANOSMIA(1, "Anosmia (perda olfativa)"),
    AGEUSIA(2,"Ageusia"),
    CATARRO(3,"Tosse com produção de catarro"),
    ESCARRO(4, "Escarro com sangue/hemopstise"),
    GARGANTA(5,"Dor de garganta"),
    RINORREIA(6, "Corrimento nasal(rinorréia)"),
    OUVIDO(7, "Dor de ouvido"),
    SIBILOS(8, "Sibilos"),
    PEITO(9, "Dor no peito"),
    MUSCULAR(10, "Dores musculares(mialga)"),
    ARTICULACAO(11, "Dor nas articulações(artralgia)"),
    FADIGA(12, "Fadiga ou mal-estar"),
    AR(13, "Falta de ar"),
    TORACICA(14, "Rebaixamento da parede torácica inferior"),
    CABEÇA(15, "Dor de cabeça"),
    CONFUSAO(16, "Consciência alterada/confusão"),
    CONVULSAO(17, "Convulsões"),
    ABDOMEM(18, "Dor abdominal"),
    VOMITO(19, "Vômitos/náusea"),
    DIARREIA(20, "Diarréia"),
    CONJUTIVITE(21, "Conjutivite"),
    CUTANEA(22, "Erupção cutânea"),
    ULCERA(23, "Úlceras na pele"),
    LINFA(24,"Linfadenopatia"),
    SANGRAMENTO(25, "Sangramento(hemorragia)"),
    SECA(26, "Tosse seca");
    public String nome;
    public int id;
    Sintoma(int id,String nome) {
        this.nome = nome;
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    public int getId() {
        return this.id;

    }

    public static Sintoma getById(int id) {
        for(Sintoma e : values()) {
            if(e.getId() == id) {
                return e;
            }
        }
        return ANOSMIA;
    }

    public static Sintoma getByName(String name) {
        for(Sintoma e : values()) {
            if(e.getNome().equals( name)) {
                return e;
            }
        }
        return ANOSMIA;
    }

    public static String[] getNameArray(){
        String[] output = new String[Sintoma.class.getEnumConstants().length];
        for(int i = 0; i < Sintoma.class.getEnumConstants().length; i++){
            output[i] = Sintoma.class.getEnumConstants()[i].getNome();

        }
        return output;
    }
}