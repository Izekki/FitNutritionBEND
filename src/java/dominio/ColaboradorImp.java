package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Medico;

public class ColaboradorImp {

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

    public static Medico obtenerMedicoPorUsuario(Integer idUsuario) {
        Medico medico = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                medico = conexionBD.selectOne("medico.obtenerPorUsuario", idUsuario);
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
