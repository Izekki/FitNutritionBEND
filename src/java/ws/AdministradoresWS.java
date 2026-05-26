package ws;

import dominio.AdministradorImp;
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
import pojo.Administrador;
import utilidades.Constantes;

@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradoresWS {

    @GET
    public List<Administrador> listarAdministradores() {
        return AdministradorImp.listarAdministradores();
    }

    @GET
    @Path("{idAdministrador}")
    public Response obtenerAdministrador(@PathParam("idAdministrador") Integer idAdministrador) {
        if (idAdministrador == null || idAdministrador <= 0) {
            throw new BadRequestException("ID de administrador requerido");
        }
        Administrador administrador = AdministradorImp.obtenerAdministrador(idAdministrador);
        if (administrador == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(administrador).build();
    }

    @POST
    public Respuesta guardarAdministrador(Administrador administrador) {
        if (administrador == null) {
            throw new BadRequestException("Administrador requerido");
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, AdministradorImp.guardarAdministrador(administrador));
    }

    @PUT
    @Path("{idAdministrador}")
    public Respuesta actualizarAdministrador(@PathParam("idAdministrador") Integer idAdministrador,
            Administrador administrador) {
        if (idAdministrador == null || idAdministrador <= 0 || administrador == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Administrador actualizado = AdministradorImp.actualizarAdministrador(idAdministrador, administrador);
        if (actualizado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @DELETE
    @Path("{idAdministrador}")
    public Respuesta eliminarAdministrador(@PathParam("idAdministrador") Integer idAdministrador) {
        if (idAdministrador == null || idAdministrador <= 0) {
            throw new BadRequestException("ID de administrador requerido");
        }
        Administrador eliminado = AdministradorImp.eliminarAdministrador(idAdministrador);
        if (eliminado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminado);
    }
}