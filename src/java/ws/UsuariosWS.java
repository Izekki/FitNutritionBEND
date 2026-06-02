package ws;

import dominio.AutenticacionImp;
import dto.RSAutenticacionUsuario;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Usuario;
import pojo.Paciente;

@Path("autenticacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuariosWS {

    @POST
    @Path("ingresar")
    public RSAutenticacionUsuario ingresar(Usuario credenciales) {
        if (credenciales == null || credenciales.getLogin() == null || credenciales.getPassword() == null) {
            throw new BadRequestException("Se requieren login y password");
        }
        return AutenticacionImp.autenticarUsuario(credenciales);
    }

    @POST
    @Path("ingresar-movil")
    public RSAutenticacionUsuario ingresarMovil(Paciente credenciales) {
        if (credenciales == null || credenciales.getEmail() == null || credenciales.getCodigoAcceso() == null) {
            throw new BadRequestException("Se requieren email y codigoAcceso");
        }
        return AutenticacionImp.autenticarPacienteMovil(credenciales.getEmail(), credenciales.getCodigoAcceso());
    }
}