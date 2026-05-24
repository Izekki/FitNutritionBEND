package ws;

import dominio.CatalogoImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Alimento;
import pojo.Dieta;
import utilidades.Constantes;

@Path("catalogos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogosNutricionalesWS {

    @GET
    @Path("dietas")
    public List<Dieta> listarDietas() {
        return CatalogoImp.listarDietas();
    }

    @GET
    @Path("dietas/{idDieta}")
    public Response obtenerDieta(@PathParam("idDieta") Integer idDieta) {
        if (idDieta == null || idDieta <= 0) {
            throw new BadRequestException("ID de dieta requerido");
        }
        Dieta dieta = CatalogoImp.obtenerDieta(idDieta);
        if (dieta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(dieta).build();
    }

    @POST
    @Path("dietas")
    public Respuesta guardarDieta(Dieta dieta) {
        if (dieta == null) {
            throw new BadRequestException("Dieta requerida");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, CatalogoImp.guardarDieta(dieta));
    }

    @PUT
    @Path("dietas/{idDieta}")
    public Respuesta actualizarDieta(@PathParam("idDieta") Integer idDieta, Dieta dieta) {
        if (idDieta == null || idDieta <= 0 || dieta == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Dieta actualizada = CatalogoImp.actualizarDieta(idDieta, dieta);
        if (actualizada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizada);
    }

    @DELETE
    @Path("dietas/{idDieta}")
    public Respuesta eliminarDieta(@PathParam("idDieta") Integer idDieta) {
        if (idDieta == null || idDieta <= 0) {
            throw new BadRequestException("ID de dieta requerido");
        }
        Dieta eliminada = CatalogoImp.eliminarDieta(idDieta);
        if (eliminada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminada);
    }

    @GET
    @Path("alimentos")
    public List<Alimento> listarAlimentos() {
        return CatalogoImp.listarAlimentos();
    }

    @GET
    @Path("alimentos/{idAlimento}")
    public Response obtenerAlimento(@PathParam("idAlimento") Integer idAlimento) {
        if (idAlimento == null || idAlimento <= 0) {
            throw new BadRequestException("ID de alimento requerido");
        }
        Alimento alimento = CatalogoImp.obtenerAlimento(idAlimento);
        if (alimento == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(alimento).build();
    }

    @POST
    @Path("alimentos")
    public Respuesta guardarAlimento(Alimento alimento) {
        if (alimento == null) {
            throw new BadRequestException("Alimento requerido");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, CatalogoImp.guardarAlimento(alimento));
    }

    @PUT
    @Path("alimentos/{idAlimento}")
    public Respuesta actualizarAlimento(@PathParam("idAlimento") Integer idAlimento, Alimento alimento) {
        if (idAlimento == null || idAlimento <= 0 || alimento == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Alimento actualizado = CatalogoImp.actualizarAlimento(idAlimento, alimento);
        if (actualizado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @DELETE
    @Path("alimentos/{idAlimento}")
    public Respuesta eliminarAlimento(@PathParam("idAlimento") Integer idAlimento) {
        if (idAlimento == null || idAlimento <= 0) {
            throw new BadRequestException("ID de alimento requerido");
        }
        Alimento eliminado = CatalogoImp.eliminarAlimento(idAlimento);
        if (eliminado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminado);
    }
}