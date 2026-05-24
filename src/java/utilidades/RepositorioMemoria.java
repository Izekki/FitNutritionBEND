package utilidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RepositorioMemoria<T> {

    private final Map<Integer, T> datos = new LinkedHashMap<>();
    private final AtomicInteger secuencia = new AtomicInteger(1);
    private final Function<T, Integer> idGetter;
    private final BiConsumer<T, Integer> idSetter;

    public RepositorioMemoria(Function<T, Integer> idGetter, BiConsumer<T, Integer> idSetter) {
        this.idGetter = idGetter;
        this.idSetter = idSetter;
    }

    public synchronized List<T> listar() {
        return new ArrayList<>(datos.values());
    }

    public synchronized T obtener(Integer id) {
        return datos.get(id);
    }

    public synchronized T guardar(T objeto) {
        Integer id = idGetter.apply(objeto);
        if (id == null || id <= 0) {
            id = secuencia.getAndIncrement();
            idSetter.accept(objeto, id);
        } else {
            final Integer idActual = id;
            secuencia.updateAndGet(valor -> Math.max(valor, idActual + 1));
        }
        datos.put(id, objeto);
        return objeto;
    }

    public synchronized T actualizar(Integer id, T objeto) {
        if (!datos.containsKey(id)) {
            return null;
        }
        idSetter.accept(objeto, id);
        datos.put(id, objeto);
        return objeto;
    }

    public synchronized T eliminar(Integer id) {
        return datos.remove(id);
    }

    public synchronized void cargarTodos(Collection<T> objetos) {
        for (T objeto : objetos) {
            guardar(objeto);
        }
    }
}