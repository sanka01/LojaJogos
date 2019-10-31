package br.unitins.loja_jogos.dao;

import br.unitins.loja_jogos.model.Pedido;
import br.unitins.loja_jogos.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends DAO<Pedido> {
    public PedidoDAO(Connection conn) {
        super(conn);
    }

    public PedidoDAO() {
        super(null);
    }

    @Override
    public void create(Pedido pedido) throws SQLException {

        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO " +
                        "public.pedido " +
                        " (valor_total, " +
                        "  usuario, " +
                        "  status) " +
                        "VALUES " +
                        " (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
        stat.setFloat(1, pedido.getValorTotal());
        stat.setInt(2, pedido.getUsuario().getId());
        stat.setInt(3, pedido.getStatus().getValue());

        stat.execute();


    }

    @Override
    public void update(Pedido pedido) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "UPDATE public.pedido SET " +
                        " valor_total= ?, " +
                        " usuario = ?, " +
                        " status = ? " +
                        "WHERE " +
                        " id = ? ");
        stat.setFloat(1, pedido.getValorTotal());
        stat.setInt(2, pedido.getUsuario().getId());
        stat.setInt(3, pedido.getStatus().getValue());
        stat.setInt(4, pedido.getId());

        stat.execute();
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection conn = getConnection();


        PreparedStatement stat = conn.prepareStatement(
                "DELETE FROM public.pedido WHERE id = ?");
        stat.setInt(1, id);

        stat.execute();
        return;
    }

    @Override
    public List<Pedido> findAll() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  valor_total, " +
                        "  usuario, " +
                        "  status " +
                        "FROM " +
                        "  public.pedido ");
        ResultSet rs = stat.executeQuery();

        List<Pedido> pedidos = new ArrayList<Pedido>();
        UsuarioDAO dao = new UsuarioDAO();
        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setId(rs.getInt("id"));
            pedido.setUsuario(dao.findId(rs.getInt("usuario")));
            pedido.setValorTotal(rs.getFloat("valor_total"));
            pedido.setStatus(Status.valueOf(rs.getInt("status")));
            pedidos.add(pedido);
        }

        if (pedidos.isEmpty())
            return null;
        return pedidos;


    }

    public Pedido findId(Integer id) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  valor_total, " +
                        "  usuario, " +
                        "  status  " +
                        "FROM " +
                        "  public.pedido " +
                        "WHERE id = ? ");

        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();

        Pedido pedido = null;
        UsuarioDAO dao = new UsuarioDAO();
        if (rs.next()) {
            pedido = new Pedido();
            pedido.setId(rs.getInt("id"));
            pedido.setUsuario(dao.findId(rs.getInt("usuario")));
            pedido.setValorTotal(rs.getFloat("valor_total"));
            pedido.setStatus(Status.valueOf(rs.getInt("status")));
        }

        return pedido;


    }
}
