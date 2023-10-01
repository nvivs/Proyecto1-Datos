package Proyecto.Util;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2023-08-15
 *
 * --------------------------------------------------------------------
 * @param <T>
 */
public interface ICollection<T> extends Iterable<T> {

    public boolean isEmpty();

    public int count();

    public T get(int p);

    public ICollection<T> add(T data);

    public ICollection<T> add(T data, int p);

    public T remove();

    public T remove(int p);

    public ICollection<T> clear();

}
