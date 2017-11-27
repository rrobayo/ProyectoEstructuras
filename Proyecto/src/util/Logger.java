/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Reyes Ruiz
 */
public class Logger {

    public enum Severity {
        INFO, WARNING, ERROR
    }
    private List<Entry> entries = new LinkedList<>();
    private VBox loggerContainer;

    public Logger(VBox loggerContainer) {
        this.loggerContainer = loggerContainer;
    }

    public void addEntry(String message, Severity severity) {
        entries.add(new Entry(severity, message));
        Label messageLabel = new Label(message);
        switch (severity) {
            case ERROR:
                messageLabel.setTextFill(Color.RED);
                break;
            case WARNING:
                messageLabel.setTextFill(Color.ORANGE);
        }
        loggerContainer.getChildren().add(messageLabel);
    }

    public void addEntry(String message) {
        addEntry(message, Severity.INFO);
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

    }

}
