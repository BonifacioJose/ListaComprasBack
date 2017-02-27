package br.com.listacomprasback.genericdao.dto;

import br.com.listacomprasback.genericdao.entity.ListaCompraProduto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author bonif
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaCompraProdutoDTO {    
        
    private Long idListaCompra;
    private Long idProduto;
    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal valorAtualizado;
    private String nome;
    private String urlImagem;

    public ListaCompraProdutoDTO() {
    }
    
    public ListaCompraProdutoDTO(ListaCompraProduto listaCompraProduto, String nome, BigDecimal valorOriginal, String urlImagem) {
        this.idListaCompra = listaCompraProduto.getIdListaCompra();
        this.idProduto = listaCompraProduto.getIdProduto();
        this.quantidade = listaCompraProduto.getQuantidade();
        this.valorAtualizado = listaCompraProduto.getValorProduto();
        this.valor = valorOriginal;
        this.nome = nome;
        this.urlImagem = urlImagem;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorAtualizado() {
        return valorAtualizado;
    }

    public void setValorAtualizado(BigDecimal valorAtualizado) {
        this.valorAtualizado = valorAtualizado;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idListaCompra);
        hash = 97 * hash + Objects.hashCode(this.idProduto);
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
        final ListaCompraProdutoDTO other = (ListaCompraProdutoDTO) obj;
        if (!Objects.equals(this.idListaCompra, other.idListaCompra)) {
            return false;
        }
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        return true;
    }
    
    
    
}
