package br.unitins.loja_jogos.dao;

import br.unitins.loja_jogos.model.Genero;
import br.unitins.loja_jogos.model.Idioma;
import br.unitins.loja_jogos.model.Jogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO extends DAO<Jogo> {

    public JogoDAO(Connection conn) {
        super(conn);
    }

    public JogoDAO() {
        super(null);
    }

    @Override
    public void create(Jogo jogo) throws SQLException {

        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "INSERT INTO " +
                        "public.jogo " +
                        " (nome, " +
                        "  descricao, " +
                        "  tipo, " +
                        "  valor," +
                        "  desconto," +
                        "  desenvolvedor," +
                        "  genero," +
                        "  idioma) " +
                        "VALUES " +
                        " (?, ?, ?, ?, ?, ?, ?, ?) ");
        stat.setString(1, jogo.getNome());
        stat.setString(2, jogo.getDescricao());
        stat.setString(3, jogo.getTipo());
        stat.setFloat(4, jogo.getValor());
        stat.setDouble(5, jogo.getDesconto());
        stat.setString(6, jogo.getDesenvolvedor());
        stat.setInt(7, jogo.getGenero().getValue());
        stat.setInt(8, jogo.getIdioma().getValue());

        stat.execute();


    }

    @Override
    public void update(Jogo jogo) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stat = conn.prepareStatement(
                "UPDATE public.jogo SET " +
                        " nome = ?, " +
                        " descricao = ?, " +
                        " tipo = ?, " +
                        " valor = ?, " +
                        " desconto = ?, " +
                        " desenvolvedor = ?, " +
                        " genero = ?, " +
                        " idioma = ? " +
                        "WHERE " +
                        " id = ? ");
        stat.setString(1, jogo.getNome());
        stat.setString(2, jogo.getDescricao());
        stat.setString(3, jogo.getTipo());
        stat.setFloat(4, jogo.getValor());
        stat.setString(5, jogo.getDescricao());
        stat.setString(6, jogo.getDesenvolvedor());
        stat.setInt(7, jogo.getGenero().getValue());
        stat.setInt(8, jogo.getIdioma().getValue());


        stat.execute();
    }

    @Override
    public void delete(int id) throws SQLException {

        Connection conn = getConnection();


        PreparedStatement stat = conn.prepareStatement(
                "DELETE FROM public.jogo WHERE id = ?");
        stat.setInt(1, id);

        stat.execute();
        return;
    }

    @Override
    public List<Jogo> findAll() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        " id," +
                        " nome, " +
                        " descricao, " +
                        " tipo, " +
                        " valor, " +
                        " desconto, " +
                        " desenvolvedor, " +
                        " genero, " +
                        " idioma " +
                        "FROM " +
                        "  public.jogo ");
        ResultSet rs = stat.executeQuery();

        List<Jogo> jogos = new ArrayList<>();

        while (rs.next()) {
            Jogo jogo = new Jogo();
            jogo.setId(rs.getInt("id"));
            jogo.setNome(rs.getString("nome"));
            jogo.setDescricao(rs.getString("descricao"));
            jogo.setTipo(rs.getString("tipo"));
            jogo.setValor(rs.getFloat("valor"));
            jogo.setDesconto(rs.getFloat("desconto"));
            jogo.setDesenvolvedor(rs.getString("desenvolvedor"));
            jogo.setGenero(Genero.valueOf(rs.getInt("genero")));
            jogo.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

            jogos.add(jogo);
        }

        if (jogos.isEmpty())
            return new ArrayList<>();
        return jogos;

    }

    public Jogo findById(Integer id) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stat = conn.prepareStatement(
                "SELECT " +
                        " id," +
                        " nome, " +
                        " descricao, " +
                        " tipo, " +
                        " valor, " +
                        " desconto, " +
                        " desenvolvedor, " +
                        " genero, " +
                        " idioma " +
                        "FROM " +
                        "  public.jogo " +
                        "WHERE id = ? ");

        stat.setInt(1, id);

        ResultSet rs = stat.executeQuery();

        Jogo jogo = new Jogo();

        if (rs.next()) {
            jogo.setId(rs.getInt("id"));
            jogo.setNome(rs.getString("nome"));
            jogo.setDescricao(rs.getString("descricao"));
            jogo.setTipo(rs.getString("tipo"));
            jogo.setValor(rs.getFloat("valor"));
            jogo.setDesconto(rs.getFloat("desconto"));
            jogo.setDesenvolvedor(rs.getString("desenvolvedor"));
            jogo.setGenero(Genero.valueOf(rs.getInt("genero")));
            jogo.setIdioma(Idioma.valueOf(rs.getInt("idioma")));

        }

        return jogo;

    }
}
