package dominio;

import dto.RSAutenticacionUsuario;
import java.util.List;
import java.util.UUID;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Usuario;
import pojo.Paciente;
import pojo.Medico;
import utilidades.Utilidades;
import utilidades.Constantes;

public class AutenticacionImp {

    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                usuarios = conexionBD.selectList("usuario.listar");
            } finally {
                conexionBD.close();
            }
        }
        return usuarios;
    }

    public static Usuario obtenerUsuario(Integer idUsuario) {
        Usuario usuario = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                usuario = conexionBD.selectOne("usuario.obtenerPorId", idUsuario);
            } finally {
                conexionBD.close();
            }
        }
        return usuario;
    }

    public static Usuario guardarUsuario(Usuario usuario) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                // Hash password before saving
                if (usuario.getPassword() != null) {
                    usuario.setPassword(Utilidades.hashPassword(usuario.getPassword()));
                }
                conexionBD.insert("usuario.insertar", usuario);
                conexionBD.commit();
                return usuario;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Usuario actualizarUsuario(Integer idUsuario, Usuario usuario) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                usuario.setIdUsuario(idUsuario);
                if (usuario.getPassword() != null) {
                    usuario.setPassword(Utilidades.hashPassword(usuario.getPassword()));
                }
                int filas = conexionBD.update("usuario.actualizar", usuario);
                conexionBD.commit();
                return filas > 0 ? usuario : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Usuario eliminarUsuario(Integer idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("usuario.eliminar", idUsuario);
                conexionBD.commit();
                return usuario;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static RSAutenticacionUsuario autenticarUsuario(Usuario credenciales) {
        RSAutenticacionUsuario respuesta = new RSAutenticacionUsuario();
        respuesta.setError(true);

        if (credenciales == null || (credenciales.getIdUsuario() == null && credenciales.getLogin() == null) || credenciales.getPassword() == null) {
            respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
            return respuesta;
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                // Resolve login (email/username) to idUsuario if provided
                if (credenciales.getIdUsuario() == null && credenciales.getLogin() != null) {
                    String login = credenciales.getLogin();
                    // try medico by email
                    Medico medico = conexionBD.selectOne("medico.obtenerPorEmail", login);
                    if (medico != null) {
                        credenciales.setIdUsuario(medico.getIdUsuario());
                    } else {
                        // try paciente by email
                        Paciente paciente = conexionBD.selectOne("paciente.obtenerPorEmail", login);
                        if (paciente != null) {
                            credenciales.setIdUsuario(paciente.getIdUsuario());
                        } else {
                            // if login looks numeric, try parse as id
                            try {
                                credenciales.setIdUsuario(Integer.parseInt(login));
                            } catch (NumberFormatException ex) {
                                respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
                                return respuesta;
                            }
                        }
                    }
                }

                // Hash incoming password before comparing to DB (DB stores hashed values)
                String hashed = Utilidades.hashPassword(credenciales.getPassword());
                credenciales.setPassword(hashed);

                Usuario usuario = conexionBD.selectOne("usuario.autenticar", credenciales);
                if (usuario == null) {
                    respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
                    return respuesta;
                }
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
                Usuario usuario = obtenerUsuario(paciente.getIdUsuario());
                if (usuario == null || usuario.getEstatus() == null || !usuario.getEstatus()) {
                    respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
                    return respuesta;
                }
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
