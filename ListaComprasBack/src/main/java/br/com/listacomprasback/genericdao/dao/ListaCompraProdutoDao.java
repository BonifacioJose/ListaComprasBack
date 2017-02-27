package br.com.listacomprasback.genericdao.dao;

import br.com.listacomprasback.genericdao.dto.ListaCompraProdutoDTO;
import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.entity.ListaCompraProduto;
import br.com.listacomprasback.genericdao.interfaces.GenericDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bonifácio
 */
public class ListaCompraProdutoDao extends GenericDaoImpl<ListaCompraProduto> implements GenericDao<ListaCompraProduto> {

    public ListaCompraProdutoDao(Connection conexao) {
        super(conexao,
                "lista_compra_produto",
                (new String[]{"idListaCompra", "idProduto"}),
                (new String[]{"idListaCompra", "idProduto","quantidade", "valorProduto"}),
                (new String[]{"quantidade", "valorProduto"}));
    }

    @Override
    public void prepareInsert(ListaCompraProduto entity) throws SQLException {
        getPs().setLong(1, entity.getIdListaCompra());
        getPs().setLong(2, entity.getIdProduto());
        getPs().setInt(3, entity.getQuantidade());
        getPs().setBigDecimal(4, entity.getValorProduto());
    }

    @Override
    public void prepareUpdate(ListaCompraProduto entity) throws SQLException {
        getPs().setInt(1, entity.getQuantidade());
        getPs().setBigDecimal(2, entity.getValorProduto());
        getPs().setLong(3, entity.getIdListaCompra());
        getPs().setLong(4, entity.getIdProduto());
    }

    @Override
    public ListaCompraProduto extract(ResultSet rs) throws SQLException {
        ListaCompraProduto entity = new ListaCompraProduto();
        entity.setIdListaCompra(rs.getLong("lista_compra_produto_idListaCompra"));
        entity.setIdProduto(rs.getLong("lista_compra_produto_idProduto"));
        entity.setQuantidade(rs.getInt("lista_compra_produto_quantidade"));
        entity.setValorProduto(rs.getBigDecimal("lista_compra_produto_valorProduto"));
        return entity;
    }

    @Override
    public ListaCompraProduto saveWithReturn(ListaCompraProduto entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ListaCompraProdutoDTO> buscarProdutosPorLista(ListaCompra listaCompra) throws SQLException {
        String sql = "select lista_compra_produto.idListaCompra as lista_compra_produto_idListaCompra,"
                + " lista_compra_produto.idProduto as lista_compra_produto_idProduto,"
                + " lista_compra_produto.quantidade as lista_compra_produto_quantidade,"
                + " lista_compra_produto.valorProduto as lista_compra_produto_valorProduto,"
                + " p.nome as nome,"
                + " p.valor as valorOriginal,"
                + " p.urlImagem as urlImagem"
                + " from lista_compra_produto as lista_compra_produto"
                + " left join produto as p on (lista_compra_produto.idProduto = p.id) where idListaCompra = " + listaCompra.getId();
        List<ListaCompraProdutoDTO> lista = new ArrayList<>();
        ResultSet result = findByQuery(sql);
        while (result.next()) {
            lista.add(new ListaCompraProdutoDTO(extract(result), result.getString("nome"), result.getBigDecimal("valorOriginal"), result.getString("urlImagem")));
        }
        return lista;
    }
    
    public void excluirTodosPorListaCompra(ListaCompra listaCompra) throws SQLException {
        String sql = "delete from lista_compra_produto where idListaCompra = " + listaCompra.getId();
        prepareStatement(sql).executeUpdate();
    }
}
