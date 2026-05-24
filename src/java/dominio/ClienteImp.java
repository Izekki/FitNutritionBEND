package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paciente;

public class ClienteImp {

    public static List<Paciente> listarPacientes() {
        List<Paciente> pacientes = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                pacientes = conexionBD.selectList("paciente.listar");
            } finally {
                conexionBD.close();
            }
        }
        return pacientes;
    }

    public static Paciente obtenerPaciente(Integer idPaciente) {
        Paciente paciente = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                paciente = conexionBD.selectOne("paciente.obtenerPorId", idPaciente);
            } finally {
                conexionBD.close();
            }
        }
        return paciente;
    }

    public static Paciente obtenerPacientePorUsuario(Integer idUsuario) {
        Paciente paciente = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                paciente = conexionBD.selectOne("paciente.obtenerPorUsuario", idUsuario);
            } finally {
                conexionBD.close();
            }
        }
        return paciente;
    }

    public static Paciente guardarPaciente(Paciente paciente) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("paciente.insertar", paciente);
                conexionBD.commit();
                return paciente;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Paciente actualizarPaciente(Integer idPaciente, Paciente paciente) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                paciente.setIdPaciente(idPaciente);
                int filas = conexionBD.update("paciente.actualizar", paciente);
                conexionBD.commit();
                return filas > 0 ? paciente : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Paciente eliminarPaciente(Integer idPaciente) {
        Paciente paciente = obtenerPaciente(idPaciente);
        if (paciente == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("paciente.eliminar", idPaciente);
                conexionBD.commit();
                return paciente;
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
