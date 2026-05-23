package skateshop.interfaces;

import java.util.List;

import skateshop.exceptions.ProductNotFoundException;

/**
 * Classes implementing Searchable can be searched by id and return a List of
 * T objects 
 * @param <T>
 */
public interface Searchable <T> {
	
    T searchById(String id) throws ProductNotFoundException;
    List<T> searchByName(String nombre);

}
