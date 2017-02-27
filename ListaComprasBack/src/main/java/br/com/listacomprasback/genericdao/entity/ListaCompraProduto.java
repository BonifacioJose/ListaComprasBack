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
public class ListaCompraProduto implements DatabaseEntity, Serializable {
    
    private Long idListaCompra;
    private Long idProduto;
    private Integer quantidade;
    private BigDecimal valorProduto;

    public ListaCompraProduto() {
    }

    public Long getIdListaCompra() {
        return idListaCompra;
    }

    public void setIdListaCompra(Long idListaCompra) {
        this.idListaCompra = idListaCompra;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.idListaCompra);
        hash = 47 * hash + Objects.hashCode(this.idProduto);
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
        final ListaCompraProduto other = (ListaCompraProduto) obj;
        if (!Objects.equals(this.idListaCompra, other.idListaCompra)) {
            return false;
        }
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        return true;
    }

    @Override
    public String[] pk() { 
        String[] pk = {""};
        if (idListaCompra != null && idProduto != null) {
            pk[0] = String.valueOf(idListaCompra);
            pk[1] = String.valueOf(idProduto);
            return pk;
        }
        return null;
    }

    @Override
    @XmlTransient
    public boolean isNew() {
        return true;
    }
    
}
