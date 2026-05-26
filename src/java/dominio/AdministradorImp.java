package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Administrador;

public class AdministradorImp {

    public static List<Administrador> listarAdministradores() {
        List<Administrador> administradores = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                administradores = conexionBD.selectList("administrador.listar");
            } finally {
                conexionBD.close();
            }
        }
        return administradores;
    }

    public static Administrador obtenerAdministrador(Integer idAdministrador) {
        Administrador administrador = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                administrador = conexionBD.selectOne("administrador.obtenerPorId", idAdministrador);
            } finally {
                conexionBD.close();
            }
        }
        return administrador;
    }

    public static Administrador obtenerAdministradorPorUsuario(Integer idUsuario) {
        Administrador administrador = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                administrador = conexionBD.selectOne("administrador.obtenerPorUsuario", idUsuario);
            } finally {
                conexionBD.close();
            }
        }
        return administrador;
    }

    public static Administrador guardarAdministrador(Administrador administrador) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("administrador.insertar", administrador);
                conexionBD.commit();
                return administrador;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Administrador actualizarAdministrador(Integer idAdministrador, Administrador administrador) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                administrador.setIdAdministrador(idAdministrador);
                int filas = conexionBD.update("administrador.actualizar", administrador);
                conexionBD.commit();
                return filas > 0 ? administrador : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Administrador eliminarAdministrador(Integer idAdministrador) {
        Administrador administrador = obtenerAdministrador(idAdministrador);
        if (administrador == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("administrador.eliminar", idAdministrador);
                conexionBD.commit();
                return administrador;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }
}

