/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Comparator;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Reyes Ruiz
 */
public final class Avion {

    private String codigo;
    private String partida;
    private String destino;
    private float distancia;
    private int tiempoInicio;
    private boolean puedeDespegar = true;
    private Pane node;

    public static final int GRAPH_SIZE = 80;
    private static final String[] COLORES = {"greenyellow", "yellow", "orange"};

    public Avion(String codigo, float distancia, int tiempoInicio) {
        this(codigo, null, null, distancia, tiempoInicio);
    }

    public Avion(String codigo, String partida, String destino, float distancia, int tiempoInicio) {
        this.codigo = codigo;
        this.partida = partida;
        this.destino = destino;
        this.distancia = distancia;
        this.tiempoInicio = tiempoInicio;

        Label l = new Label(codigo);
        l.setFont(new Font(15));
        l.setTextAlignment(TextAlignment.CENTER);
        StackPane sp = new StackPane(l);
        switch (getPrioridad()) {
            case 0:
                sp.setStyle("-fx-background-color: greenyellow");
                break;
            case 1:
                sp.setStyle("-fx-background-color: yellow");
                break;
            case 2:
                sp.setStyle("-fx-background-color: orange");
                break;
            default:
                break;
        }
        sp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        sp.setPrefSize(GRAPH_SIZE, GRAPH_SIZE);

        sp.setOnMouseClicked(this::clicGrafico);
        node = sp;
    }

    public static Avion parseAvion(String linea, int tiempoInicio) {
        String[] data = linea.trim().split(",");
        return new Avion(data[0], data[1], data[2], Integer.parseInt(data[3]), tiempoInicio);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(int tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public boolean puedeDespegar() {
        return puedeDespegar;
    }

    public void setPuedeDespegar(boolean puedeDespegar) {
        this.puedeDespegar = puedeDespegar;
    }

    public Node getNodo() {
        return node;
    }

    public int getPrioridad() {
        if (distancia < 500) {
            return 0;
        } else if (distancia < 1000) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setColor(String color) {
        ((StackPane) node).setStyle("-fx-background-color: " + color);
    }

    @Override
    public String toString() {
        return "Avion{" + "codigo=" + codigo + ", partida=" + partida + ", destino=" + destino + ", distancia=" + distancia + ", tiempoInicio=" + tiempoInicio + ", puedeDespegar=" + puedeDespegar + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.codigo);
        hash = 71 * hash + Objects.hashCode(this.partida);
        hash = 71 * hash + Objects.hashCode(this.destino);
        hash = 71 * hash + Float.floatToIntBits(this.distancia);
        hash = 71 * hash + this.tiempoInicio;
        hash = 71 * hash + (this.puedeDespegar ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Avion other = (Avion) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    private void clicGrafico(MouseEvent e) {
        puedeDespegar = !puedeDespegar;
        Node possibleLabel = node.getChildren().get(0);
        if (possibleLabel instanceof Label) {
            Label label = (Label) possibleLabel;
            if (puedeDespegar) {
                label.setTextFill(Color.BLACK);
                label.setText(codigo);
                label.setFont(Font.font(null, FontWeight.NORMAL, 15));
            } else {
                label.setTextFill(Color.RED);
                label.setText(codigo + "\n!");
                label.setFont(Font.font(null, FontWeight.BOLD, 15));
            }
        }
    }

    public static String colorForPrioridad(int prioridad) {
        return COLORES[prioridad];
    }

    public static class AvionComparadorPorTiempoEspera implements Comparator<Avion> {

        @Override
        public int compare(Avion o1, Avion o2) {
            return o1.getTiempoInicio() - o2.getTiempoInicio();
        }

    }
}
