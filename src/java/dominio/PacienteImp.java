package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Paciente;

public class PacienteImp {

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

    public static Paciente guardarPaciente(Paciente paciente) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                if (paciente.getCodigoAcceso() == null || paciente.getCodigoAcceso().trim().isEmpty() || paciente.getCodigoAcceso().equals("N/D") || paciente.getCodigoAcceso().equals("****")) {
                    String nuevoCodigo = String.format("%04d", new java.util.Random().nextInt(10000));
                    paciente.setCodigoAcceso(nuevoCodigo);
                }
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
                // Recuperar estado actual para ver si cambió el médico asignado
                Paciente actual = conexionBD.selectOne("paciente.obtenerPorId", idPaciente);
                
                // Si el cliente mandó "****" o "N/D", anularlo para no sobrescribir
                if (paciente.getCodigoAcceso() != null && (paciente.getCodigoAcceso().equals("****") || paciente.getCodigoAcceso().equals("N/D"))) {
                    paciente.setCodigoAcceso(null);
                }

                // Si no hay PIN en la BD y tampoco se envió uno nuevo válido, asignamos uno al azar
                if (actual != null && (actual.getCodigoAcceso() == null || actual.getCodigoAcceso().trim().isEmpty())) {
                    if (paciente.getCodigoAcceso() == null || paciente.getCodigoAcceso().trim().isEmpty()) {
                        String nuevoCodigo = String.format("%04d", new java.util.Random().nextInt(10000));
                        paciente.setCodigoAcceso(nuevoCodigo);
                    }
                }
                
                int filas = conexionBD.update("paciente.actualizar", paciente);
                
                if (filas > 0 && actual != null && actual.getIdMedico() != null && paciente.getIdMedico() != null 
                        && !actual.getIdMedico().equals(paciente.getIdMedico())) {
                    java.util.Map<String, Object> params = new java.util.HashMap<>();
                    params.put("idPaciente", idPaciente);
                    params.put("idMedicoOrigen", actual.getIdMedico());
                    params.put("idMedicoDestino", paciente.getIdMedico());
                    conexionBD.update("paciente.reasignarCitasDePacienteIndividual", params);
                }
                
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
                conexionBD.update("paciente.eliminar", idPaciente);
                conexionBD.commit();
                paciente.setEstatus("Inactivo");
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

    public static List<Paciente> buscarPacientes(String nombre, String email, Integer idMedico, String estatus) {
        List<Paciente> pacientes = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                java.util.Map<String, Object> params = new java.util.HashMap<>();
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("idMedico", idMedico);
                params.put("estatus", estatus);
                pacientes = conexionBD.selectList("paciente.buscar", params);
            } finally {
                conexionBD.close();
            }
        }
        return pacientes;
    }
}
