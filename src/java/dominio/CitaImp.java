package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cita;

public class CitaImp {

    public static List<Cita> listarCitas() {
        List<Cita> citas = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                citas = conexionBD.selectList("cita.listar");
            } finally {
                conexionBD.close();
            }
        }
        return citas;
    }

    public static Cita obtenerCita(Integer idCita) {
        Cita cita = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                cita = conexionBD.selectOne("cita.obtenerPorId", idCita);
            } finally {
                conexionBD.close();
            }
        }
        return cita;
    }

    public static Cita guardarCita(Cita cita) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("cita.insertar", cita);
                conexionBD.commit();
                return cita;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Cita actualizarCita(Integer idCita, Cita cita) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                cita.setIdCita(idCita);
                int filas = conexionBD.update("cita.actualizar", cita);
                conexionBD.commit();
                return filas > 0 ? cita : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Cita eliminarCita(Integer idCita) {
        Cita cita = obtenerCita(idCita);
        if (cita == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("cita.eliminar", idCita);
                conexionBD.commit();
                return cita;
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
