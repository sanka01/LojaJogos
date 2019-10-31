package br.unitins.loja_jogos.model;

public enum Status {
    ABERTO(1,"Aberto"),
    FECHADO(2,"Fechado"),
    CANCELADO(3,"Cancelado");
    private int value;
    private String label;

    Status(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static Status valueOf(int value) {

        for (Status status : Status.values()) {
            if (status.getValue() == value)
                return status;
        }
        return null;
    }
}
