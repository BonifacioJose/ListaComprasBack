package br.com.listacomprasback.genericdao.dao;

import br.com.listacomprasback.genericdao.dto.ListaCompraDTO;
import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.interfaces.DatabaseEntity;
import br.com.listacomprasback.genericdao.interfaces.GenericDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bonifácio
 */
public class ListaCompraDao extends GenericDaoImpl<ListaCompra> implements GenericDao<ListaCompra> {

    public ListaCompraDao(Connection conexao) {
        super(conexao,
                "lista_compra",
                (new String[]{"id"}),
                (new String[]{"nome", "dataCadastro"}),
                (new String[]{"nome"}));
    }

    @Override
    public void prepareInsert(ListaCompra entity) throws SQLException {
        getPs().setString(1, entity.getNome());
        getPs().setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
    }

    @Override
    public void prepareUpdate(ListaCompra entity) throws SQLException {
        getPs().setString(1, entity.getNome());
        getPs().setLong(2, entity.getId());
    }

    @Override
    public ListaCompra extract(ResultSet rs) throws SQLException {
        ListaCompra entity = new ListaCompra();
        entity.setId(rs.getLong("lista_compra_id"));
        entity.setNome(rs.getString("lista_compra_nome"));
        entity.setDataCadastro(new java.util.Date(rs.getTimestamp("lista_compra_dataCadastro").getTime()));
        return entity;
    }

    @Override
    public <T extends DatabaseEntity> boolean delete(T entity) throws SQLException {
        String sql = "delete from lista_compra_produto where idListaCompra = " + entity.pk()[0];
        prepareStatement(sql).executeUpdate();
        return super.delete(entity);
    }

    public ListaCompra buscarPorPorIdComProdutos(Long id) throws SQLException {
        ListaCompra listaCompra = findById(id);
        ListaCompraProdutoDao listaCompraProdutoDao = new ListaCompraProdutoDao(getConnection());
        listaCompra.setListaProdutos(listaCompraProdutoDao.buscarProdutosPorLista(listaCompra));
        listaCompraProdutoDao.closePointers();
        return listaCompra;
    }

    public List<ListaCompraDTO> buscarTodasComValorTotal() throws SQLException {
        String sql = "select lc.id as lista_compra_id, lc.nome as lista_compra_nome, lc.dataCadastro as lista_compra_dataCadastro,"
                + " sum(lcp.quantidade * lcp.valorProduto) as valorTotal from lista_compra as lc"
                + " left join lista_compra_produto as lcp on (lcp.idListaCompra = lc.id)"
                + " group by lc.id"
                + " order by lc.dataCadastro";

        List<ListaCompraDTO> lista = new ArrayList<>();
        ResultSet result = findByQuery(sql);
        while (result.next()) {
            lista.add(new ListaCompraDTO(extract(result), result.getBigDecimal("valorTotal")));
        }
        return lista;
    }

}
