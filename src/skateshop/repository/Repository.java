package skateshop.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Repository<T> {
    private final List<T> elements = new ArrayList<>();

    public void add(T element)          { elements.add(element); }
    public boolean eliminar(T elemento)      { return elements.remove(elemento); }
    public List<T> obtenerTodos()            { return Collections.unmodifiableList(elements); }
    public long contar()                     { return elements.size(); }

    public List<T> filtrar(java.util.function.Predicate<T> criteria) {
        return elements.stream().filter(criteria).collect(Collectors.toList());
    }
}


