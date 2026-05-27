package skateshop.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Generic in-memory repository that holds a list of elements of type T.
 * Provides basic add, remove, get all, count, and filter operations.
 * @param <T> the type of elements stored in the repository
 */
public class Repository<T> {
    private final List<T> elements = new ArrayList<>();
    

    /** Adds an element to the repository. */
    public void add(T element)          { elements.add(element); }
    
    /** Removes an element from the repository. Returns true if it existed. */
    public boolean remove(T elemento)      { return elements.remove(elemento); }
    
    /** Returns an unmodifiable view of all elements. */
    public List<T> getAll()            { return Collections.unmodifiableList(elements); }
    
    /** Returns the number of elements. */
    public long count()                     { return elements.size(); }
    
    /** Returns a list of elements that match the given criteria. */

    public List<T> filter(java.util.function.Predicate<T> criteria) {
        return elements.stream().filter(criteria).collect(Collectors.toList());
    }
}


