package br.unitins.loja_jogos.dao;

import br.unitins.loja_jogos.model.Tipo;
import br.unitins.loja_jogos.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO<Usuario> {

    public UsuarioDAO(Connection conn) {
        super(conn);
    }

    public UsuarioDAO() {
        super(null);
    }
    public Usuario login(String login, String senha) {

        Connection conn = getConnection();

        try {
            PreparedStatement stat = conn.prepareStatement(
                    "SELECT " +
                            "  id, " +
                            "  nome, " +
                            "  login, " +
                            "  senha, " +
                            "  tipo " +
                            "FROM " +
                            "  public.usuario " +
                            "WHERE login = ? AND senha = ? ");

            stat.setString(1, login);
            stat.setString(2, senha);

            ResultSet rs = stat.executeQuery();

            Usuario usuario = null;

            if(rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(Tipo.valueOf(rs.getInt("tipo")));
            }

            return usuario;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public void create(Usuario usuario) throws SQLException {

        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO " +
                        "public.usuario " +
                        " (nome, " +
                        "  login, " +
                        "  senha, " +
                        "  tipo) " +
                        "VALUES " +
                        " (?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, usuario.getNome());
        stat.setString(2, usuario.getLogin());
        stat.setString(3, usuario.getSenha());
        stat.setInt(4, usuario.getTipo().getValue());

        stat.execute();


    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "UPDATE public.usuario SET " +
                        " nome = ?, " +
                        " login = ?, " +
                        " senha = ?, " +
                        " tipo = ? " +
                        "WHERE " +
                        " id = ? ");
        stat.setString(1, usuario.getNome());
        stat.setString(2, usuario.getLogin());
        stat.setString(3, usuario.getSenha());
        stat.setInt(4, usuario.getTipo().getValue());
        stat.setInt(5, usuario.getId());

        stat.execute();
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection conn = getConnection();


        PreparedStatement stat = conn.prepareStatement(
                "DELETE FROM public.usuario WHERE id = ?");
        stat.setInt(1, id);

        stat.execute();
        return;
    }

    @Override
    public List<Usuario> findAll() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  nome, " +
                        "  login, " +
                        "  senha, " +
                        "  tipo " +
                        "FROM " +
                        "  public.usuario ");
        ResultSet rs = stat.executeQuery();

        List<Usuario> listaUsuario = new ArrayList<Usuario>();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipo(Tipo.valueOf(rs.getInt("tipo")));

            listaUsuario.add(usuario);
        }

        if (listaUsuario.isEmpty())
            return null;
        return listaUsuario;

    }

    public Usuario findId(Integer id) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        "  id, " +
                        "  nome, " +
                        "  login, " +
                        "  senha, " +
                        "  tipo " +
                        "FROM " +
                        "  public.usuario " +
                        "WHERE id = ? ");

        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();

        Usuario usuario = null;

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTipo(Tipo.valueOf(rs.getInt("tipo")));
        }

        return usuario;


    }

}
