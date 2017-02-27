package br.com.listacomprasback.genericdao.entity;

import br.com.listacomprasback.genericdao.interfaces.DatabaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bonifácio
 */
public class Produto implements DatabaseEntity, Serializable {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private String urlImagem;

    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String[] pk() {
        String[] pk = {""};
        if (id != null) {
            pk[0] = String.valueOf(id);
            return pk;
        }
        return null;
    }

    @Override
    @XmlTransient
    public boolean isNew() {
        return pk() == null;
    }

}
