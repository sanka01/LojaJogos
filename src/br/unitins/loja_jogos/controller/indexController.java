package br.unitins.loja_jogos.controller;

import br.unitins.loja_jogos.application.Session;
import br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.dao.DAO;
import br.unitins.loja_jogos.dao.JogoDAO;
import br.unitins.loja_jogos.model.*;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class indexController implements Serializable {
    private static final long serialVersionUID = 2971574874624531104L;
    private Usuario usuario;

    private Jogo jogoView = null;

    private String nome = null;

    private List<Jogo> jogos = null;


    public void adicionar(int idProduto) {
        JogoDAO dao = new JogoDAO();
        Jogo jogo = null;
        try {
            jogo = dao.findById(idProduto);
            // verifica se existe um carrinho na sessao
            if (Session.getInstance().getAttribute("carrinho") == null) {
                // adiciona um carrinho (de itens de venda) na sessao
                Session.getInstance().setAttribute("carrinho",
                        new ArrayList<Item>());
            }

            // obtendo o carrinho da sessao
            List<Item> carrinho =
                    (ArrayList<Item>) Session.getInstance().getAttribute("carrinho");

            // criando um item de venda para adicionar no carrinho
            Item item = new Item();
            item.setJogo(jogo);
            item.setValor(jogo.getValor());

            // adicionando o item no carrinho (variavel local)
            carrinho.add(item);

            // atualizando o carrinho na sessao
            Session.getInstance().setAttribute("carrinho", carrinho);

            Util.addMessageInfo("Jogo adicionado no carrinho. "
                    + "Quantidade de Itens: " + carrinho.size());
        } catch (SQLException e) {
            Util.addMessageError("Erro ao adicionar jogo no carrinho");
            e.printStackTrace();
        }


    }


    public void incluir() {

        DAO<Jogo> dao = new JogoDAO(null);
        // faz a inclusao no banco de dados
        try {
            dao.create(getJogoView());
            dao.getConnection().commit();
            Util.addMessageInfo("Inclusão realizada com sucesso.");
            limpar();
        } catch (SQLException e) {
            dao.rollbackConnection();
            dao.closeConnection();
            Util.addMessageError("Erro ao incluir o Produto no Banco de Dados.");
            e.printStackTrace();
        }

    }

    public void alterar() {
        DAO<Jogo> dao = new JogoDAO();
        // faz a alteracao no banco de dados
        try {
            dao.update(getJogoView());
            dao.getConnection().commit();
            Util.addMessageInfo("Alteração realizada com sucesso.");
            limpar();
        } catch (SQLException e) {
            e.printStackTrace();
            dao.rollbackConnection();
            dao.closeConnection();
            Util.addMessageError("Erro ao alterar o Produto no Banco de Dados.");
        }

    }


    public boolean excluir() {
        DAO<Jogo> dao = new JogoDAO();
        // faz a exclusao no banco de dados
        try {
            dao.delete(getJogoView().getId());
            dao.getConnection().commit();
            Util.addMessageInfo("Exclusão realizada com sucesso.");
            limpar();
            return true;
        } catch (SQLException e) {
            Util.addMessageError("Erro ao excluir o Produto no Banco de Dados.");
            dao.rollbackConnection();
            dao.closeConnection();
            e.printStackTrace();
            return false;
        }
    }

    public String mostrar(int id) {
        JogoDAO dao = new JogoDAO();
        Jogo jogo = null;
        try {
            jogo = dao.findById(id);
            Flash flash = FacesContext.
                    getCurrentInstance().
                    getExternalContext().getFlash();
            flash.put("jogoFlash", jogo);
            return "jogo.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            Util.addMessageError("Falha ao buscar detalhes do jogo");
            e.printStackTrace();
            return "";
        }

    }

    public void editar(Jogo jogoParametro) {
        JogoDAO dao = new JogoDAO();
        // buscando um jogo pelo id
        try {
            Jogo jogoBD = dao.findById(jogoParametro.getId());
            setJogoView(jogoBD);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Jogo getJogoView() {
        if (jogoView == null) {
            jogoView = new Jogo();
        }
        return jogoView;
    }

    public void setJogoView(Jogo jogo) {
        this.jogoView = jogo;
    }

    public void limpar() {
        jogoView = null;
    }

    public Tipo[] getListaPerfil() {
        return Tipo.values();
    }
    public Idioma[] getListaIdiomas() {
        return Idioma.values();
    }
    public Genero[] getListaGeneros() {
        return Genero.values();
    }

    public List<Jogo> getJogos() {
        if (jogos == null) {
            JogoDAO dao = new JogoDAO();
            try {
                jogos = dao.findByNome(nome);
            } catch (SQLException e) {
                e.printStackTrace();
                Util.addMessageError("Falha ao buscar jogos");
            }
        }
        return jogos;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = (Usuario) Session.getInstance().getAttribute(Util.USER);
            if (usuario == null)
                usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void pesquisar() {
        jogos = null;
    }
}
