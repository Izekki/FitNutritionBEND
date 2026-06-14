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

    public static List<Cita> listarPorPaciente(Integer idPaciente) {
        List<Cita> citas = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                citas = conexionBD.selectList("cita.obtenerPorPaciente", idPaciente);
            } finally {
                conexionBD.close();
            }
        }
        return citas;
    }

    public static List<Cita> buscarCitas(String fecha, Integer idPaciente, Integer idMedico, String estado) {
        List<Cita> citas = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                java.util.Map<String, Object> params = new java.util.HashMap<>();
                params.put("fecha", fecha);
                params.put("idPaciente", idPaciente);
                params.put("idMedico", idMedico);
                params.put("estado", estado);
                citas = conexionBD.selectList("cita.buscar", params);
            } finally {
                conexionBD.close();
            }
        }
        return citas;
    }

    public static dto.Respuesta cancelarCita(Integer idCita, String motivo) {
        Cita cita = obtenerCita(idCita);
        if (cita == null) {
            return new dto.Respuesta(true, "Cita no encontrada");
        }

        if ("Cancelada".equalsIgnoreCase(cita.getEstado())) {
            return new dto.Respuesta(true, "Esta cita ya fue cancelada anteriormente.");
        }

        String fechaStr = cita.getFecha();
        String horaStr = cita.getHora();
        if (fechaStr == null || horaStr == null) {
            return new dto.Respuesta(true, "La cita no tiene fecha u hora definida.");
        }

        try {
            if (horaStr.length() == 5) {
                horaStr = horaStr + ":00";
            }
            java.time.LocalDate date = java.time.LocalDate.parse(fechaStr);
            java.time.LocalTime time = java.time.LocalTime.parse(horaStr);
            java.time.LocalDateTime citaDateTime = java.time.LocalDateTime.of(date, time);
            java.time.LocalDateTime ahora = java.time.LocalDateTime.now();

            if (citaDateTime.isBefore(ahora)) {
                return new dto.Respuesta(true, "No se puede cancelar una cita que ya ha pasado.");
            }

            java.time.Duration duracion = java.time.Duration.between(ahora, citaDateTime);
            if (duracion.toMinutes() < 60) {
                return new dto.Respuesta(true, "No se puede cancelar la cita. Las cancelaciones deben realizarse al menos con 1 hora de anticipación.");
            }

            SqlSession conexionBD = MybatisUtil.getSession();
            if (conexionBD != null) {
                try {
                    cita.setEstado("Cancelada");
                    String nuevasObs = (cita.getObservaciones() != null && !cita.getObservaciones().trim().isEmpty())
                        ? cita.getObservaciones() + " | Cancelada por el paciente. Motivo: " + motivo
                        : "Cancelada por el paciente. Motivo: " + motivo;
                    cita.setObservaciones(nuevasObs);
                    int filas = conexionBD.update("cita.actualizar", cita);
                    conexionBD.commit();
                    if (filas > 0) {
                        return new dto.Respuesta(false, "Cita cancelada exitosamente", cita);
                    }
                } catch (Exception e) {
                    conexionBD.rollback();
                    return new dto.Respuesta(true, "Error al cancelar la cita en la base de datos: " + e.getMessage());
                } finally {
                    conexionBD.close();
                }
            }
        } catch (Exception e) {
            return new dto.Respuesta(true, "Error al validar la fecha y hora de la cita: " + e.getMessage());
        }

        return new dto.Respuesta(true, "No se pudo establecer la sesión con la base de datos");
    }
}
