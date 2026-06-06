package ws;

import dominio.MedicoImp;
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
import pojo.Medico;
import javax.ws.rs.QueryParam;
import utilidades.Constantes;

@Path("medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicosWS {

    @GET
    public List<Medico> listarMedicos() {
        return MedicoImp.listarMedicos();
    }

    @GET
    @Path("{idMedico}")
    public Response obtenerMedico(@PathParam("idMedico") Integer idMedico) {
        if (idMedico == null || idMedico <= 0) {
            throw new BadRequestException("ID de médico requerido");
        }
        Medico medico = MedicoImp.obtenerMedico(idMedico);
        if (medico == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(medico).build();
    }

    @POST
    public Respuesta guardarMedico(Medico medico) {
        if (medico == null) {
            throw new BadRequestException("Médico requerido");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, MedicoImp.guardarMedico(medico));
    }

    @PUT
    @Path("{idMedico}")
    public Respuesta actualizarMedico(@PathParam("idMedico") Integer idMedico, Medico medico) {
        if (idMedico == null || idMedico <= 0 || medico == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Medico actualizado = MedicoImp.actualizarMedico(idMedico, medico);
        if (actualizado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @DELETE
    @Path("{idMedico}")
    public Respuesta eliminarMedico(@PathParam("idMedico") Integer idMedico) {
        if (idMedico == null || idMedico <= 0) {
            throw new BadRequestException("ID de médico requerido");
        }
        try {
            Medico eliminado = MedicoImp.eliminarMedico(idMedico);
            if (eliminado == null) {
                return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
            }
            return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminado);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (e.getCause() != null && e.getCause().getMessage() != null) {
                msg = e.getCause().getMessage();
            }
            return new Respuesta(true, msg != null ? msg : Constantes.MSJ_ERROR_BD, null);
        }
    }

    @POST
    @Path("{idMedicoOrigen}/reasignar-pacientes/{idMedicoDestino}")
    public Respuesta reasignarPacientes(
            @PathParam("idMedicoOrigen") Integer idMedicoOrigen,
            @PathParam("idMedicoDestino") Integer idMedicoDestino) {
        if (idMedicoOrigen == null || idMedicoOrigen <= 0 || idMedicoDestino == null || idMedicoDestino <= 0) {
            throw new BadRequestException("IDs de médicos inválidos");
        }
        return MedicoImp.reasignarPacientes(idMedicoOrigen, idMedicoDestino);
    }

    @GET
    @Path("buscar")
    public List<Medico> buscarMedicos(
            @QueryParam("nombre") String nombre,
            @QueryParam("numPersonal") Integer numPersonal,
            @QueryParam("cedulaProfesional") String cedulaProfesional,
            @QueryParam("estatus") String estatus) {
        return MedicoImp.buscarMedicos(nombre, numPersonal, cedulaProfesional, estatus);
    }
}