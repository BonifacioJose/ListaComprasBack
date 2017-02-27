package br.com.listacomprasback.rest;

import br.com.listacomprasback.genericdao.dao.ProdutoDao;
import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.util.DatabaseUtil;
import java.sql.SQLException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Bonifácio
 */
@ApplicationPath("/rest")
@Path("listacompraproduto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListaCompraProdutoRest extends Application {

    @GET
    @Path("/{idListaCompra}")
    public Response getProdutosNaoSelecionadosListaCompra(
            @PathParam(value = "idListaCompra") Long id) {
        ProdutoDao produtoDao = new ProdutoDao(DatabaseUtil.getConnection());
        try {
            ListaCompra listaCompra = new ListaCompra();
            listaCompra.setId(id);
            return Response.ok(produtoDao.buscarProdutosNaoSelecionadosPorListaCompra(listaCompra)).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        } finally {
            produtoDao.closeConnections();
        }
    }
}
