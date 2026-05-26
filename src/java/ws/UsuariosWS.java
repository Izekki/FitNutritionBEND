package ws;

import dominio.AutenticacionImp;
import dto.RSAutenticacionUsuario;
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
import pojo.Usuario;
import pojo.Paciente;
import utilidades.Constantes;

@Path("autenticacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuariosWS {

    @GET
    @Path("usuarios")
    public List<Usuario> listarUsuarios() {
        return AutenticacionImp.listarUsuarios();
    }

    @GET
    @Path("usuarios/{idUsuario}")
    public Response obtenerUsuario(@PathParam("idUsuario") Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new BadRequestException("ID de usuario requerido");
        }
        Usuario usuario = AutenticacionImp.obtenerUsuario(idUsuario);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null)).build();
        }
        return Response.ok(usuario).build();
    }

    @POST
    @Path("usuarios")
    public Respuesta guardarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new BadRequestException("Usuario requerido");
        }
        Usuario guardado = AutenticacionImp.guardarUsuario(usuario);
        return new Respuesta(false, Constantes.MSJ_REGISTRO_GUARDADO, guardado);
    }

    @PUT
    @Path("usuarios/{idUsuario}")
    public Respuesta actualizarUsuario(@PathParam("idUsuario") Integer idUsuario, Usuario usuario) {
        if (idUsuario == null || idUsuario <= 0 || usuario == null) {
            throw new BadRequestException("Datos inválidos");
        }
        Usuario actualizado = AutenticacionImp.actualizarUsuario(idUsuario, usuario);
        if (actualizado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ACTUALIZADO, actualizado);
    }

    @DELETE
    @Path("usuarios/{idUsuario}")
    public Respuesta eliminarUsuario(@PathParam("idUsuario") Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new BadRequestException("ID de usuario requerido");
        }
        Usuario eliminado = AutenticacionImp.eliminarUsuario(idUsuario);
        if (eliminado == null) {
            return new Respuesta(true, Constantes.MSJ_NO_ENCONTRADO, null);
        }
        return new Respuesta(false, Constantes.MSJ_REGISTRO_ELIMINADO, eliminado);
    }

    @POST
    @Path("ingresar")
    public RSAutenticacionUsuario ingresar(Usuario credenciales) {
        if (credenciales == null || (credenciales.getIdUsuario() == null && credenciales.getLogin() == null) || credenciales.getPassword() == null) {
            throw new BadRequestException("Se requieren idUsuario/login y password");
        }
        return AutenticacionImp.autenticarUsuario(credenciales);
    }

    @POST
    @Path("ingresar-movil")
    public RSAutenticacionUsuario ingresarMovil(Paciente credenciales) {
        if (credenciales == null || credenciales.getCodigoAcceso() == null) {
            throw new BadRequestException("Se requiere codigoAcceso");
        }
        return AutenticacionImp.autenticarPacienteMovil(credenciales.getCodigoAcceso());
    }
}