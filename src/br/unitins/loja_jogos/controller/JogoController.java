package br.unitins.loja_jogos.controller;


import br.unitins.loja_jogos.application.Session;
import br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.model.Jogo;
import br.unitins.loja_jogos.model.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

@Named
@RequestScoped
public class JogoController{

    private Usuario usuario;
    private Jogo jogo;

    public JogoController() {
        Flash flash = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getFlash();
        flash.keep("jogoFlash");
        jogo = (Jogo) flash.get("jogoFlash");
    }


    public Jogo getJogo() {
        if (jogo == null) {
            jogo = new Jogo();
        }
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Usuario getUsuario() {
        if (usuario == null){
            usuario = (Usuario) Session.getInstance().getAttribute(Util.USER);
            if (usuario == null)
                usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
