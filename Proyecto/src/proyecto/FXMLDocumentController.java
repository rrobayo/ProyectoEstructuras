/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import util.Tuple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.Logger;
import util.Timer;

/**
 *
 * @author Reyes Ruiz
 */
public class FXMLDocumentController implements Initializable {

    private Timeline timerIn;
    private Timeline timerOut;
    private Queue<Avion> avionesPorEntrar = new LinkedList<>();
    private ColaPrioridad<Avion> avionesEnPista = new ColaPrioridad<>(3, new Avion.AvionComparadorPorTiempoEspera());
    private Logger logger;
    private Timer timer;

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
        if (timerIn != null) {
            timerIn.pause();
        }
        if (timerOut != null) {
            timerOut.pause();
        }
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
                x -> ingresarAvion())
        );
        timerIn.setCycleCount(Timeline.INDEFINITE);
        timerOut = new Timeline(new KeyFrame(Duration.seconds(speedOut.getValue()),
                x -> sacarAvion())
        );
        timerOut.setCycleCount(Timeline.INDEFINITE);
        logger.addEntry("Simulación iniciada");
        timerIn.play();
        timerOut.play();
        timer.start();
    }

    @FXML
    private void pausarSimulacion(ActionEvent event) {
        timer.pause();
        timerIn.pause();
        timerOut.pause();
        speedIn.setDisable(false);
        speedOut.setDisable(false);
        btnPausar.setDisable(true);
        btnIniciar.setDisable(false);
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
            logger.addEntry(e.getMessage(), Logger.Severity.ERROR);
        }
    }

    private void dibujarPista() {
        int pos = 0;
        gfxContainer.getChildren().clear();
        for (int i = 0; i < avionesEnPista.prioridades(); i++) {
            for (Avion avion : avionesEnPista.cola(i)) {
                gfxContainer.getChildren().add(avion.getNodo());
                avion.getNodo().relocate(pos * Avion.GRAPH_SIZE, 0);
                pos++;
            }
        }
    }

    private void ingresarAvion() {
        if (!avionesPorEntrar.isEmpty()) {
            Avion nuevo = avionesPorEntrar.poll();
            nuevo.setTiempoInicio(timer.getSegundos());
            avionesEnPista.offer(nuevo, nuevo.getPrioridad());
            logger.addEntry(String.format("Entra %s a pista %d. Falta(n) %d por entrar",
                    nuevo.getCodigo(), nuevo.getPrioridad(), avionesPorEntrar.size()));
        }
        for (int i = 1; i <= 2; i++) {
            while (!avionesEnPista.cola(i).isEmpty() && (timer.getSegundos() - avionesEnPista.cola(i).peek().getTiempoInicio()) > 5) {
                Avion atrasado = avionesEnPista.cola(i).poll();
                logger.addEntry(String.format("%s está atrasado y se mueve a pista %d", atrasado.getCodigo(), i - 1));
                atrasado.setTiempoInicio(timer.getSegundos());
                atrasado.setColor(Avion.colorForPrioridad(i - 1));
                avionesEnPista.offer(atrasado, i - 1);
            }
        }
        dibujarPista();
    }

    private void sacarAvion() {
        if (!avionesEnPista.isEmpty()) {
            PriorityQueue<Avion> primeraColaNoVacia = avionesEnPista.primeraColaNoVacia();
            for (int i = 0; i < primeraColaNoVacia.size(); i++) {
                Tuple<Avion, Integer> sale = avionesEnPista.poll();
                if (sale.getFirst().puedeDespegar()) {
                    logger.addEntry(String.format("Sale %s de pista %d. Falta(n) %d por salir",
                            sale.getFirst().getCodigo(), sale.getSecond(), avionesEnPista.size()));
                    break;
                } else {
                    logger.addEntry(String.format("%s no puede despegar de pista %d", sale.getFirst().getCodigo(), sale.getSecond()));
                    sale.getFirst().setTiempoInicio(timer.getSegundos());
                    avionesEnPista.offer(sale.getFirst(), sale.getSecond());
                }
            }
            dibujarPista();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger = new Logger(loggerContainer);
        timer = new Timer();
    }

    public void shutdown() {
        if (timer != null) {
            timer.pause();
        }
        if (timerIn != null) {
            timerIn.stop();
        }
        if (timerOut != null) {
            timerOut.stop();
        }
    }

}
