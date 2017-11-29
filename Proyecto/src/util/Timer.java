/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.TimerTask;

/**
 * Representa un Timer que cuenta los segundos pasados desde que se inició
 *
 *
 * @author Reyes Ruiz
 */
public class Timer {

    private int segundos;
    private java.util.Timer backingTimer;

    /**
     * Crear un nuevo objeto Timer (no se inicia automáticamente)
     */
    public Timer() {
        this.segundos = 0;
    }

    /**
     * Devuelve el número de segundos transcurridos desde que se inició este
     * Timer, sin contar los períodos que ha estado pausado
     *
     * @return Un número entero que representa los segundos desde que este Timer
     * se inició
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * Inicia el conteo de este timer. Usar <code>pause()</code> para detenerlo.
     * Si se ha llamado <code>pause()</code> previamente, el conteo retoma desde
     * donde se quedó.
     */
    public void start() {
        backingTimer = new java.util.Timer();
        backingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                segundos++;
            }
        }, 0, 1000);
    }

    /**
     * Detiene el conteo de este Timer. Permite reiniciarlo. No se debería
     * llamar varias veces consecutivas.
     */
    public void pause() {
        backingTimer.cancel();
    }
}
