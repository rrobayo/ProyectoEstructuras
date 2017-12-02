/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import util.Tuple;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Implementación de una cola de prioridades con clases. Se pueden almacenar
 * elementos con una prioridad especificada. Los elementos con una prioridad
 * menor van antes que los de una prioridad mayor. Dentro de una misma
 * prioridad, los elementos se ordenan de acuerdo a su orden natural u,
 * opcionalmente, de acuerdo a un <code>Comparator&lt;T&gt;</code> especificado.
 *
 * @author Reyes Ruiz
 */
public class ColaPrioridad<T> {

    private StringBuilder buffer;
    private List<PriorityQueue<T>> colas;

    /**
     * Crear una nueva ColaPrioridad con un número específico de clases
     *
     * @param nColas El número de clases que tendrá la ColaPrioridad.
     */
    public ColaPrioridad(int nColas) {
        this(nColas, null);
    }

    /**
     * Crear una nueva ColaPrioridad con un número específico de clases y un
     * Comparador para los elementos de una misma clase
     *
     * @param nColas El número de clases que tendrá la ColaPrioridad.
     * @param comparador El Comparator que define el orden de los elementos de
     * una misma clase
     */
    public ColaPrioridad(int nColas, Comparator<T> comparador) {
        colas = new ArrayList<>();
        for (int i = 0; i < nColas; i++) {
            colas.add(new PriorityQueue<>(comparador));
        }
    }

    /**
     * Vacía la cola de prioridades (todas las clases quedan vacías)
     */
    public void clear() {
        colas.forEach(cola -> cola.clear());
    }

    /**
     * Inserta un elemento en la cola en la clase dada
     *
     * @param e El elemento a ingresar
     * @param prioridad La prioridad o clase en la que el elemento debe ingresar
     * @return
     */
    public boolean offer(T e, int prioridad) {
        return colas.get(prioridad).offer(e);
    }

    /**
     * Extrae y devuelve el elemento que tiene la menor prioridad y que es el
     * menor de todos los que tienen su misma prioridad
     *
     * @return El elemento con la menor prioridad y menor de entre todos los de
     * su clase
     */
    public Tuple<T, Integer> poll() {
        int i = 0;
        for (Queue<T> cola : colas) {
            if (!cola.isEmpty()) {
                return new Tuple<>(cola.poll(), i);
            }
            i++;
        }
        return null;
    }

    /**
     * <b>NO</b> extrae y devuelve el elemento que tiene la menor prioridad y
     * que es el menor de todos los que tienen su misma prioridad
     *
     * @return El elemento con la menor prioridad y menor de entre todos los de
     * su clase
     */
    public Tuple<T, Integer> peek() {
        int i = 0;
        for (Queue<T> cola : colas) {
            if (!cola.isEmpty()) {
                return new Tuple<>(cola.peek(), i);
            }
            i++;
        }
        return null;
    }

    /**
     * Devuelve el tamaño de la cola (la suma del número de elementos de todas
     * las clases)
     *
     * @return El número total de elementos en la ColaPrioridad
     */
    public int size() {
        int total = 0;
        for (Queue<T> cola : colas) {
            total += cola.size();
        }
        return total;
    }

    /**
     * Devuelve el número de clases de la cola (hayan o no elementos de esa
     * prioridad)
     *
     * @return El número de clases de la cola.
     */
    public int prioridades() {
        return colas.size();
    }

    /**
     * Devuelve <code>true</code> si y sólo si la cola no tiene elementos. En
     * ese caso, llamar <code>cola.poll()</code> devolvería <code>null</code>.
     *
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Devuelve el número de elementos en la cola con una prioridad específica.
     *
     * @param prioridad La prioridad de la que se desea saber el número de
     * elementos
     * @return El número de elementos que tienen la prioridad dada
     */
    public int elemsConPrioridad(int prioridad) {
        return colas.get(prioridad).size();
    }

    /**
     * Devuelve la PriorityQueue que almacena los elementos de la prioridad
     * dada.
     *
     * @param prioridad La prioridad de la que se desea la Cola
     * @return La Cola para la prioridad dada.
     */
    public PriorityQueue<T> cola(int prioridad) {
        return colas.get(prioridad);
    }

    /**
     * Devuelve la primera cola que no está vacía, o <code>null</code> si todas
     * las colas de la ColaPrioridad están vacías
     *
     * @return La primera cola que tiene elementos
     */
    public PriorityQueue<T> primeraColaNoVacia() {
        for (int i = 0; i < colas.size(); i++) {
            if (!colas.get(i).isEmpty()) {
                return colas.get(i);
            }
        }
        return new PriorityQueue<>();
    }

    @Override
    public String toString() {
        buffer = new StringBuilder();
        int i = 0;
        for (Queue cola : colas) {
            buffer.append(i).append("-").append(cola.toString()).append("\t");
            i++;
        }
        return buffer.toString();
    }

}
