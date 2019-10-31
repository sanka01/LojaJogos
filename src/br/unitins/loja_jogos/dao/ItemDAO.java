package br.unitins.loja_jogos.dao;

import br.unitins.loja_jogos.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO<Item> {
    public ItemDAO(Connection conn) {
        super(conn);
    }

    public ItemDAO() {
        super(null);
    }

    @Override
    public void create(Item item) throws SQLException {

        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO " +
                        "public.item " +
                        " (jogo, " +
                        "  valor, " +
                        "  carrinho) " +
                        "VALUES " +
                        " (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
        stat.setInt(1, item.getJogo().getId());
        stat.setFloat(2, item.getValor());
        stat.setInt(3, item.getPedido().getId());

        stat.execute();


    }

    @Override
    public void update(Item item) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "UPDATE public.item SET " +
                        " jogo = ?, " +
                        " valor = ?, " +
                        " carrinho = ? " +
                        "WHERE " +
                        " id = ? ");
        stat.setInt(1, item.getJogo().getId());
        stat.setFloat(2, item.getValor());
        stat.setInt(3, item.getPedido().getId());
        stat.setInt(4, item.getId());

        stat.execute();
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection conn = getConnection();


        PreparedStatement stat = conn.prepareStatement(
                "DELETE FROM public.item WHERE id = ?");
        stat.setInt(1, id);

        stat.execute();
        return;
    }

    @Override
    public List<Item> findAll() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  jogo, " +
                        "  valor, " +
                        "  carrinho " +
                        "FROM " +
                        "  public.item ");
        ResultSet rs = stat.executeQuery();

        List<Item> items = new ArrayList<Item>();
        PedidoDAO pedidoDAO = new PedidoDAO();
        JogoDAO jogoDAO = new JogoDAO();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setPedido(pedidoDAO.findId(rs.getInt("carrinho")));
            item.setValor(rs.getFloat("valor"));
            item.setJogo(jogoDAO.findById(rs.getInt("jogo")));
            items.add(item);
        }

        if (items.isEmpty())
            return null;
        return items;


    }

    public Item findId(Integer id) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  jogo, " +
                        "  valor, " +
                        "  carrinho  " +
                        "FROM " +
                        "  public.item " +
                        "WHERE id = ? ");

        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();

        Item item = null;
        PedidoDAO pedidoDAO = new PedidoDAO();
        JogoDAO jogoDAO = new JogoDAO();
        if (rs.next()) {
            item = new Item();
            item.setId(rs.getInt("id"));
            item.setPedido(pedidoDAO.findId(rs.getInt("carrinho")));
            item.setValor(rs.getFloat("valor"));
            item.setJogo(jogoDAO.findById(rs.getInt("jogo")));
        }

        return item;

    }
}
