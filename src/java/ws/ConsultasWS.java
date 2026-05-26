package ws;

import dominio.ConsultaImp;
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
import pojo.Consulta;
import utilidades.Constantes;

@Path("consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultasWS {

    @GET
    public List<Consulta> listarConsultas() {
        return ConsultaImp.listarConsultas();
    }

    @GET
    @Path("{idConsulta}")
    public Response obtenerConsulta(@PathParam("idConsulta") Integer idConsulta) {
        if (idConsulta == null || idConsulta <= 0) {
            throw new BadRequestException("ID de consulta requerido");
        }
        Consulta consulta = ConsultaImp.obtenerConsulta(idConsulta);
        if (consulta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(consulta).build();
    }

    @POST
    public Respuesta guardarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new BadRequestException("Consulta requerida");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, ConsultaImp.guardarConsulta(consulta));
    }

    @PUT
    @Path("{idConsulta}")
    public Respuesta actualizarConsulta(@PathParam("idConsulta") Integer idConsulta, Consulta consulta) {
        if (idConsulta == null || idConsulta <= 0 || consulta == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Consulta actualizada = ConsultaImp.actualizarConsulta(idConsulta, consulta);
        if (actualizada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizada);
    }

    @DELETE
    @Path("{idConsulta}")
    public Respuesta eliminarConsulta(@PathParam("idConsulta") Integer idConsulta) {
        if (idConsulta == null || idConsulta <= 0) {
            throw new BadRequestException("ID de consulta requerido");
        }
        Consulta eliminada = ConsultaImp.eliminarConsulta(idConsulta);
        if (eliminada == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminada);
    }
}