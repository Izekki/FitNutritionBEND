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
        paciente.setCodigoAcceso(null);
        return Response.ok(paciente).build();
    }

    @POST
    public Respuesta guardarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new BadRequestException("Paciente requerido");
        }
        Paciente guardado = PacienteImp.guardarPaciente(paciente);
        if (guardado != null) {
            guardado.setCodigoAcceso(null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, guardado);
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
        actualizado.setCodigoAcceso(null);
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
        eliminado.setCodigoAcceso(null);
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
        // Mezclar únicamente los campos editables que no sean nulos o vacíos en el registro existente
        if (paciente.getNombrePaciente() != null && !paciente.getNombrePaciente().trim().isEmpty()) {
            actual.setNombrePaciente(paciente.getNombrePaciente().trim());
        }
        if (paciente.getApellidosPaciente() != null && !paciente.getApellidosPaciente().trim().isEmpty()) {
            actual.setApellidosPaciente(paciente.getApellidosPaciente().trim());
        }
        if (paciente.getEmail() != null && !paciente.getEmail().trim().isEmpty()) {
            actual.setEmail(paciente.getEmail().trim());
        }
        if (paciente.getTelefono() != null && !paciente.getTelefono().trim().isEmpty()) {
            actual.setTelefono(paciente.getTelefono().trim());
        }
        if (paciente.getDomicilio() != null && !paciente.getDomicilio().trim().isEmpty()) {
            actual.setDomicilio(paciente.getDomicilio().trim());
        }
        Paciente actualizado = PacienteImp.actualizarPaciente(idPaciente, actual);
        if (actualizado == null) {
            return new Respuesta(true, "Error al actualizar el perfil móvil", null);
        }
        actualizado.setCodigoAcceso(null);
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @PUT
    @Path("{idPaciente}/codigo-acceso")
    public Respuesta actualizarCodigoAcceso(@PathParam("idPaciente") Integer idPaciente, dto.PeticionCodigoAcceso payload) {
        if (idPaciente == null || idPaciente <= 0 || payload == null 
                || payload.getCodigoActual() == null || payload.getCodigoActual().trim().isEmpty()
                || payload.getCodigoAcceso() == null || payload.getCodigoAcceso().trim().isEmpty()) {
            throw new BadRequestException("Datos inválidos");
        }
        String codigoActual = payload.getCodigoActual().trim();
        String nuevoCodigo = payload.getCodigoAcceso().trim();
        
        if (!nuevoCodigo.matches("^\\d{1,4}$")) {
            throw new BadRequestException("El código de acceso debe ser puramente numérico y tener un máximo de 4 dígitos.");
        }
        
        Paciente actual = PacienteImp.obtenerPaciente(idPaciente);
        if (actual == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        
        // Validación del código de acceso actual
        if (actual.getCodigoAcceso() == null || !actual.getCodigoAcceso().equals(codigoActual)) {
            return new Respuesta(true, "El código de acceso actual es incorrecto", null);
        }
        
        actual.setCodigoAcceso(nuevoCodigo);
        Paciente actualizado = PacienteImp.actualizarPaciente(idPaciente, actual);
        if (actualizado == null) {
            return new Respuesta(true, "Error al actualizar el código de acceso", null);
        }
        actualizado.setCodigoAcceso(null);
        return new Respuesta(false, "Código de acceso actualizado exitosamente", actualizado);
    }
}