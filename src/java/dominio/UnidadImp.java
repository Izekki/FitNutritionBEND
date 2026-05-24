package dominio;

import java.util.List;
import java.util.stream.Collectors;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.DietaAlimento;

public class UnidadImp {

    public static List<DietaAlimento> listarDietasAlimentos() {
        List<DietaAlimento> relaciones = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                relaciones = conexionBD.selectList("dietaAlimento.listar");
            } finally {
                conexionBD.close();
            }
        }
        return relaciones;
    }

    public static List<DietaAlimento> listarPorDieta(Integer idDieta) {
        List<DietaAlimento> relaciones = listarDietasAlimentos();
        return relaciones == null ? null
                : relaciones.stream()
                        .filter(relacion -> relacion.getIdDieta() != null && relacion.getIdDieta().equals(idDieta))
                        .collect(Collectors.toList());
    }

    public static List<DietaAlimento> listarPorAlimento(Integer idAlimento) {
        List<DietaAlimento> relaciones = listarDietasAlimentos();
        return relaciones == null ? null
                : relaciones.stream()
                        .filter(relacion -> relacion.getIdAlimento() != null
                                && relacion.getIdAlimento().equals(idAlimento))
                        .collect(Collectors.toList());
    }

    public static DietaAlimento obtenerDietaAlimento(Integer idDietaAlimento) {
        DietaAlimento relacion = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                relacion = conexionBD.selectOne("dietaAlimento.obtenerPorId", idDietaAlimento);
            } finally {
                conexionBD.close();
            }
        }
        return relacion;
    }

    public static DietaAlimento guardarDietaAlimento(DietaAlimento dietaAlimento) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("dietaAlimento.insertar", dietaAlimento);
                conexionBD.commit();
                return dietaAlimento;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static DietaAlimento actualizarDietaAlimento(Integer idDietaAlimento, DietaAlimento dietaAlimento) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                dietaAlimento.setIdDietaAlimento(idDietaAlimento);
                int filas = conexionBD.update("dietaAlimento.actualizar", dietaAlimento);
                conexionBD.commit();
                return filas > 0 ? dietaAlimento : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static DietaAlimento eliminarDietaAlimento(Integer idDietaAlimento) {
        DietaAlimento relacion = obtenerDietaAlimento(idDietaAlimento);
        if (relacion == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("dietaAlimento.eliminar", idDietaAlimento);
                conexionBD.commit();
                return relacion;
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
