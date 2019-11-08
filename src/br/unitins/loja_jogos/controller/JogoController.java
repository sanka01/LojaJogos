package br.unitins.loja_jogos.controller;


import br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.dao.DAO;
import br.unitins.loja_jogos.dao.JogoDAO;
import br.unitins.loja_jogos.model.Jogo;
import br.unitins.loja_jogos.model.Tipo;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;

@Named
@ViewScoped
public class JogoController implements Serializable {
    private static final long serialVersionUID = 5590814041742196405L;

    private Jogo jogo;

    public JogoController() {
        Flash flash = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getFlash();
//        flash.keep("jogoFlash");
        jogo = (Jogo) flash.get("jogoFlash");
    }

    public void alterar() {
        DAO<Jogo> dao = new JogoDAO();
        // faz a alteracao no banco de dados
        try {
            dao.update(getJogo());
            dao.getConnection().commit();
            Util.addMessageInfo("Alteração realizada com sucesso.");
            limpar();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.rollbackConnection();
            dao.closeConnection();
            Util.addMessageInfo("Erro ao alterar o Produto no Banco de Dados.");
        }

    }


    public boolean excluir() {
        DAO<Jogo> dao = new JogoDAO();
        // faz a exclusao no banco de dados
        try {
            dao.delete(getJogo().getId());
            dao.getConnection().commit();
            Util.addMessageInfo("Exclusão realizada com sucesso.");
            limpar();
            return true;
        } catch (SQLException e) {
            Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
            dao.rollbackConnection();
            dao.closeConnection();
            e.printStackTrace();
            return false;
        }
    }


    public void editar(Jogo jogo) {
        JogoDAO dao = new JogoDAO();
        // buscando um jogo pelo id
        Jogo jogoBD = null;
        try {
            jogoBD = dao.findById(jogo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setJogo(jogoBD);
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

    public void limpar() {
        jogo = null;
    }

    public Tipo[] getListaPerfil() {
        return Tipo.values();
    }

}
