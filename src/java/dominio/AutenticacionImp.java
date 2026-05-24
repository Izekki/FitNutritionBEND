package dominio;

import dto.RSAutenticacionColaborador;
import java.util.List;
import java.util.UUID;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Usuario;
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

    public static RSAutenticacionColaborador autenticarUsuario(Usuario credenciales) {
        RSAutenticacionColaborador respuesta = new RSAutenticacionColaborador();
        respuesta.setError(true);

        if (credenciales == null || credenciales.getIdUsuario() == null || credenciales.getPassword() == null) {
            respuesta.setMensaje(Constantes.MSJ_CREDENCIALES_INVALIDAS);
            return respuesta;
        }

        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
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
}
