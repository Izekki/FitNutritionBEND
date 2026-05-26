package ws;

import dominio.DietaAlimentoImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.DietaAlimento;
import utilidades.Constantes;

@Path("dieta-alimentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DietaAlimentosWS {

    @GET
    public List<DietaAlimento> listarRelaciones() {
        return DietaAlimentoImp.listarDietasAlimentos();
    }

    @GET
    @Path("{idDietaAlimento}")
    public Response obtenerRelacion(@PathParam("idDietaAlimento") Integer idDietaAlimento) {
        if (idDietaAlimento == null || idDietaAlimento <= 0) {
            throw new BadRequestException("ID de relación requerido");
        }
        DietaAlimento relacion = DietaAlimentoImp.obtenerDietaAlimento(idDietaAlimento);
        if (relacion == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(relacion).build();
    }

    @GET
    @Path("dieta/{idDieta}")
    public List<DietaAlimento> listarPorDieta(@PathParam("idDieta") Integer idDieta) {
        if (idDieta == null || idDieta <= 0) {
            throw new BadRequestException("ID de dieta requerido");
        }
        return DietaAlimentoImp.listarPorDieta(idDieta);
    }

    @GET
    @Path("alimento/{idAlimento}")
    public List<DietaAlimento> listarPorAlimento(@PathParam("idAlimento") Integer idAlimento) {
        if (idAlimento == null || idAlimento <= 0) {
            throw new BadRequestException("ID de alimento requerido");
        }
        return DietaAlimentoImp.listarPorAlimento(idAlimento);
    }

    @POST
    public Respuesta guardarRelacion(DietaAlimento dietaAlimento) {
        if (dietaAlimento == null) {
            throw new BadRequestException("Relación requerida");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, DietaAlimentoImp.guardarDietaAlimento(dietaAlimento));
    }

    @PUT
    @Path("{idDietaAlimento}")
    public Respuesta actualizarRelacion(@PathParam("idDietaAlimento") Integer idDietaAlimento,
            DietaAlimento dietaAlimento) {
        if (idDietaAlimento == null || idDietaAlimento <= 0 || dietaAlimento == null) {
            throw new BadRequestException("Datos inválidos");
        }
        DietaAlimento actualizada = DietaAlimentoImp.actualizarDietaAlimento(idDietaAlimento, dietaAlimento);
        if (actualizada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizada);
    }

    @DELETE
    @Path("{idDietaAlimento}")
    public Respuesta eliminarRelacion(@PathParam("idDietaAlimento") Integer idDietaAlimento) {
        if (idDietaAlimento == null || idDietaAlimento <= 0) {
            throw new BadRequestException("ID de relación requerido");
        }
        DietaAlimento eliminada = DietaAlimentoImp.eliminarDietaAlimento(idDietaAlimento);
        if (eliminada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminada);
    }
}