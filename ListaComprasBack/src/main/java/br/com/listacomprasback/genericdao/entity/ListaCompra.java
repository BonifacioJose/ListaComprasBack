package br.com.listacomprasback.genericdao.entity;

import br.com.listacomprasback.genericdao.dto.ListaCompraProdutoDTO;
import br.com.listacomprasback.genericdao.interfaces.DatabaseEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bonifácio
 */
public class ListaCompra implements DatabaseEntity, Serializable {

    private Long id;
    private String nome;
    private Date dataCadastro;
    
    private List<ListaCompraProdutoDTO> listaProdutos;

    public ListaCompra() {
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<ListaCompraProdutoDTO> getListaProdutos() {
        if (listaProdutos == null) {
            listaProdutos = new ArrayList<>();
        }
        return listaProdutos;
    }

    public void setListaProdutos(List<ListaCompraProdutoDTO> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nome);
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
        final ListaCompra other = (ListaCompra) obj;
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
