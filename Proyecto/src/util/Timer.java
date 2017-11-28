/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.TimerTask;

/**
 *
 * @author Reyes Ruiz
 */
public class Timer {

    private int segundos;
    private java.util.Timer backingTimer;

    public Timer() {
        this.segundos = 0;
    }

    public int getSegundos() {
        return segundos;
    }

    public void start() {
        backingTimer = new java.util.Timer();
        backingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                segundos++;
            }
        }, 0, 1000);
    }

    public void pause() {
        backingTimer.cancel();
    }
}
