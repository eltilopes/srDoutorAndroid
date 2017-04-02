package br.com.srdoutorandroid.model;

/**
 * Created by elton on 01/04/2017.
 */


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by raphael.bruno on 20/07/2015.
 */

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

    public Medico(Integer id, String nome, String email, String crm, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.cpf = cpf;
    }
}
