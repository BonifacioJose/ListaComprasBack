package br.com.listacomprasback.rest;

import br.com.listacomprasback.genericdao.dao.ListaCompraDao;
import br.com.listacomprasback.genericdao.dao.ListaCompraProdutoDao;
import br.com.listacomprasback.genericdao.dto.ListaCompraDTO;
import br.com.listacomprasback.genericdao.dto.ListaCompraProdutoDTO;
import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.entity.ListaCompraProduto;
import br.com.listacomprasback.genericdao.util.DatabaseUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Bonifacio
 */
@ApplicationPath("/rest")
@Path("listacompra")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListaCompraRest extends Application {

    @POST
    public Response salvar(ListaCompra listaCompra) {
        ListaCompraDao listaCompraDao = new ListaCompraDao(DatabaseUtil.getConnection());
        ListaCompraProdutoDao listaCompraProdutoDao = new ListaCompraProdutoDao(DatabaseUtil.getConnection());
        try {
            if (listaCompra.isNew()) {
                ListaCompra listaCompraBanco = listaCompraDao.saveWithReturn(listaCompra);
                listaCompra.setId(listaCompraBanco.getId());
                for (ListaCompraProdutoDTO produto : listaCompra.getListaProdutos()) {
                    ListaCompraProduto novoProduto = new ListaCompraProduto();
                    novoProduto.setIdListaCompra(listaCompraBanco.getId());
                    novoProduto.setIdProduto(produto.getIdProduto());
                    novoProduto.setQuantidade(produto.getQuantidade());
                    novoProduto.setValorProduto(produto.getValorAtualizado());
                    listaCompraProdutoDao.save(novoProduto);
                }
            } else {
                listaCompraDao.save(listaCompra);
                listaCompraProdutoDao.excluirTodosPorListaCompra(listaCompra);
                for (ListaCompraProdutoDTO produto : listaCompra.getListaProdutos()) {
                    ListaCompraProduto novoProduto = new ListaCompraProduto();
                    novoProduto.setIdListaCompra(listaCompra.getId());
                    novoProduto.setIdProduto(produto.getIdProduto());
                    novoProduto.setQuantidade(produto.getQuantidade());
                    novoProduto.setValorProduto(produto.getValorAtualizado());
                    listaCompraProdutoDao.save(novoProduto);
                }
            }
            return Response.ok(listaCompra).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        } finally {
            listaCompraDao.closeConnections();
            listaCompraProdutoDao.closeConnections();
        }
    }

    @GET
    public Response getListasCompras() {
        ListaCompraDao listaCompraDao = new ListaCompraDao(DatabaseUtil.getConnection());
        try {
            return Response.ok(listaCompraDao.buscarTodasComValorTotal()).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        } finally {
            listaCompraDao.closeConnections();
        }
    }

    @GET
    @Path("/{id}")
    public Response getListaCompra(
            @PathParam(value = "id") Long id) {
        ListaCompraDao listaCompraDao = new ListaCompraDao(DatabaseUtil.getConnection());
        try {
            return Response.ok(listaCompraDao.buscarPorPorIdComProdutos(id)).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        } finally {
            listaCompraDao.closeConnections();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteListaCompra(
            @PathParam(value = "id") Long id) {
        ListaCompraDao listaCompraDao = new ListaCompraDao(DatabaseUtil.getConnection());
        try {
            ListaCompra listaCompra = new ListaCompra();
            listaCompra.setId(id);
            return Response.ok(listaCompraDao.delete(listaCompra)).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        } finally {
            listaCompraDao.closeConnections();
        }
    }
}
