package br.unitins.loja_jogos.model;

public class Item {
    private Jogo jogo;
    private float valor;
    private Pedido pedido;
    private int id;


    public Jogo getJogo() {
        return jogo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
