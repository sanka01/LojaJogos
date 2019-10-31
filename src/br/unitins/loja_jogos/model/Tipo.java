package br.unitins.loja_jogos.model;

public enum Tipo {
    ADMINISTRADOR(1, "Administrador"),
    FUNCIONARIO(2, "Funcionário"),
    CLIENTE(3, "Cliente");

    private int value;
    private String label;

    Tipo(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static Tipo valueOf(int value) {

        for (Tipo tipo : Tipo.values()) {
            if (tipo.getValue() == value)
                return tipo;
        }
        return null;
    }

}
