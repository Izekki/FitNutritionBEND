package ws;

import dominio.PacienteImp;
import dto.Respuesta;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Paciente;
import utilidades.Constantes;

@Path("pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacientesWS {

    @GET
    public List<Paciente> listarPacientes() {
        return PacienteImp.listarPacientes();
    }

    @GET
    @Path("{idPaciente}")
    public Response obtenerPaciente(@PathParam("idPaciente") Integer idPaciente) {
        if (idPaciente == null || idPaciente <= 0) {
            throw new BadRequestException("ID de paciente requerido");
        }
        Paciente paciente = PacienteImp.obtenerPaciente(idPaciente);
        if (paciente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(paciente).build();
    }

    @POST
    public Respuesta guardarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new BadRequestException("Paciente requerido");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, PacienteImp.guardarPaciente(paciente));
    }

    @PUT
    @Path("{idPaciente}")
    public Respuesta actualizarPaciente(@PathParam("idPaciente") Integer idPaciente, Paciente paciente) {
        if (idPaciente == null || idPaciente <= 0 || paciente == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Paciente actualizado = PacienteImp.actualizarPaciente(idPaciente, paciente);
        if (actualizado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @DELETE
    @Path("{idPaciente}")
    public Respuesta eliminarPaciente(@PathParam("idPaciente") Integer idPaciente) {
        if (idPaciente == null || idPaciente <= 0) {
            throw new BadRequestException("ID de paciente requerido");
        }
        Paciente eliminado = PacienteImp.eliminarPaciente(idPaciente);
        if (eliminado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminado);
    }

    @GET
    @Path("buscar")
    public List<Paciente> buscarPacientes(
            @QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("idMedico") Integer idMedico,
            @QueryParam("estatus") String estatus) {
        return PacienteImp.buscarPacientes(nombre, email, idMedico, estatus);
    }

    @PUT
    @Path("{idPaciente}/perfil-movil")
    public Respuesta actualizarPerfilMovil(@PathParam("idPaciente") Integer idPaciente, Paciente paciente) {
        if (idPaciente == null || idPaciente <= 0 || paciente == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Paciente actual = PacienteImp.obtenerPaciente(idPaciente);
        if (actual == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        paciente.setIdMedico(actual.getIdMedico());
        paciente.setEstatus(actual.getEstatus());
        // El código de acceso no debe ser modificado por este endpoint
        paciente.setCodigoAcceso(actual.getCodigoAcceso());
        Paciente actualizado = PacienteImp.actualizarPaciente(idPaciente, paciente);
        if (actualizado == null) {
            return new Respuesta(true, "Error al actualizar el perfil móvil", null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @PUT
    @Path("{idPaciente}/codigo-acceso")
    public Respuesta actualizarCodigoAcceso(@PathParam("idPaciente") Integer idPaciente, dto.PeticionCodigoAcceso payload) {
        if (idPaciente == null || idPaciente <= 0 || payload == null || payload.getCodigoAcceso() == null || payload.getCodigoAcceso().trim().isEmpty()) {
            throw new BadRequestException("Datos inválidos");
        }
        String nuevoCodigo = payload.getCodigoAcceso().trim();
        Paciente actual = PacienteImp.obtenerPaciente(idPaciente);
        if (actual == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        
        actual.setCodigoAcceso(nuevoCodigo);
        Paciente actualizado = PacienteImp.actualizarPaciente(idPaciente, actual);
        if (actualizado == null) {
            return new Respuesta(true, "Error al actualizar el código de acceso", null);
        }
        return new Respuesta(false, "Código de acceso actualizado exitosamente", actualizado);
    }
}