/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.Logger;

/**
 *
 * @author Reyes Ruiz
 */
public class FXMLDocumentController implements Initializable {

    private Timeline timerIn;
    private Timeline timerOut;
    private Queue<Avion> avionesPorEntrar = new LinkedList<>();
    private ColaPrioridad<Avion> avionesEnPista = new ColaPrioridad<>(3,
            Collections.reverseOrder(new Avion.AvionComparadorPorTiempoEspera()));
    private Logger logger;

    @FXML
    private Slider speedIn;
    @FXML
    private Slider speedOut;
    @FXML
    private Button btnIniciar;
    @FXML
    private Button btnPausar;
    @FXML
    private VBox loggerContainer;
    @FXML
    private Pane gfxContainer;

    @FXML
    private void cargarArchivo(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Cargar archivo");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = chooser.showOpenDialog(speedIn.getScene().getWindow());

        if (file != null) {
            cargarArchivo(file);
        }
    }

    @FXML
    private void iniciarSimulacion(ActionEvent event) {
        speedIn.setDisable(true);
        speedOut.setDisable(true);
        btnIniciar.setDisable(true);
        btnPausar.setDisable(false);
        timerIn = new Timeline(new KeyFrame(Duration.seconds(speedIn.getValue()),
                x -> {
                    if (!avionesPorEntrar.isEmpty()) {
                        Avion nuevo = avionesPorEntrar.poll();
                        avionesEnPista.offer(nuevo, nuevo.getPrioridad());
                        logger.addEntry(String.format("Entra %s a pista %d. Falta(n) %d por entrar",
                                nuevo.getCodigo(), nuevo.getPrioridad(), avionesPorEntrar.size()));
                        dibujarPista();
                    }
                })
        );
        timerIn.setCycleCount(Timeline.INDEFINITE);
        timerOut = new Timeline(new KeyFrame(Duration.seconds(speedOut.getValue()),
                x -> {
                    if (!avionesEnPista.isEmpty()) {
                        Tuple<Avion, Integer> sale = avionesEnPista.poll();
                        logger.addEntry(String.format("Sale %s de pista %d. Falta(n) %d por salir",
                                sale.getFirst().getCodigo(), sale.getSecond(), avionesEnPista.size()));
                        dibujarPista();

                    }
                })
        );
        timerOut.setCycleCount(Timeline.INDEFINITE);
        logger.addEntry("Simulación iniciada");
        timerIn.play();
        timerOut.play();
    }

    @FXML
    private void pausarSimulacion(ActionEvent event) {
        timerIn.pause();
        timerOut.pause();
        speedIn.setDisable(false);
        speedOut.setDisable(false);
        logger.addEntry("Simulación pausada");
    }

    private void cargarArchivo(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            avionesPorEntrar.clear();
            avionesEnPista.clear();
            for (String line; (line = br.readLine()) != null;) {
                avionesPorEntrar.offer(Avion.parseAvion(line, 0));
            }
            btnIniciar.setDisable(false);
            logger.addEntry("Se carga el archivo " + file.getName());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    private void dibujarPista() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger = new Logger(loggerContainer);
    }

}
