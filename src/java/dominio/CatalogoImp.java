package dominio;

import java.util.List;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Alimento;
import pojo.Dieta;
import utilidades.Constantes;

public class CatalogoImp {

    public static List<Dieta> listarDietas() {
        List<Dieta> dietas = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                dietas = conexionBD.selectList("dieta.listar");
            } finally {
                conexionBD.close();
            }
        }
        return dietas;
    }

    public static Dieta obtenerDieta(Integer idDieta) {
        Dieta dieta = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                dieta = conexionBD.selectOne("dieta.obtenerPorId", idDieta);
            } finally {
                conexionBD.close();
            }
        }
        return dieta;
    }

    public static Dieta guardarDieta(Dieta dieta) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("dieta.insertar", dieta);
                conexionBD.commit();
                return dieta;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Dieta actualizarDieta(Integer idDieta, Dieta dieta) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                dieta.setIdDieta(idDieta);
                int filas = conexionBD.update("dieta.actualizar", dieta);
                conexionBD.commit();
                return filas > 0 ? dieta : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Dieta eliminarDieta(Integer idDieta) {
        Dieta dieta = obtenerDieta(idDieta);
        if (dieta == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("dieta.eliminar", idDieta);
                conexionBD.commit();
                return dieta;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static List<Alimento> listarAlimentos() {
        List<Alimento> alimentos = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                alimentos = conexionBD.selectList("alimento.listar");
            } finally {
                conexionBD.close();
            }
        }
        return alimentos;
    }

    public static Alimento obtenerAlimento(Integer idAlimento) {
        Alimento alimento = null;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                alimento = conexionBD.selectOne("alimento.obtenerPorId", idAlimento);
            } finally {
                conexionBD.close();
            }
        }
        return alimento;
    }

    public static Alimento guardarAlimento(Alimento alimento) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.insert("alimento.insertar", alimento);
                conexionBD.commit();
                return alimento;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Alimento actualizarAlimento(Integer idAlimento, Alimento alimento) {
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                alimento.setIdAlimento(idAlimento);
                int filas = conexionBD.update("alimento.actualizar", alimento);
                conexionBD.commit();
                return filas > 0 ? alimento : null;
            } catch (Exception e) {
                conexionBD.rollback();
                throw new RuntimeException(e);
            } finally {
                conexionBD.close();
            }
        }
        return null;
    }

    public static Alimento eliminarAlimento(Integer idAlimento) {
        Alimento alimento = obtenerAlimento(idAlimento);
        if (alimento == null) {
            return null;
        }
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                conexionBD.delete("alimento.eliminar", idAlimento);
                conexionBD.commit();
                return alimento;
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
