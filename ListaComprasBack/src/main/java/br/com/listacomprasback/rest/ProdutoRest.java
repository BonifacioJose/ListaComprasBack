package br.com.listacomprasback.rest;

import br.com.listacomprasback.genericdao.dao.ProdutoDao;
import br.com.listacomprasback.genericdao.dto.ProdutoDTO;
import br.com.listacomprasback.genericdao.entity.ListaCompra;
import br.com.listacomprasback.genericdao.entity.Produto;
import br.com.listacomprasback.genericdao.util.DatabaseUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@Path("produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoRest extends Application {

    @GET
    public Response getProdutos() {
        ProdutoDao produtoDao = new ProdutoDao(DatabaseUtil.getConnection());
        try {
            List<ProdutoDTO> produtosDto = new ArrayList<>();
            for (Produto produto : produtoDao.findAll()) {
                produtosDto.add(new ProdutoDTO(produto));
            }
            return Response.ok(produtosDto).build();
        } catch (SQLException ex) {
            return Response.serverError().build();
        } finally {
            produtoDao.closeConnections();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProduto(
            @PathParam(value = "id") Long id) {
        ProdutoDao produtoDao = new ProdutoDao(DatabaseUtil.getConnection());
        try {
            return Response.ok(produtoDao.findById(id)).build();
        } catch (SQLException ex) {
            return Response.serverError().build();
        } finally {
            produtoDao.closeConnections();
        }
    }
}
