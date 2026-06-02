package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Medico;
import utilidades.Utilidades;

public class MedicoImp {

    public static List<Medico> listarMedicos() {
        List<Medico> medicos = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                medicos = conexionBD.selectList("medico.listar");
            } finally {
                conexionBD.close();
            }
        }
        return medicos;
    }

    public static Medico obtenerMedico(Integer idMedico) {
        Medico medico = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                medico = conexionBD.selectOne("medico.obtenerPorId", idMedico);
            } finally {
                conexionBD.close();
            }
        }
        return medico;
    }

    public static Medico guardarMedico(Medico medico) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                if (medico.getContrasena() != null) {
                    medico.setContrasena(Utilidades.hashPassword(medico.getContrasena()));
                }
                conexionBD.insert("medico.insertar", medico);
                conexionBD.commit();
                return medico;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Medico actualizarMedico(Integer idMedico, Medico medico) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                medico.setIdMedico(idMedico);
                if (medico.getContrasena() != null) {
                    medico.setContrasena(Utilidades.hashPassword(medico.getContrasena()));
                }
                int filas = conexionBD.update("medico.actualizar", medico);
                conexionBD.commit();
                return filas > 0 ? medico : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Medico eliminarMedico(Integer idMedico) {
        Medico medico = obtenerMedico(idMedico);
        if (medico == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("medico.eliminar", idMedico);
                conexionBD.commit();
                return medico;
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
