package br.unitins.loja_jogos.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Usuario {
    private int id;
    @NotEmpty(message = "O campo Nome não pode ser vazio")
    @Size(max = 60, message = "O campo Nome deve conter no máximo 60 caracteres")
    private String nome;
    @Email
    private String login;
    @Size(min = 6, max = 30, message = "A senha deve conter entre 6 e 30 caracteres")
    private String senha;
    private Tipo tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String usuario) {
        this.login = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
