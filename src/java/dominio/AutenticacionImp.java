package dominio;

import dto.RSAutenticacionUsuario;
import dto.Respuesta;
import dto.PeticionCambiarContrasena;
import java.util.HashMap;
import java.util.Map;
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

    public static RSAutenticacionUsuario autenticarPacienteMovil(String email, String codigoAcceso) {
        RSAutenticacionUsuario respuesta = new RSAutenticacionUsuario();
        respuesta.setError(true);

        if (email == null || email.trim().isEmpty() || codigoAcceso == null || codigoAcceso.trim().isEmpty()) {
            respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
            return respuesta;
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                java.util.Map<String, Object> parametros = new java.util.HashMap<>();
                parametros.put("email", email);
                parametros.put("codigoAcceso", codigoAcceso);

                Paciente paciente = conexionBD.selectOne("paciente.obtenerPorEmailYCodigoAcceso", parametros);
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

    public static Respuesta cambiarContrasena(PeticionCambiarContrasena peticion) {
        if (peticion == null || peticion.getId() == null || peticion.getRol() == null 
                || peticion.getContrasenaActual() == null || peticion.getContrasenaNueva() == null) {
            return new Respuesta(true, "Datos requeridos faltantes para cambiar la contraseña");
        }

        String rol = peticion.getRol().trim().toLowerCase();
        int id = peticion.getId();
        String actual = peticion.getContrasenaActual();
        String nueva = peticion.getContrasenaNueva();

        if (actual.trim().isEmpty() || nueva.trim().isEmpty()) {
            return new Respuesta(true, "Las contraseñas no pueden estar vacías");
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD == null) {
            return new Respuesta(true, Constantes.MSJ_ERROR_BD);
        }

        try {
            if ("medico".equals(rol)) {
                Medico medicoId = conexionBD.selectOne("medico.obtenerPorId", id);
                if (medicoId == null) {
                    return new Respuesta(true, "Médico no encontrado");
                }
                Medico medicoCompleto = conexionBD.selectOne("medico.obtenerPorEmail", medicoId.getEmail());
                if (medicoCompleto == null) {
                    return new Respuesta(true, "Error al recuperar datos del médico");
                }

                String hashedActual = Utilidades.hashPassword(actual);
                if (hashedActual == null || !hashedActual.equals(medicoCompleto.getContrasena())) {
                    return new Respuesta(true, "La contraseña actual es incorrecta");
                }

                String hashedNueva = Utilidades.hashPassword(nueva);
                Map<String, Object> params = new HashMap<>();
                params.put("idMedico", id);
                params.put("contrasena", hashedNueva);

                int filas = conexionBD.update("medico.actualizarContrasena", params);
                conexionBD.commit();
                if (filas > 0) {
                    return new Respuesta(false, "Contraseña cambiada exitosamente");
                }
            } else if ("administrador".equals(rol)) {
                Administrador adminId = conexionBD.selectOne("administrador.obtenerPorId", id);
                if (adminId == null) {
                    return new Respuesta(true, "Administrador no encontrado");
                }
                Administrador adminCompleto = conexionBD.selectOne("administrador.obtenerPorEmail", adminId.getEmail());
                if (adminCompleto == null) {
                    return new Respuesta(true, "Error al recuperar datos del administrador");
                }

                String hashedActual = Utilidades.hashPassword(actual);
                if (hashedActual == null || !hashedActual.equals(adminCompleto.getContrasena())) {
                    return new Respuesta(true, "La contraseña actual es incorrecta");
                }

                String hashedNueva = Utilidades.hashPassword(nueva);
                Map<String, Object> params = new HashMap<>();
                params.put("idAdministrador", id);
                params.put("contrasena", hashedNueva);

                int filas = conexionBD.update("administrador.actualizarContrasena", params);
                conexionBD.commit();
                if (filas > 0) {
                    return new Respuesta(false, "Contraseña cambiada exitosamente");
                }
            } else if ("paciente".equals(rol)) {
                Paciente pacienteId = conexionBD.selectOne("paciente.obtenerPorId", id);
                if (pacienteId == null) {
                    return new Respuesta(true, "Paciente no encontrado");
                }
                Paciente pacienteCompleto = conexionBD.selectOne("paciente.obtenerPorEmail", pacienteId.getEmail());
                if (pacienteCompleto == null) {
                    return new Respuesta(true, "Error al recuperar datos del paciente");
                }

                if (!actual.equals(pacienteCompleto.getCodigoAcceso())) {
                    return new Respuesta(true, "El código de acceso actual es incorrecto");
                }

                Map<String, Object> params = new HashMap<>();
                params.put("idPaciente", id);
                params.put("codigoAcceso", nueva);

                int filas = conexionBD.update("paciente.actualizarContrasena", params);
                conexionBD.commit();
                if (filas > 0) {
                    return new Respuesta(false, "Código de acceso cambiado exitosamente");
                }
            } else {
                return new Respuesta(true, "Rol no soportado: " + rol);
            }
        } catch (Exception e) {
            conexionBD.rollback();
            return new Respuesta(true, "Error al actualizar la contraseña: " + e.getMessage());
        } finally {
            conexionBD.close();
        }

        return new Respuesta(true, "No se pudo actualizar la contraseña");
    }
}
