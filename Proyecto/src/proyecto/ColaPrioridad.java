/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Reyes Ruiz
 */
public class ColaPrioridad<T> {

    private static StringBuilder buffer;
    private List<PriorityQueue<T>> colas;

    public ColaPrioridad(int nColas) {
        this(nColas, null);
    }

    public ColaPrioridad(int nColas, Comparator<T> comparador) {
        colas = new ArrayList<>();
        for (int i = 0; i < nColas; i++) {
            colas.add(new PriorityQueue<>(comparador));
        }
    }

    public void clear() {
        colas.forEach(cola -> cola.clear());
    }

    public boolean offer(T e, int prioridad) {
        return colas.get(prioridad).offer(e);
    }

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

    public int size() {
        int total = 0;
        for (Queue<T> cola : colas) {
            total += cola.size();
        }
        return total;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int elemsConPrioridad(int prioridad) {
        return colas.get(prioridad).size();
    }

    public PriorityQueue<T> cola(int prioridad) {
        return colas.get(prioridad);
    }

    @Override
    public String toString() {
        buffer = new StringBuilder();
        int i = 0;
        for (Queue cola : colas) {
            buffer.append(i).append("-").append(cola.toString()).append("\n");
            i++;
        }
        return buffer.toString();
    }

}
