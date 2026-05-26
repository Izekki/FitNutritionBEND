package ws;

import dominio.CitaImp;
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
import pojo.Cita;
import utilidades.Constantes;

@Path("citas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitasWS {

    @GET
    public List<Cita> listarCitas() {
        return CitaImp.listarCitas();
    }

    @GET
    @Path("{idCita}")
    public Response obtenerCita(@PathParam("idCita") Integer idCita) {
        if (idCita == null || idCita <= 0) {
            throw new BadRequestException("ID de cita requerido");
        }
        Cita cita = CitaImp.obtenerCita(idCita);
        if (cita == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(cita).build();
    }

    @POST
    public Respuesta guardarCita(Cita cita) {
        if (cita == null) {
            throw new BadRequestException("Cita requerida");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, CitaImp.guardarCita(cita));
    }

    @PUT
    @Path("{idCita}")
    public Respuesta actualizarCita(@PathParam("idCita") Integer idCita, Cita cita) {
        if (idCita == null || idCita <= 0 || cita == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Cita actualizada = CitaImp.actualizarCita(idCita, cita);
        if (actualizada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizada);
    }

    @DELETE
    @Path("{idCita}")
    public Respuesta eliminarCita(@PathParam("idCita") Integer idCita) {
        if (idCita == null || idCita <= 0) {
            throw new BadRequestException("ID de cita requerido");
        }
        Cita eliminada = CitaImp.eliminarCita(idCita);
        if (eliminada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminada);
    }
}