/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * Una clase que representa una Tupla o par ordenado de dos valores de tipos
 * arbitrarios. Usar cuando se desea encapsular dos objetos juntos (por ejemplo,
 * devolver un objeto de una lista y, a la vez, el índice en el que este
 * elemento fue encontrado)
 *
 * Notar que los valores tienen un orden específico.
 *
 * @author Reyes Ruiz
 */
public class Tuple<T, U> {

    private T first;
    private U second;

    /**
     * Crear una nueva Tupla con dos elementos, uno de cada tipo de dato
     * especificado.
     *
     * @param first El primer objeto, que va en la primera posición.
     * @param second El segundo objeto, que va en la segunda posición.
     */
    public Tuple(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Devuelve el objeto en la primera posición.
     *
     * @return El objeto en la primera posición
     */
    public T getFirst() {
        return first;
    }

    /**
     * Define el objeto en la primera posición.
     *
     * @param first El nuevo objeto a almacenar en la primera posición.
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Devuelve el objeto en la segunda posición.
     *
     * @return El objeto en la segunda posición
     */
    public U getSecond() {
        return second;
    }

    /**
     * Define el objeto en la segunda posición.
     *
     * @param second El nuevo objeto a almacenar en la segunda posición.
     */
    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}
