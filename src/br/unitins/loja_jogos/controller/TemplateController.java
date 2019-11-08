package br.unitins.loja_jogos.controller;

import br.unitins.loja_jogos.application.Session;
import br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.model.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class TemplateController {
    private Usuario usuario;

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
