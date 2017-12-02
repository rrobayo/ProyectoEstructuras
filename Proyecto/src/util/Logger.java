/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Representa un objeto que recibe y almacena logs y los muestra en una interfaz
 * gráfica.
 *
 * @author Reyes Ruiz
 */
public class Logger {

    /**
     * Las severidades de los mensajes. En orden creciente de importancia, son
     * INFO, WARNING y ERROR.
     */
    public enum Severity {
        INFO, WARNING, ERROR
    }
    private List<Entry> entries = new LinkedList<>();
    private VBox loggerContainer;

    /**
     * Crea un nuevo Logger que mostrará los mensajes en el VBox provisto.
     *
     * @param loggerContainer El VBox en el cual se mostrarán los mensajes que
     * se registren en el Logger. Un VBox debería ser usado por un único Logger
     * a la vez.
     */
    public Logger(VBox loggerContainer) {
        this.loggerContainer = loggerContainer;
    }

    /**
     * Crear una nueva entrada en el Logger, con el mensaje y la severidad
     * especificados.
     *
     * @param message El cuerpo del mensaje
     * @param severity La severidad del evento
     */
    public void addEntry(String message, Severity severity) {
        entries.add(new Entry(severity, message));
        Label messageLabel = new Label(message);
        switch (severity) {
            case ERROR:
                messageLabel.setTextFill(Color.RED);
                break;
            case WARNING:
                messageLabel.setTextFill(Color.ORANGE);
                break;
            default:
                break;
        }
        loggerContainer.getChildren().add(messageLabel);
    }

    /**
     * Crear una nueva entrada en el Logger, con el mensaje especificado y una
     * severidad por defecto de INFO
     *
     * @param message El cuerpo del mensaje
     */
    public void addEntry(String message) {
        addEntry(message, Severity.INFO);
    }

    /**
     * Crear una nueva entrada en el Logger, basada en la excepción dada.
     * Siempre tendrá una severidad de ERROR
     *
     * @param e La excepción que causa el mensaje
     */
    public void addEntry(Exception e) {
        addEntry(e.getMessage(), Severity.ERROR);
    }

    /**
     * Guardar el estado actual del Logger en un archivo (sobreescribe el
     * archivo existente)
     *
     * @param file El objeto File que apunta al archivo a usar
     * @return <code>true</code> si el proceso ocurrió sin errores,
     * <code>false</code> si ocurrió algún error
     */
    public boolean save(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Entry e : entries) {
                switch (e.getSeverity()) {
                    case INFO:
                        bw.write("INFO");
                        break;
                    case WARNING:
                        bw.write("WARN");
                        break;
                    case ERROR:
                        bw.write("ERR");
                        break;
                }
                bw.write("-");
                bw.write(e.getCreateDate().toString());
                bw.write("-");
                bw.write(e.getMessage().trim());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Guardar el estado actual del Logger en un archivo (sobreescribe el
     * archivo existente)
     *
     * @param file La ubicación del archivo a usar
     * @return <code>true</code> si el proceso ocurrió sin errores,
     * <code>false</code> si ocurrió algún error
     */
    public boolean save(String file) {
        return save(new File(file));
    }

    private class Entry {

        private Severity severity;
        private String message;
        private Date createDate;

        public Entry(Severity severity, String message) {
            this.severity = severity;
            this.message = message;
            createDate = new Date();
        }

        public Severity getSeverity() {
            return severity;
        }

        public void setSeverity(Severity severity) {
            this.severity = severity;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

    }

}
