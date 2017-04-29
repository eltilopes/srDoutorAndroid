package br.com.srdoutorandroid.model;

/**
 * Created by elton on 01/04/2017.
 */


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "medico", id = "_id")
public class Medico extends Model implements Serializable {

    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "crm")
    private String crm;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "urlFoto")
    private String urlFoto;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Medico mensagem = (Medico) o;

        return !(id != null ? !id.equals(mensagem.id) : mensagem.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public Medico() {
    }

    public Medico(Integer id, String nome, String email, String crm, String cpf, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.cpf = cpf;
        this.urlFoto = urlFoto;
    }
    public Integer getIdMedico() { return id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

}
