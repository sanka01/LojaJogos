package br.unitins.loja_jogos.controller;

import br.unitins.loja_jogos.br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.dao.DAO;
import br.unitins.loja_jogos.dao.UsuarioDAO;
import br.unitins.loja_jogos.model.Tipo;
import br.unitins.loja_jogos.model.Usuario;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PerfilController implements Serializable {
    private static final long serialVersionUID = 7483503027113826339L;

    private Usuario usuario;

    private List<Usuario> usuarioList;

    public List<Usuario> getListaUsuario() {
        if (usuarioList == null) {
            DAO<Usuario> dao = new UsuarioDAO();
            try {
                usuarioList = dao.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (usuarioList == null)
                usuarioList = new ArrayList<Usuario>();
        }
        return usuarioList;
    }

    public void incluir() {
        if (validarDados()) {
            DAO<Usuario> dao = new UsuarioDAO();
            // faz a inclusao no banco de dados
            try {
                dao.create(getUsuario());
                dao.getConnection().commit();
                Util.addMessageInfo("Inclusão realizada com sucesso.");
                limpar();
                usuarioList = null;
            } catch (SQLException e) {
                dao.rollbackConnection();
                dao.closeConnection();
                Util.addMessageInfo("Erro ao incluir o Usuário no Banco de Dados.");
                e.printStackTrace();
            }
        }
    }

    public void alterar() {
        if (validarDados()) {
            DAO<Usuario> dao = new UsuarioDAO();
            // faz a alteracao no banco de dados
            try {
                dao.update(getUsuario());
                dao.getConnection().commit();
                Util.addMessageInfo("Alteração realizada com sucesso");
                limpar();
                usuarioList = null;
            } catch (SQLException e) {
                dao.rollbackConnection();
                dao.closeConnection();
                Util.addMessageError("Erro ao alterar o Usuário no Banco de Dados");
                e.printStackTrace();

            }
        }
    }

    public void excluir() {
        excluir(getUsuario());
        limpar();
    }

    public boolean excluir(Usuario usuario) {
        DAO<Usuario> dao = new UsuarioDAO();
        // faz a exclusao no banco de dados
        try {
            dao.delete(usuario.getId());
            usuarioList = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean validarDados() {
        if (getUsuario().getSenha().isBlank()) {
            Util.addMessageWarn("O campo senha deve ser informado.");
            return false;
        }
//		if (getUsuario().getSenha() == null ||
//				getUsuario().getSenha().trim().equals("") ) {
//			Util.addMessageError("O campo senha deve ser informado.");
//			return false;
//		}
        return true;
    }

    private int ultimoId() {
        int maior = 0;
        for (Usuario usuario : usuarioList) {
            if (usuario.getId() > maior)
                maior = usuario.getId();
        }
        return maior;
    }

    public void editar(Usuario usuario) {
        UsuarioDAO dao = new UsuarioDAO();
        // buscando um usuario pelo id
        Usuario usu = null;
        try {
            usu = dao.findId(usuario.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setUsuario(usu);
//		setUsuario(dao.findId(usuario.getId()));
    }

    public Usuario getUsuario() {
        if (usuario == null) usuario = new Usuario();
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void limpar() {
        usuario = null;
    }

    public Tipo[] getListaTipo() {
        return Tipo.values();
    }

}
