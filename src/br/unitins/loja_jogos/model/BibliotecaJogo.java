package br.unitins.loja_jogos.model;

public class BibliotecaJogo {
    private Jogo jogo;
    private Usuario usuario;
    private boolean instalado;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isInstalado() {
        return instalado;
    }

    public void setInstalado(boolean instalado) {
        this.instalado = instalado;
    }
}
