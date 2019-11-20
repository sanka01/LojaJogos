package br.unitins.loja_jogos.model;

public enum Genero {
    RPG(1, "RPG"),
    ACAO(2, "Ação"),
    AVENTURA(3,"Aventura"),
    SIMULACAO(4,"Simulação"),
    CASUAL(5,"Casual"),
    INDIE(6,"Indie"),
    ESTRATEGIA(7,"Estrategia"),
    CORRIDA(8,"Corrida");

    private int value;
    private String label;
    Genero(int value, String label) {
        this.value = value;
        this.label = label;
    }
    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static Genero valueOf(int value) {

        for (Genero genero : Genero.values()) {
            if (genero.getValue() == value)
                return genero;
        }
        return null;
    }
}
