package dominio;

import dto.Respuesta;
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
                int countPatients = conexionBD.selectOne("medico.contarPacientesActivos", idMedico);
                if (countPatients > 0) {
                    throw new IllegalStateException("No se puede dar de baja al médico porque aún tiene pacientes activos asignados. Reasígnelos primero.");
                }
                conexionBD.update("medico.eliminar", idMedico);
                conexionBD.commit();
                medico.setEstatus("Inactivo");
                return medico;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e.getMessage() != null ? e.getMessage() : e.toString(), e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Respuesta reasignarPacientes(Integer idMedicoOrigen, Integer idMedicoDestino) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                // Verificar que ambos médicos existan
                Medico origen = conexionBD.selectOne("medico.obtenerPorId", idMedicoOrigen);
                Medico destino = conexionBD.selectOne("medico.obtenerPorId", idMedicoDestino);
                if (origen == null || destino == null) {
                    return new Respuesta(true, "Uno o ambos médicos no existen.");
                }

                java.util.Map<String, Object> params = new java.util.HashMap<>();
                params.put("idMedicoOrigen", idMedicoOrigen);
                params.put("idMedicoDestino", idMedicoDestino);

                // 1. Reasignar citas del médico anterior para los pacientes correspondientes
                conexionBD.update("medico.reasignarCitasDePacientes", params);

                // 2. Reasignar los pacientes al nuevo médico
                int pacientesReasignados = conexionBD.update("medico.reasignarPacientes", params);

                conexionBD.commit();
                return new Respuesta(false, "Pacientes y citas reasignados con éxito. Pacientes modificados: " + pacientesReasignados);
            } catch (Exception e) {
                conexionBD.rollback();
                return new Respuesta(true, "Error al reasignar pacientes: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        }
        return new Respuesta(true, utilidades.Constantes.MSJ_ERROR_BD);
    }

    public static List<Medico> buscarMedicos(String nombre, Integer numPersonal, String cedulaProfesional, String estatus) {
        List<Medico> medicos = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                java.util.Map<String, Object> params = new java.util.HashMap<>();
                params.put("nombre", nombre);
                params.put("numPersonal", numPersonal);
                params.put("cedulaProfesional", cedulaProfesional);
                params.put("estatus", estatus);
                medicos = conexionBD.selectList("medico.buscar", params);
            } finally {
                conexionBD.close();
            }
        }
        return medicos;
    }
}
