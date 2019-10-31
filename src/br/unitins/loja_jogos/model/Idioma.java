package br.unitins.loja_jogos.model;

public enum Idioma {
    PORTUGUES(1,"Portugues"),
    INGLES(2,"Ingles"),
    JAPONES(3,"Japones"),
    ESPANHOL(4,"Espanhol");

    private int value;
    private String label;

    Idioma(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static Idioma valueOf(int value) {

        for (Idioma idioma : Idioma.values()) {
            if (idioma.getValue() == value)
                return idioma;
        }
        return null;
    }
}
