package br.com.listacomprasback.genericdao.dao;

import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.entity.Produto;
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
public class ProdutoDao extends GenericDaoImpl<Produto> implements GenericDao<Produto> {

    public ProdutoDao(Connection conexao) {
        super(conexao,
                "produto",
                (new String[]{"id"}),
                (new String[]{"nome", "valor", "urlImagem"}),
                (new String[]{"nome", "valor", "urlImagem"}));
    }

    @Override
    public void prepareInsert(Produto entity) throws SQLException {
        getPs().setString(1, entity.getNome());
        getPs().setBigDecimal(2, entity.getValor());
        getPs().setString(3, entity.getUrlImagem());
    }

    @Override
    public void prepareUpdate(Produto entity) throws SQLException {
        getPs().setString(1, entity.getNome());
        getPs().setBigDecimal(2, entity.getValor());
        getPs().setString(3, entity.getUrlImagem());
        getPs().setLong(4, entity.getId());
    }

    @Override
    public Produto extract(ResultSet rs) throws SQLException {
        Produto entity = new Produto();
        entity.setId(rs.getLong("produto_id"));
        entity.setNome(rs.getString("produto_nome"));
        entity.setValor(rs.getBigDecimal("produto_valor"));
        entity.setUrlImagem(rs.getString("produto_urlImagem"));
        return entity;
    }

    @Override
    public Produto saveWithReturn(Produto entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Produto> buscarProdutosNaoSelecionadosPorListaCompra(ListaCompra listaCompra) throws SQLException {
        String sql = "select produto.id as produto_id,"
                + " produto.nome as produto_nome,"
                + " produto.valor as produto_valor,"
                + " produto.urlImagem as produto_urlImagem"
                + " from produto as produto"
                + " where produto.id not in (select lcp.idProduto from lista_compra_produto as lcp where lcp.idListaCompra = " + listaCompra.getId() + ")";
        List<Produto> lista = new ArrayList<>();
        ResultSet result = findByQuery(sql);       
        while (result.next()) {
            lista.add(extract(result));
        }
        return lista;
    }

}
