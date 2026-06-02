package dominio;

import dto.RSAutenticacionUsuario;
import java.util.UUID;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Usuario;
import pojo.Paciente;
import pojo.Medico;
import pojo.Administrador;
import utilidades.Utilidades;
import utilidades.Constantes;

public class AutenticacionImp {

    public static RSAutenticacionUsuario autenticarUsuario(Usuario credenciales) {
        RSAutenticacionUsuario respuesta = new RSAutenticacionUsuario();
        respuesta.setError(true);

        if (credenciales == null || credenciales.getLogin() == null || credenciales.getPassword() == null) {
            respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
            return respuesta;
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                String login = credenciales.getLogin();
                String password = credenciales.getPassword();

                // 1. try medico by email
                Medico medico = conexionBD.selectOne("medico.obtenerPorEmail", login);
                if (medico != null) {
                    String hashed = Utilidades.hashPassword(password);
                    if (hashed != null && hashed.equals(medico.getContrasena())) {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(medico.getIdMedico());
                        usuario.setLogin(medico.getEmail());
                        usuario.setRol("Medico");
                        usuario.setEstatus(true);

                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.MSJ_ACCESO_CONCEDIDO);
                        respuesta.setToken(UUID.randomUUID().toString());
                        respuesta.setUsuario(usuario);
                        return respuesta;
                    }
                }

                // 2. try administrador by email
                Administrador admin = conexionBD.selectOne("administrador.obtenerPorEmail", login);
                if (admin != null) {
                    String hashed = Utilidades.hashPassword(password);
                    if (hashed != null && hashed.equals(admin.getContrasena())) {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(admin.getIdAdministrador());
                        usuario.setLogin(admin.getEmail());
                        usuario.setRol("Administrador");
                        usuario.setEstatus(true);

                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.MSJ_ACCESO_CONCEDIDO);
                        respuesta.setToken(UUID.randomUUID().toString());
                        respuesta.setUsuario(usuario);
                        return respuesta;
                    }
                }

                // 3. try paciente by email (web login)
                Paciente paciente = conexionBD.selectOne("paciente.obtenerPorEmail", login);
                if (paciente != null) {
                    if (password.equals(paciente.getCodigoAcceso())) {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(paciente.getIdPaciente());
                        usuario.setLogin(paciente.getEmail());
                        usuario.setRol("Paciente");
                        usuario.setEstatus(true);

                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.MSJ_ACCESO_CONCEDIDO);
                        respuesta.setToken(UUID.randomUUID().toString());
                        respuesta.setUsuario(usuario);
                        return respuesta;
                    }
                }

                respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
                return respuesta;
            } finally {
                conexionBD.close();
            }
        }

        respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        return respuesta;
    }

    public static RSAutenticacionUsuario autenticarPacienteMovil(String codigoAcceso) {
        RSAutenticacionUsuario respuesta = new RSAutenticacionUsuario();
        respuesta.setError(true);

        if (codigoAcceso == null || codigoAcceso.trim().isEmpty()) {
            respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
            return respuesta;
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                Paciente paciente = conexionBD.selectOne("paciente.obtenerPorCodigoAcceso", codigoAcceso);
                if (paciente == null) {
                    respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
                    return respuesta;
                }
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(paciente.getIdPaciente());
                usuario.setLogin(paciente.getEmail());
                usuario.setRol("Paciente");
                usuario.setEstatus(true);

                respuesta.setError(false);
                respuesta.setMensaje(Constantes.MSJ_ACCESO_CONCEDIDO);
                respuesta.setToken(UUID.randomUUID().toString());
                respuesta.setUsuario(usuario);
                return respuesta;
            } finally {
                conexionBD.close();
            }
        }

        respuesta.setMensaje(Constantes.MSJ_ERROR_BD);
        return respuesta;
    }
}
