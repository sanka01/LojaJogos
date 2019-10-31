package br.unitins.loja_jogos.dao;

import br.unitins.loja_jogos.model.BibliotecaJogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDAO extends DAO<BibliotecaJogo> {
    public BibliotecaDAO(Connection conn) {
        super(conn);
    }

    public BibliotecaDAO() {
        super(null);
    }

    @Override
    public void create(BibliotecaJogo bibliotecaJogo) throws SQLException {

        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO " +
                        "public.biblioteca_jogo " +
                        " (jogo, " +
                        "  perfil, " +
                        "  instalado) " +
                        "VALUES " +
                        " (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
        stat.setInt(1, bibliotecaJogo.getJogo().getId());
        stat.setInt(2, bibliotecaJogo.getUsuario().getId());
        stat.setBoolean(3, bibliotecaJogo.isInstalado());

        stat.execute();


    }

    @Override
    public void update(BibliotecaJogo bibliotecaJogo) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "UPDATE public.biblioteca_jogo SET " +
                        " jogo = ?, " +
                        " perfil = ?, " +
                        " instalado = ? " +
                        "WHERE " +
                        " id = ? ");
        stat.setFloat(1, bibliotecaJogo.getJogo().getId());
        stat.setInt(2, bibliotecaJogo.getUsuario().getId());
        stat.setBoolean(3, bibliotecaJogo.isInstalado());
        stat.setInt(4, bibliotecaJogo.getId());

        stat.execute();
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection conn = getConnection();


        PreparedStatement stat = conn.prepareStatement(
                "DELETE FROM public.biblioteca_jogo WHERE id = ?");
        stat.setInt(1, id);

        stat.execute();
        return;
    }

    @Override
    public List<BibliotecaJogo> findAll() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  jogo, " +
                        "  perfil, " +
                        "  instalado " +
                        "FROM " +
                        "  public.biblioteca_jogo ");
        ResultSet rs = stat.executeQuery();

        List<BibliotecaJogo> jogos = new ArrayList<BibliotecaJogo>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        JogoDAO jogoDAO = new JogoDAO();
        while (rs.next()) {
            BibliotecaJogo jogo = new BibliotecaJogo();
            jogo.setId(rs.getInt("id"));
            jogo.setUsuario(usuarioDAO.findId(rs.getInt("usuario")));
            jogo.setInstalado(rs.getBoolean("instalado"));
            jogo.setJogo(jogoDAO.findById(rs.getInt("jogo")));
            jogos.add(jogo);
        }

        if (jogos.isEmpty())
            return null;
        return jogos;


    }

    public BibliotecaJogo findId(Integer id) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  jogo, " +
                        "  perfil, " +
                        "  instalado  " +
                        "FROM " +
                        "  public.biblioteca_jogo " +
                        "WHERE id = ? ");

        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();

        BibliotecaJogo jogo = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        JogoDAO jogoDAO = new JogoDAO();
        if (rs.next()) {
            jogo = new BibliotecaJogo();
            jogo.setId(rs.getInt("id"));
            jogo.setUsuario(usuarioDAO.findId(rs.getInt("usuario")));
            jogo.setInstalado(rs.getBoolean("instalado"));
            jogo.setJogo(jogoDAO.findById(rs.getInt("jogo")));
        }

        return jogo;


    }
}
