package br.com.listacomprasback.genericdao.dto;

import br.com.listacomprasback.genericdao.entity.ListaCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author José
 */
public class ListaCompraDTO implements Serializable {
    
    private Long id;
    private String nome;
    private Date dataCadastro;
    private BigDecimal valor;
    private BigDecimal valorAtualizado;
    
    private List<ListaCompraProdutoDTO> listaProdutos;

    public ListaCompraDTO() {
    }
    
    public ListaCompraDTO(ListaCompra listaCompra, BigDecimal valorProduto) {
        this.id = listaCompra.getId();
        this.nome = listaCompra.getNome();
        this.dataCadastro = listaCompra.getDataCadastro();
        this.valor = valorProduto;
        this.valorAtualizado = valorProduto;
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

    public BigDecimal getValor() {
        if (valor == null) {
            valor = new BigDecimal(0);
        }
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorAtualizado() {
        if (valorAtualizado == null) {
            valorAtualizado = new BigDecimal(0);
        }
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
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
    
    @XmlTransient
    public ListaCompra getListaCompra() {
        ListaCompra listaCompra = new ListaCompra();
        listaCompra.setId(this.id);
        listaCompra.setNome(this.nome);
        listaCompra.setDataCadastro(this.dataCadastro);
        return listaCompra;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final ListaCompraDTO other = (ListaCompraDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
