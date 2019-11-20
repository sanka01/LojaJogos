package br.unitins.loja_jogos.controller;

import br.unitins.loja_jogos.application.Session;
import br.unitins.loja_jogos.application.Util;
import br.unitins.loja_jogos.dao.ItemDAO;
import br.unitins.loja_jogos.dao.JogoDAO;
import br.unitins.loja_jogos.dao.PedidoDAO;
import br.unitins.loja_jogos.model.*;
import br.unitins.loja_jogos.model.Item;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CarrinhoController implements Serializable{

    private static final long serialVersionUID = 780477496476930858L;

    private Pedido pedido;

    public Pedido getPedido() {
        if (pedido == null)
            pedido = new Pedido();

        // obtendo o carrinho da sessao
        ArrayList<Item> carrinho =
                (ArrayList<Item>) Session.getInstance().getAttribute("carrinho");

        // adicionando os itens do carrinho na pedido
        if (carrinho == null)
            carrinho = new ArrayList<Item>();
        pedido.setItems(carrinho);

        return pedido;
    }

    public void remover(int id) {

//        ItemDAO dao = new ItemDAO();
//        try {
//            Item jogo = dao.findById(id);
//            ArrayList<Item> carrinho = (ArrayList)
//                    Session.getInstance().getAttribute("carrinho");
//            carrinho.remove(jogo);
//            Session.getInstance().setAttribute("carrinho",carrinho);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        ArrayList<Item> carrinho = (ArrayList)
                Session.getInstance().getAttribute("carrinho");
        carrinho.remove(id);

    }

    public void finalizar() {
        Usuario usuario = (Usuario)Session.getInstance().getAttribute("usuarioLogado");
        if (usuario == null) {
            Util.addMessageWarn("Eh preciso estar logado para realizar uma pedido. Faca o Login!!");
            return;
        }
        // montar a pedido
        Pedido pedido = new Pedido();
        pedido.setData(LocalDate.now());
        pedido.setUsuario(usuario);
        ArrayList<Item> carrinho = (ArrayList<Item>)
                Session.getInstance().getAttribute("carrinho");
        pedido.setItems(carrinho);
        // salvar no banco
        PedidoDAO dao = new PedidoDAO();
        try {
            dao.create(pedido);
            dao.getConnection().commit();
            Util.addMessageInfo("Pedido realizado com sucesso.");
            // limpando o carrinho
            Session.getInstance().setAttribute("carrinho", null);
        } catch (SQLException e) {
            dao.rollbackConnection();
            dao.closeConnection();
            Util.addMessageInfo("Erro ao finalizar a Pedido.");
            e.printStackTrace();
        }

    }

    public void setPedido(Pedido pedido) {

        this.pedido = pedido;
    }



}
