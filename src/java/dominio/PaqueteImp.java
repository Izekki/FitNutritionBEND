package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Consulta;
import utilidades.Utilidades;

public class ConsultaImp {

    public static List<Consulta> listarConsultas() {
        List<Consulta> consultas = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                consultas = conexionBD.selectList("consulta.listar");
            } finally {
                conexionBD.close();
            }
        }
        return consultas;
    }

    public static Consulta obtenerConsulta(Integer idConsulta) {
        Consulta consulta = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                consulta = conexionBD.selectOne("consulta.obtenerPorId", idConsulta);
            } finally {
                conexionBD.close();
            }
        }
        return consulta;
    }

    public static Consulta guardarConsulta(Consulta consulta) {
        if (consulta != null && consulta.getImcCalculado() == null) {
            consulta.setImcCalculado(Utilidades.calcularImc(consulta.getPesoCapturado(), consulta.getTallaCapturada()));
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("consulta.insertar", consulta);
                conexionBD.commit();
                return consulta;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Consulta actualizarConsulta(Integer idConsulta, Consulta consulta) {
        if (consulta != null && consulta.getImcCalculado() == null) {
            consulta.setImcCalculado(Utilidades.calcularImc(consulta.getPesoCapturado(), consulta.getTallaCapturada()));
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                consulta.setIdConsulta(idConsulta);
                int filas = conexionBD.update("consulta.actualizar", consulta);
                conexionBD.commit();
                return filas > 0 ? consulta : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Consulta eliminarConsulta(Integer idConsulta) {
        Consulta consulta = obtenerConsulta(idConsulta);
        if (consulta == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("consulta.eliminar", idConsulta);
                conexionBD.commit();
                return consulta;
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
